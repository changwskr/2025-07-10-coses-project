package com.ims.eplaton.eplatonFKC.othersystem.operation.cashcard;


import java.rmi.*;
import java.text.*;
import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;

import com.chb.coses.foundation.calendar.CalendarUtil;
import com.chb.coses.foundation.jndi.JNDIService;
import com.chb.coses.foundation.log.Log;
import com.chb.coses.foundation.security.CipherManager;
import com.chb.coses.framework.constants.Constants;
import com.chb.coses.cosesFramework.transfer.ModifyDTO;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.chb.coses.cosesFramework.transfer.BatchJobProcessorResultDTO;
import com.ims.eplaton.cashCard.business.facade.helper.*;

import com.chb.coses.cashCard.transfer.*;
import com.chb.coses.cashCard.business.constants.CashCardConstants;
import com.chb.coses.cashCard.business.constants.CashCardErrorConstants;

import com.chb.coses.common.business.constants.CommonErrorMessageConstants;
import com.chb.coses.common.business.constants.CommonSystemConstants;
import com.chb.coses.deposit.business.facade.*;
import com.chb.coses.deposit.transfer.*;
import com.chb.coses.reference.transfer.*;
import com.chb.coses.reference.business.facade.*;
import javax.naming.*;

import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.logej.*;

public class CashCardManagementSBBean implements ICashCardManagementSB
{
    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
    }
//========================    Private Method Area     ========================//


    public EPlatonEvent queryForRegisterCashCard(EPlatonEvent eplatonevent)
        throws CosesAppException
    {
      EPlatonCommonDTO commonDTO = null;
      TPSVCINFODTO tpsvcinfo = null;
      AccountQueryCDTO rescdto = null;
      AccountQueryCDTO reqcdto = new AccountQueryCDTO();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplatonevent,"==================[DED0021000 START]");

      commonDTO = (EPlatonCommonDTO)eplatonevent.getCommon();
      tpsvcinfo = eplatonevent.getTPSVCINFODTO();
      rescdto = (AccountQueryCDTO)eplatonevent.getRequest();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplatonevent,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplatonevent,"1");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplatonevent,"1");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplatonevent,"1");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      eplatonevent.setResponse(reqcdto);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplatonevent,"==================[DED0021000  END]");
      return eplatonevent;
    }

    private boolean validateAccount(String accountNo, String bankCode)
            throws CosesAppException
    {
        //IAccountManagement iDep = EJBUtilFacade.getIAccountManagement();
        IAccountManagement iDep = null;
        boolean normalCheck = false;
        try
        {
            normalCheck = iDep.validateAccount(accountNo, bankCode);
        }
        catch (RemoteException ex)
        {
            throw new EJBException(ex);
        }
        return normalCheck;
    }

    public CashCardCDTO queryForRegisterCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        try
        {
            if(!validateAccount(cashCardCDTO.getPrimaryAccountNo(), commonDTO.getBankCode()))
            {
                throw new CosesAppException(new CosesExceptionDetail(
                        CashCardErrorConstants.ERR1978, CashCardErrorConstants.ERR1978_MESSAGE));
            }
        }
        catch(CosesAppException cae)
        {
            throw cae;
        }

        return cashCardCDTO;
    }

    public CashCardCDTO registerCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        IReferenceManagementSB iReferenceManagementSB = EJBUtilFacade.getIReferenceManagementSB();

        //Log.LNDLogger.debug("ReferenceManagement의 값은:" + iReferenceManagementSB.toString());
        String referenceNo = null;
        try
        {
            // getReferenceNo()를 통하여 ReferenceNo를 얻는다.
            referenceNo =
                    iReferenceManagementSB.getReferenceNo(null);
        }
        catch (RemoteException re)
        {
            throw new EJBException(re);
        }


        return cashCardCDTO;
    }

    public CashCardCDTO modifyCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {

        return cashCardCDTO;
    }

    public CashCardCDTO inquiryCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
      return null;
    }
}
