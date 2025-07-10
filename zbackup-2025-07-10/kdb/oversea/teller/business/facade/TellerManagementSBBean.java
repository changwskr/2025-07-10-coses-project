package com.kdb.oversea.teller.business.facade;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.deposit.business.facade.*;
import com.chb.coses.deposit.transfer.*;

import com.kdb.oversea.eplatonframework.transfer.*;
import com.kdb.oversea.foundation.logej.*;
import com.kdb.oversea.eplatonframework.transfer.EPLcommonCDTO;

public class TellerManagementSBBean implements ITellerManagementSB
{
    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
    }

    public EPlatonEvent callmethod01(EPlatonEvent event)
        throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO rescdto = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod01 START]");

      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO)event.getRequest();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod01  END]");
      return event;
    }

    public EPlatonEvent callmethod02(EPlatonEvent event) throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO rescdto = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod02 START]");

      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO)event.getRequest();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[callmethod02  END]");
      return event;
    }


}


