package com.ims.oversea.eplatonframework.business.rule.spcommoRule;

import javax.ejb.*;
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
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.bcf.BCF;
import com.chb.coses.framework.transfer.IDTO;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 실제 업무 시스템의 rule단이 정의되는 부분이다.
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

public class SPcommoRuleSBBean implements SessionBean {
  SessionContext sessionContext;
  public void ejbCreate() throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() {
    /**@todo Complete this method*/
  }
  public void ejbActivate() {
    /**@todo Complete this method*/
  }
  public void ejbPassivate() {
    /**@todo Complete this method*/
  }
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  public IDTO execute(String call_operation_class,
                      String call_operation_method,
                      IDTO transferDDTO,
                      EPlatonEvent event)
  {

    EPlatonEvent resp_event = null;
    IDTO responseDDTO = null;

    try
    {
      resp_event = (EPlatonEvent)event;

      BCF bcf = new BCF();

      responseDDTO = (IDTO)bcf.execute(call_operation_class,
                                       call_operation_method,
                                       transferDDTO,
                                       event);

    }
    catch (Exception re)
    {
      re.printStackTrace();
      //////////////////////////////////////////////////////////////////////////
      // 에러코드 관리
      //////////////////////////////////////////////////////////////////////////
      {
        TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)event).getTPSVCINFODTO();

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
      LOGEJ.getInstance().eprintf(TCFConstants.TCF_LOG_LEVEL,(EPlatonEvent)event,re);
      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,this.getClass().getName()+ ".execute():" + re.toString());

    }

    return responseDDTO;

  }
}