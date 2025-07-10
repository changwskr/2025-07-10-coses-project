package com.chb.coses.eplatonFMK.othersystem.operation;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.IEvent;

import com.chb.coses.eplatonFMK.business.operation.IBizOperation;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.helper.logej.LOGEJ;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.chb.coses.eplatonFMK.transfer.*;
import com.chb.coses.eplatonFMK.business.helper.TPMSVCAPI;

public class COMMO1000 implements IBizOperation
{
  public EPlatonEvent act(EPlatonEvent event)
  {
    EPlatonEvent tpmevent = new EPlatonEvent();
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    AccountQueryCDTO rescdto = null;
    AccountQueryCDTO reqcdto = new AccountQueryCDTO();

    try{
      LOGEJ.getInstance().printf((EPlatonEvent)event,"==================[COMO1000 START]");

      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (AccountQueryCDTO)event.getRequest();

      LOGEJ.getInstance().printf((EPlatonEvent)event,"신규계좌에 대한 정보를 셋팅한다");
      reqcdto.setAccountNumber("0001100100000048");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("9999999999999999");

      ////////////////////////////////////////////////////////////////////////////
      // TPSsendrecv TEST
      ////////////////////////////////////////////////////////////////////////////
      EPlatonCommonDTO tpmcommonDTO = tpmevent.getCommon() ;
      TPSVCINFODTO tpmtpsvcinfo = tpmevent.getTPSVCINFODTO() ;
      tpmevent.setRequest(rescdto);

      tpmcommonDTO.setBankCode("11");
      tpmcommonDTO.setChannelType("03");
      tpmcommonDTO.setBranchCode("99");
      tpmcommonDTO.setEventNo("12345678");
      tpmcommonDTO.setSystemDate("20031031");
      tpmcommonDTO.setUserID("wsjang");
      tpmcommonDTO.setReqName("com.chb.coses.eplatonFMK.business.delegate.action.DepositBizAction");
      tpmcommonDTO.setgetIPAddress("172.21.111.110");


      tpmtpsvcinfo.setSystem_name("Deposit");
      tpmtpsvcinfo.setAction_name("com.chb.coses.eplatonFMK.business.delegate.action.CashCardBizAction");
      tpmtpsvcinfo.setOperation_name("com.chb.coses.eplatonFMK.othersystem.operation.DED0021000");
      tpmtpsvcinfo.setTpfq("100");
      tpmtpsvcinfo.setErrorcode("IZZ000");
      tpmtpsvcinfo.setTx_timer("030");
      tpmtpsvcinfo.setHostseq(tpsvcinfo.getHostseq());
      tpmtpsvcinfo.setOrgseq(tpsvcinfo.getOrgseq());


      tpmevent = TPMSVCAPI.getInstance().TPSsendrecv("CashCard","Deposit",
          "com.chb.coses.eplatonFMK.business.delegate.action.DepositBizAction",
          "com.chb.coses.eplatonFMK.othersystem.operation.DED0021000",
          tpmevent);

      LOGEJ.getInstance().printf((EPlatonEvent)event,"3333333333333");
      ////////////////////////////////////////////////////////////////////////////

      switch( tpmevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'I' :
          event.setResponse(tpmevent.getResponse() );
          break;
        case 'E' :
          event.getTPSVCINFODTO().setErrorcode(tpmevent.getTPSVCINFODTO().getErrorcode() );
          String errorcode = "EFWK0047"+"|"+tpsvcinfo.getErrorcode();
          tpsvcinfo.setErrorcode(errorcode);
          tpsvcinfo.setError_message(this.getClass().getName()+ ".COMMO1000():TPSsendrecv Fail");
          break;
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      String errorcode = "EFWK0048"+"|"+tpsvcinfo.getErrorcode();
      tpsvcinfo.setErrorcode(errorcode);
      tpsvcinfo.setError_message(this.getClass().getName()+ ".COMMO1000():TPSsendrecv Fail");
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,ex);
    }

    ////////////////////////////////////////////////////////////////////////////

    LOGEJ.getInstance().printf((EPlatonEvent)event,"==================[COMO1000  END]"+event.getTPSVCINFODTO().getSystem_name()  );
    return event;

  }
}

