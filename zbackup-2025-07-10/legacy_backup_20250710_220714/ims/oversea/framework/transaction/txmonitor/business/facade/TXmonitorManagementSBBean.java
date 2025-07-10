package com.ims.oversea.framework.transaction.txmonitor.business.facade;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.deposit.business.facade.*;
import com.chb.coses.deposit.transfer.*;
import com.ims.oversea.framework.transaction.helper.Convert;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.tpmutil.TPMSutil;
import com.ims.oversea.framework.transaction.tpmutil.TPSsendrecv;
import com.ims.oversea.eplatonframework.transfer.*;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.ims.oversea.eplatonframework.transfer.EPLcommonCDTO;
import com.ims.oversea.framework.transaction.txmonitor.transfer.TransactionInfoCDTO;
import com.ims.oversea.framework.transaction.txmonitor.business.facade.helper.*;
import com.ims.oversea.framework.transaction.txmonitor.business.rule.ISPtxmonitorRuleSB;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.TransactionInfoDDTO;
import com.chb.coses.framework.transfer.IDTO;

public class TXmonitorManagementSBBean implements ITXmonitorManagementSB{

    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
    }

    // 하나의 자료를 들고온다.
    public EPlatonEvent getTransactionInfoByTransactionNo(EPlatonEvent event)
        throws CosesAppException
    {
      EPlatonEvent     response_event = Convert.createEPlatonEvent(event) ;
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      TransactionInfoCDTO rescdto = null;
      TransactionInfoCDTO reqcdto = new TransactionInfoCDTO() ;

      try{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo START]");
        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        reqcdto = (TransactionInfoCDTO)event.getRequest();

        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"TransactionInfoCDTO에 대한 내용을 가지고 온다.");
        ////////////////////////////////////////////////////////////////////////////
        // 1.rule단의 세션빈을 구한다.
        ISPtxmonitorRuleSB remote = EJBUtilFacade.getISPtxmonitorRuleSB();
        // 2.넘겨줄 DDTO 객체를 구한다.
        TransactionInfoDDTO ddto = DTOConverter.getTransactionInfoDDTO(reqcdto);
        // 3.DDTO 객체를 넘겨받는다.
        TransactionInfoDDTO resddto = remote.getTransactionInfoByTransactionNo(ddto,event);
        rescdto = DTOConverter.getTransactionInfoCDTO((TransactionInfoCDTO)reqcdto,resddto);
        event.setResponse((IDTO)rescdto);
      }
      catch(Exception ex){
        ex.printStackTrace();
        String errorcode = "EFWK0048"+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      }
      ////////////////////////////////////////////////////////////////////////////

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      return event;

    }

    public EPlatonEvent setTransactionInfoByTransactionNo(EPlatonEvent event) throws CosesAppException
    {
      EPlatonEvent     response_event = Convert.createEPlatonEvent(event) ;
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      TransactionInfoCDTO rescdto = null;
      TransactionInfoCDTO reqcdto = new TransactionInfoCDTO() ;

      try{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo START]");
        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        reqcdto = (TransactionInfoCDTO)event.getRequest();

        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"TransactionInfoCDTO에 대한 내용을 가지고 온다.");
        ////////////////////////////////////////////////////////////////////////////
        // 1.rule단의 세션빈을 구한다.
        ISPtxmonitorRuleSB remote = EJBUtilFacade.getISPtxmonitorRuleSB();
        // 2.넘겨줄 DDTO 객체를 구한다.
        TransactionInfoDDTO ddto = DTOConverter.getTransactionInfoDDTO(reqcdto);
        // 3.DDTO 객체를 넘겨받는다.
        if( remote.setTransactionInfoByTransactionNo(ddto,event) ){
          LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"transactionNo에의한 갱신성공");
          ddto=remote.getTransactionInfoByTransactionNo(ddto,event);
        }
        else{
          LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"transactionNo에의한 갱신실패");
        }
        rescdto = DTOConverter.getTransactionInfoCDTO((TransactionInfoCDTO)reqcdto,ddto);
        event.setResponse((IDTO)rescdto);
      }
      catch(Exception ex){
        ex.printStackTrace();
        String errorcode = "EFWK0048"+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      }
      ////////////////////////////////////////////////////////////////////////////

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      return event;

    }

    public EPlatonEvent makeTransactionInfoByTransactionNo(EPlatonEvent event) throws CosesAppException
    {
      EPlatonEvent     response_event = Convert.createEPlatonEvent(event) ;
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      TransactionInfoCDTO rescdto = null;
      TransactionInfoCDTO reqcdto = new TransactionInfoCDTO() ;

      try{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo START]");
        commonDTO = (EPlatonCommonDTO)event.getCommon();
        tpsvcinfo = event.getTPSVCINFODTO();
        reqcdto = (TransactionInfoCDTO)event.getRequest();

        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"TransactionInfoCDTO에 대한 내용을 가지고 온다.");
        ////////////////////////////////////////////////////////////////////////////
        // 1.rule단의 세션빈을 구한다.
        ISPtxmonitorRuleSB remote = EJBUtilFacade.getISPtxmonitorRuleSB();
        // 2.넘겨줄 DDTO 객체를 구한다.
        TransactionInfoDDTO ddto = DTOConverter.getTransactionInfoDDTO(reqcdto);
        // 3.DDTO 객체를 넘겨받는다.
        if( remote.makeTransactionInfoByTransactionNo(ddto,event) )
        {
          LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"makeTransactionInfoByTransactionNo에의한 갱신성공");
          ddto=remote.getTransactionInfoByTransactionNo(ddto,event);
        }
        else{
          LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"makeTransactionInfoByTransactionNo에의한 갱신실패");
        }
        rescdto = DTOConverter.getTransactionInfoCDTO((TransactionInfoCDTO)reqcdto,ddto);
        event.setResponse((IDTO)rescdto);
      }
      catch(Exception ex){
        ex.printStackTrace();
        String errorcode = "EFWK0048"+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(this.getClass().getName()+ ".callmethod01():TPSsendrecv Fail");
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      }
      ////////////////////////////////////////////////////////////////////////////

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[getTransactionInfo  END]"+event.getTPSVCINFODTO().getSystem_name()  );
      return event;

    }
    public EPlatonEvent callmethod03(EPlatonEvent event) throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO() ;

      reqcdto.setAccountNumber("0001100100000048");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("9999999999999999");

      event.setResponse(reqcdto);

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod03  START]"+event.getTPSVCINFODTO().getSystem_name()  );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event," -->> call method 3이 호출되었습니다."  );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod03  END]"+event.getTPSVCINFODTO().getSystem_name()  );

      return event;
    }


}


