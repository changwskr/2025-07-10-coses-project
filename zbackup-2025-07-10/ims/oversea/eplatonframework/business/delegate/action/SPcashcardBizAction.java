package com.ims.oversea.eplatonframework.business.delegate.action;

import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import java.lang.reflect.*;

import com.chb.coses.foundation.jndi.JNDIService;
import com.chb.coses.framework.transfer.*;

import com.ims.oversea.eplatonframework.business.facade.cashCard.*;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.delegate.action.EPlatonBizAction;
import com.ims.oversea.eplatonframework.business.facade.spcashcard.*;
import com.ims.oversea.framework.transaction.tpmutil.TPMSutil;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
import com.ims.oversea.framework.transaction.constant.TCFConstants;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * EPlatonBizAction을 상속받아 생성된 클래스로서
 * 클라이언트단에서 정한 request_name에 의해서 서버에서 각 특정시스템으로의 라우팅을 받아
 * 들이는 부분이다.
 * 실제 EPlatonBizDelegate에서 호출하며, 호출되는 메소드는 act()메소드를 호출한다
 * [수행]
 *    각 업무시스템의 facade단의 세션빈의 객체를 생성한다. 즉 home 객체와 remote 객체를
 *    생성해고 상속한 doAct()메소드로 remote객체와 클라이언트에서 올라온 event 객체를
 *    전송한다.
 *    실제 facade단의 메소드가 호출되는 곳은 EPlatonBizAction의 doAct()메소드에서
 *    전달되어온 facade단의 리모트객체를 가지고 리모트객체속에 포함된 클래스를 dynamic
 *    invoke을 함으로서 실질적인 처리가 이루어지도록 한다.
 *
 * BizAction 클래스를 둠으로서 delegate와 각 시스템간의 결합도를 낮추는 방안을 마련된다
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

public class SPcashcardBizAction extends EPlatonBizAction
{
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;
  private IEvent event;
  private String call_bean_type = TCFConstants.BEAN_TYPE;

  /**
   * 생성자함수
   */
  public SPcashcardBizAction()
  {
  }

  /**
   * 각 업무시스템의 facade단의 세션빈의 홈객체와 리모트객체를 생성해
   * EPlatonBizAction 클래스의 doAct()메소드를 호출하는 역할 을 한다
   *
   * @param event
   * @return
   */

  public IEvent act(IEvent event)
  {
    try
    {
      event = event;
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=================================================SPcashcardBizAction.act() start");

      SPcashcardManagementSBHome spcashcardHome = null;
      SPcashcardManagementSB iSPcashcardManagementSB = null;

      SPcashcardManagementSBLocalHome spcashcardLocalLocalHome = null;
      SPcashcardManagementSBLocal iSPcashcardManagementSBLocal = null;

      /**
       * 앞으로 우리시스템은 remote/local을 같이 가져가는 구조를 택한다
       * spcashcard.xml
       * -----------------------------------------------------------------------
       *
        <spcashcard-callmethod01>
          <bizaction-class>com.ims.oversea.eplatonframework.business.delegate.action.SPcashcardBizAction</bizaction-class>
          <transactionable>Y</transactionable>
          <action-call-bean-type>local</action-call-bean-type>
          <bizaction-method>callmethod01</bizaction-method>
          <operation-class>com.ims.oversea.cashCard.business.facade.CashCardManagementSBBean</operation-class>
          <bizaction-parametertype>com.ims.oversea.eplatonframework.transfer.EPLcommonCDTO</bizaction-parametertype>
        </spcashcard-callmethod01>
       *
       */
      call_bean_type = TPMSutil.getInstance().TPgetcallbeantype((EPlatonEvent)event);

      if( TCFConstants.BEAN_TYPE.equals(call_bean_type) )
      {
        spcashcardHome =
           (SPcashcardManagementSBHome)JNDIService.getInstance()
                                          .lookup(SPcashcardManagementSBHome.class);
        iSPcashcardManagementSB = spcashcardHome.create();

        event = this.doAct(iSPcashcardManagementSB, event);
      }
      else if( TCFConstants.BEAN_LOCAL_TYPE.equals(call_bean_type) )
      {
        spcashcardLocalLocalHome =
           (SPcashcardManagementSBLocalHome)JNDIService.getInstance()
                                          .lookup(SPcashcardManagementSBLocalHome.class);
        iSPcashcardManagementSBLocal = spcashcardLocalLocalHome.create();

        event = this.doAct(iSPcashcardManagementSBLocal, event);
      }
      else{
        spcashcardHome =
           (SPcashcardManagementSBHome)JNDIService.getInstance()
                                          .lookup(SPcashcardManagementSBHome.class);
        iSPcashcardManagementSB = spcashcardHome.create();
        event = this.doAct(iSPcashcardManagementSB, event);
      }


      /*
       * EPlatonBizAction 클래스의 doAct()메소드를 호출하는 역할 을 한다
       */

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"SPcashcardBizAction success");
    }
    catch(Exception _e)
    {
      SET_SPerror(TCFConstantErrcode.EACT002,TCFConstantErrcode.EACT002_MSG + "," + this.getClass().getName() + " exception");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,_e);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"SPcashcardBizAction error:[EFWK0035]"+_e.toString() );
    }
    finally{
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=================================================SPcashcardBizAction.act() end");
    }

    return event;
  }

  /**
   * 에러코드와 에러메시지를 셋팅한다.
   *
   * @param errorcode
   * @param message
   */
  private void SET_SPerror(String errorcode,String message)
  {
    commonDTO = ((EPlatonEvent)event).getCommon();
    tpsvcinfo = ((EPlatonEvent)event).getTPSVCINFODTO();

    switch( tpsvcinfo.getErrorcode().charAt(0) )
    {
      case 'I' :
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(message);
        return;
      case 'E' :
        errorcode = errorcode+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(message);
        return;
    }
  }

  public String getCall_bean_type() {
    return call_bean_type;
  }
  public void setCall_bean_type(String call_bean_type) {
    this.call_bean_type = call_bean_type;
  }
  public void setCommonDTO(EPlatonCommonDTO commonDTO) {
    this.commonDTO = commonDTO;
  }
  public void setEvent(IEvent event) {
    this.event = event;
  }
  public void setTpsvcinfo(TPSVCINFODTO tpsvcinfo) {
    this.tpsvcinfo = tpsvcinfo;
  }
  public TPSVCINFODTO getTpsvcinfo() {
    return tpsvcinfo;
  }
  public IEvent getEvent() {
    return event;
  }
  public EPlatonCommonDTO getCommonDTO() {
    return commonDTO;
  }

}
