package com.ims.oversea.deposit.business.facade;

import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.deposit.business.facade.*;
import com.chb.coses.deposit.transfer.*;
import com.ims.oversea.eplatonframework.transfer.EPLcommonCDTO;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.logej.*;
import com.ims.oversea.framework.transaction.bean.AbstractBIZTCF;

public class DepositManagementSBean extends AbstractBIZTCF implements IDepositManagementSB
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

      printf(1,"==================[callmethod01 START]");

      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO)event.getRequest();

      printf(1,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
      printf(1,"1");
      printf(1,"1");
      printf(1,"1");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);
      printf(1,"==================[callmethod01  END]");
      return event;
    }

    public EPlatonEvent callmethod02(EPlatonEvent event) throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      EPLcommonCDTO rescdto = null;
      EPLcommonCDTO reqcdto = new EPLcommonCDTO();

      printf(1,"==================[callmethod02 START]");

      commonDTO = (EPlatonCommonDTO)event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO)event.getRequest();

      printf(1,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
      printf(1,"1");
      printf(1,"1");
      printf(1,"1");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);
      printf(1,"==================[callmethod02  END]");
      return event;
    }


}
