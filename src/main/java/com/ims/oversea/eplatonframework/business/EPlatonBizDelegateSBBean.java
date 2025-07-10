package com.ims.oversea.eplatonframework.business;

import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;

import com.chb.coses.framework.business.*;
import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.exception.*;

import com.chb.coses.framework.transfer.*;
import com.ims.oversea.foundation.config.*;
import com.ims.oversea.framework.transaction.delegate.action.*;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.tpmutil.TPMSutil;
import com.ims.oversea.framework.transaction.delegate.action.EPlatonBizAction;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
import com.ims.oversea.framework.transaction.config.TCFConfig;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 우리시스템의 비즈니스 델리게이트이다.
 * 트랜잭션 타입은 not supported 이다
 * 우리시스템의 프락시서버와 같은 역할을 담당하며, 클라이언서와 서버와의 결합도를
 * 낮추어준다.
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 *  2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 *                                                        @author : 장우승(WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */

public class EPlatonBizDelegateSBBean extends com.chb.coses.framework.business.AbstractSessionBean
    implements IBizDelegate, SessionBean
{

  /**
   * eplaton의 모드가 개발인지 실환경인지를 조사해서 bizActionMap파일생성여부를 결정
   * 하기위해 만든 로직이다.
   * 운영환경에서는 적용되지 않는다.
   * @throws CreateException
   */
  public void ejbCreate() throws CreateException {
    String machine_mode = null;
    try {
      //Config == com.ims.foundation.config.Config 클래스
      machine_mode = TCFConfig.getInstance().getElement(com.ims.oversea.framework.transaction.constant.TCFConstants.MACHINE_SERVICE_MODE_TAG).getTextTrim();
      if( machine_mode.equals(com.ims.oversea.framework.transaction.constant.TCFConstants.MACHINE_SERVICE_MODE_DEV) ){
        // BizAction map generator service start
        //ActionManager.getInstance().getActionMapService().startService();
        System.out.println("-------------------------------["+machine_mode+"]");
      }
      System.out.println("-------------------------------["+machine_mode+"]");
    } catch (Exception e) {
      e.printStackTrace();
      String str = "Action map generation service start failed.";
      //System.out.println(str+e);
    }

  }

  /**
   * 클라이언트의 요청을 서버에서 받아들이는 최초의 모듈이다
   * 실질적인 각 업무시스템으로의 reflection을 시작하는 곳이다.
   *
   * @ 참조 : com.chb.coses.framework.business.delegate.IBizDelegate#execute(IEvent)
   * @param event : 클라이언트와 서버간 메시지 교환을 위한 패킷
   * @return : IEvent타입을 리턴한다
   * @throws BizDelegateException
   */
  public IEvent execute(IEvent event) throws BizDelegateException {
    IEvent resevent = null;
    long startcurrentTimeMillis = System.currentTimeMillis();
    long endcurrentTimeMillis = System.currentTimeMillis() - startcurrentTimeMillis;

    try
    {
      /*
       * 최초의 메시지를 처리하기위한 TPMSutil.TPSrecv 모듈을 호출한다
       * 1. 기본적인 데이타 검증
       * 2. 거래번호를 채번 이후 tpfq의 정보에 따라서 hostseq,orgseq을 생성한다.
       */
      resevent = event = TPMSutil.getInstance().TPSrecv((EPlatonEvent)event);
      TPMSutil.getInstance().TPSDBrecv((EPlatonEvent)event);

      if ( !isErr((EPlatonEvent)event) )
      {
        /*
         * 보내온 EPLATONEVENT 객체에 에러코드에 에러가 아닌경우에만 다음 로직을 수행한다
         */
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,
                                   "actionClassName : " +
                                   ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name());
        /*
         * ACTION 클래스를 가지고 온다
         * actionClassName="com.ims.oversea.eplatonframework.business.delegate.action.CashCardBizAction"
         * 이값은 TPSVCINFODTO의 action_name 과 EPlatonCommonDTO의 request_name은 같은 값이다.
         * 현재는 EPlatonCommonDTO의 request_name을 기준으로 한다.
         */
        String actionClassName = ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name();

        /*
         * 받아온 action 명 즉 클래스명으로 (ex .....action.CashCardBizAction)을 가지고 하나의
         * 인스턴스를 생성하고 act() 메소드를 호출한다.
         * 이렇게 함으로서 클라이언트의 요청을 서버에서 감추면서 클라이언트와 서버간의
         * 결합도를 낮춘다.
         * 이로직에서 각시스템으로의 실질적인 reflection을 시작한다.
         */
        EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
        resevent = action.act(event);
      }
      else{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,
                           "에러가 발생했습니다. 클라이언트로 바로 리턴합니다 : action - " +
                           ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name());
      }
    }
    catch (Exception _e)
    {
      ((EPlatonEvent)event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.EBDL001);
      ((EPlatonEvent)event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.EBDL001+"-"+_e.toString());
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"EPlatonBizDelegateSBBean-execute()에서 에러발생:[EDEL001]");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,_e);
      _e.printStackTrace();
    }
    finally {
    }

    /*
     * 클라이언트로 데이타 전송
     */
    endcurrentTimeMillis = System.currentTimeMillis() - startcurrentTimeMillis;
    TPMSutil.getInstance().TPSDBsend((EPlatonEvent)event,endcurrentTimeMillis);

    TPMSutil.getInstance().TPSsend((EPlatonEvent)resevent);


    return resevent;

  }

  public void ejbRemove()
  {
  }

  /**
   * 현재 클라이언트에서 올라온 객체에 에러가 있는 지 조사한다.
   * @param eplevent
   * @return
   */
  private boolean isErr(EPlatonEvent eplevent)
  {
    switch (eplevent.getTPSVCINFODTO().getErrorcode().charAt(0))
    {
      case 'e':
      case 's':
      case 'E':
      case 'S':
        return true;
      case 'I':
        return false;
      case '*':
      default:
      return true;
    }
  }

  public com.ims.oversea.eplatonframework.transfer.EPlatonEvent epl_method_01(EPlatonEvent event, String s1) {
    /**@todo Complete this method*/
    return event;
  }

  /**
   * group file에서 강제 적용해본 로직
   */
  java.lang.String epl_val_01;
  public void setEpl_val_01(java.lang.String epl_val_01) {
    this.epl_val_01 = epl_val_01;
  }
  public java.lang.String getEpl_val_01() {
    return epl_val_01;
  }

}

