package com.ims.oversea.eplatonframework.business.facade.cashCard;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;

import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.ims.oversea.framework.transaction.tcf.*;
import com.ims.oversea.eplatonframework.business.facade.cashCard.ICashCardManagementSB;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
//import com.ims.oversea.framework.transaction.tpmutil.TPMSutil;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 실제 업무 시스템의 facade단이 정의되는 부분이다.
 * 현재 이 각 업무시스템의 facade단을 직접가지고 감으로서 모든 시스템을 하나로 로직을 관리
 * 하고 트랜잭션을 일률적으로 관리하기 위한 로직이다.
 * 실제 호출되는 세션빈에 대한 메소드는 execute()이다.
 * 이 메소드내에서 각 업무시스템과의 연계를 위한 하나의 메소드가 정의 된다.
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

public class CashCardManagementSBBean extends com.chb.coses.framework.business.AbstractSessionBean
    implements ICashCardManagementSB,SessionBean
{

  public void ejbCreate() throws CreateException {

  }

  /**
   * 이메소드에서 호출되는 객체는 tcf로서 tcf는 3개의 객체로 구성되며, 전체 트랜잭션을
   * 일률적으로 관리해주는 역할을 한다.
   * 또한 트랜잭션을 하나의 로직으로 감싸므로서 트랜잭션을 단순화 시킬수 있는 구조로
   * 갈수가 있다.
   *
   * @param event
   * @return
   */
  public EPlatonEvent execute(EPlatonEvent event)
  {
    EPlatonEvent resp_event = null;
    String transaction_type = null;
    try
    {
      resp_event = event;

      TCF tcf = new TCF();

      resp_event = tcf.execute(event,this.ctx, com.ims.oversea.framework.transaction.tpmutil.TPMSutil.getInstance().TPgetbeantransactiontype(event) );

    }
    catch (Exception re)
    {

      re.printStackTrace();
      //////////////////////////////////////////////////////////////////////////
      // 에러코드 관리
      //////////////////////////////////////////////////////////////////////////
      {
        TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)resp_event).getTPSVCINFODTO();

        switch( tpsvcinfo.getErrorcode().charAt(0) )
        {
          case 'I' :
            tpsvcinfo.setErrorcode(TCFConstantErrcode.EFAD001);
            tpsvcinfo.setError_message(TCFConstantErrcode.EFAD001_MSG + "-" + this.getClass().getName());
            break;
          case 'E' :
            String errorcode = TCFConstantErrcode.EFAD001+"|"+tpsvcinfo.getErrorcode();
            tpsvcinfo.setErrorcode(errorcode);
            tpsvcinfo.setError_message(TCFConstantErrcode.EFAD001_MSG + "-" + this.getClass().getName());
            break;
        }
      }
      //////////////////////////////////////////////////////////////////////////
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,re);
      LOGEJ.getInstance().printf(1,resp_event,this.getClass().getName()+ ".execute():" + re.toString());

    }

    return resp_event;

  }


}
