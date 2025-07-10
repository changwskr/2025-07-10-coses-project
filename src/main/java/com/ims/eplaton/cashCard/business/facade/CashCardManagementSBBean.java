package com.ims.eplaton.cashCard.business.facade;

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

import com.ims.eplaton.cashCard.transfer.*;
import com.ims.eplaton.cashCard.business.cashCardRule.*;
import com.ims.eplaton.cashCard.business.constants.CashCardConstants;
import com.ims.eplaton.cashCard.business.constants.CashCardErrorConstants;
import com.ims.eplaton.cashCard.business.facade.helper.*;
import com.ims.eplaton.cashCard.business.cashCard.model.*;
import com.ims.eplaton.cashCard.business.facade.dao.ICashCardDAO;
import com.ims.eplaton.cashCard.business.facade.dao.CashCardDAOFactory;

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
    private boolean validateAccount(String accountNo, String bankCode)
            throws CosesAppException
    {
        IAccountManagement iDep = EJBUtilFacade.getIAccountManagement();
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

    private CashCardCDTO makeCashCardCDTOForRegister(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO, int lastSequenceNo)
            throws CosesAppException
    {
        cashCardCDTO.setBankCode(commonDTO.getBankCode());
        cashCardCDTO.setBranchCode(commonDTO.getBranchCode());
        cashCardCDTO.setSequenceNo(lastSequenceNo);
        cashCardCDTO.setIncidentCode(CashCardConstants.NORMAL);
        cashCardCDTO.setInvalidAttemptCnt(0);
        cashCardCDTO.setMISSendDate(commonDTO.getBusinessDate());
        cashCardCDTO.setStatus(CashCardConstants.NORMAL_STATUS);
        cashCardCDTO.setType(CashCardConstants.CASH_CARD);
        cashCardCDTO.setFeeWaive(CashCardConstants.FEE_CHARGE);

        return cashCardCDTO;
    }

    private ModifyDTO setModifyDTOForCashCard(CashCardCDTO
            cashCardCDTO, ModifyDTO modifyDTO, CosesCommonDTO commonDTO)
    {
        modifyDTO.setBranchCode(commonDTO.getBranchCode());
        modifyDTO.setTransactionNo(commonDTO.getTransactionNo());
        modifyDTO.setSystem(CommonSystemConstants.SYSTEM_LENDING);
        modifyDTO.setSubSystem(CommonSystemConstants.SYSTEM_LENDING);
        modifyDTO.setRefNo(cashCardCDTO.getCardNumber());
        modifyDTO.setRemark(cashCardCDTO.getAmendReason());
        modifyDTO.setUserId(commonDTO.getUserID());
        modifyDTO.setAmendDate(commonDTO.getSystemDate());
        modifyDTO.setAmendTime(commonDTO.getSystemInTime());
        modifyDTO.setBankCode(commonDTO.getBankCode());
        modifyDTO.setBusinessDate(commonDTO.getBusinessDate());
        return modifyDTO;
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
            //ctx.setRollbackOnly();
            throw cae;
        }

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        // 신규 발행일 경우 seqNo = 1 아니면 나머지는 재발급
        int lastSequenceNo =
                dao.getLastSequenceNoForRegisterCashCard(cashCardCDTO, commonDTO);

        // 신규 등록 또는 재발급일 경우
        if(lastSequenceNo == 1)
        {
            cashCardCDTO = makeCashCardCDTOForRegister(cashCardCDTO,
                    commonDTO, lastSequenceNo);
        }
        else if(lastSequenceNo > 1)
        {
            if(!isRegisterHotCard(cashCardCDTO, commonDTO))
            {
               throw new CosesAppException(new CosesExceptionDetail(
                       CashCardErrorConstants.ERR1980, CashCardErrorConstants.ERR1980_MESSAGE));
            }
            else
            {
                cashCardCDTO = makeCashCardCDTOForRegister(cashCardCDTO,
                        commonDTO, lastSequenceNo);
            }
        }
        else
        {
            throw new CosesAppException(new CosesExceptionDetail(
                    CashCardErrorConstants.ERR1979, CashCardErrorConstants.ERR1979_MESSAGE));
        }

        CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardDDTO 1 : " + cashCardDDTO.toString());
        }

        ICashCardRuleSB iCashCardRule = EJBUtilFacade.getICashCardRuleSB();

        cashCardDDTO = iCashCardRule.getCashCardForRegister(cashCardDDTO, commonDTO);

        cashCardCDTO = DTOConverter.getCashCardCDTO(cashCardCDTO, cashCardDDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug(">>>>>>>CashCardCDTO : " + cashCardCDTO.toString());
        }

        return cashCardCDTO;
    }

    private boolean isRegisterHotCard(CashCardCDTO cashCardCDTO, CosesCommonDTO
            commonDTO) throws CosesAppException
    {
        HotCardCDTO hotCardCDTO = new HotCardCDTO();
        hotCardCDTO.setPrimaryAccountNo(cashCardCDTO.getPrimaryAccountNo());

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        HotCardListCDTO listCDTO = dao.selectHotCardForRegister(hotCardCDTO,
                commonDTO);

        if(listCDTO.size() > 0)
        {
            for(Iterator it = listCDTO.iterator(); it.hasNext();)
            {
                HotCardCDTO rHotCard = (HotCardCDTO)it.next();
                if(rHotCard.getIncidentCode().equals(CashCardConstants.LOST_STOLEN))
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return true;
        }
    }

    public CashCardCDTO registerCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

        // Reference Code를 얻기 위하여 ReferenceCDTO를 작성한다.
        ReferenceCDTO referenceCDTO = new ReferenceCDTO();
        referenceCDTO.setBankCode(commonDTO.getBankCode());
        referenceCDTO.setSystem(CommonSystemConstants.SYSTEM_CASH_CARD_MANAGEMENT);
        referenceCDTO.setSubSystem(CommonSystemConstants.SYSTEM_CASH_CARD_MANAGEMENT);
        referenceCDTO.setBranchCode(commonDTO.getBranchCode());
        referenceCDTO.setBusinessDate(commonDTO.getBusinessDate());
        referenceCDTO.setMiddlefix(commonDTO.getBankCode());

        // ReferenceManagement의 Reference를 얻는다.
        IReferenceManagementSB iReferenceManagementSB = EJBUtilFacade.getIReferenceManagementSB();

        //Log.LNDLogger.debug("ReferenceManagement의 값은:" + iReferenceManagementSB.toString());
        String referenceNo = null;
        try
        {
            // getReferenceNo()를 통하여 ReferenceNo를 얻는다.
            referenceNo =
                    iReferenceManagementSB.getReferenceNo(referenceCDTO);
        }
        catch (RemoteException re)
        {
            throw new EJBException(re);
        }

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("ReferenceNo : " + referenceNo);
        }

        // ReferenceNo를 세팅한다.
        cashCardDDTO.setCardNumber(referenceNo);
        cashCardDDTO.setBankType(CashCardConstants.BANK_TYPE);
        cashCardDDTO.setBranchCode(commonDTO.getBranchCode());
        cashCardDDTO.setRegisterDate(commonDTO.getSystemDate());
        cashCardDDTO.setRegisterTime(commonDTO.getSystemInTime());
        cashCardDDTO.setRegisterBy(commonDTO.getUserID());
        cashCardDDTO.setMISSendDate(commonDTO.getBusinessDate());

        ICashCardRuleSB iCashCardRuleSB = EJBUtilFacade.getICashCardRuleSB();

        cashCardDDTO = iCashCardRuleSB.makeCashCard(cashCardDDTO, commonDTO);

        DTOConverter.getCashCardCDTO(cashCardCDTO, cashCardDDTO);

        return cashCardCDTO;
    }

    public CashCardCDTO modifyCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        // 12/5 modify
        cashCardCDTO.setLastUpdateUserID(commonDTO.getUserID());
        cashCardCDTO.setLastUpdateDate(commonDTO.getSystemDate());
        cashCardCDTO.setLastUpdateTime(commonDTO.getSystemInTime());

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardCDTO : " + cashCardCDTO.toString());
        }

        // CDTO -> ModifyXXXDDTO
        CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardDDTO : " + cashCardDDTO.toString());
        }

        // 원장 변경 거래를 위해 ModifyDTO를 생성
        ModifyDTO modifyDTO = new ModifyDTO();
        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.LNDLogger.debug("ModifyDTO created " + modifyDTO.toString());
        }
        modifyDTO = setModifyDTOForCashCard(cashCardCDTO, modifyDTO, commonDTO);
        // ModifyDTO 내에 있는 Map에 세팅한다.
        modifyDTO.setAfter(cashCardDDTO);

        ICashCardRuleSB iCashCardRule   =   EJBUtilFacade.getICashCardRuleSB();
        cashCardDDTO = iCashCardRule.modifyCashCard(modifyDTO, commonDTO);

        cashCardCDTO = DTOConverter.getCashCardCDTO(cashCardCDTO, cashCardDDTO);

        return cashCardCDTO;
    }

/*    public CashCardCDTO inquiryCashCardForTransfer(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        // 암호화 모듈 생성
        CipherManager cipherManager = CipherManager.getManager();
        // ATM에서 올라온 Password를 얻는다.
        String atmPassword = cashCardCDTO.getPasswordNo();

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        Log.SDBLogger.debug("CashCardCDTO in DAO : " + cashCardCDTO.toString());

        // 원래 저장되어 있는 현금카드의 비밀번호를 decrypt하여 얻는다.
        String cardPassword = cipherManager.decryptAsString(cashCardCDTO.getPasswordNo(),
                CipherManager.CUSTOMER);
        // Check record count
        int recordCount = 0;
        recordCount = checkRecordCount(cashCardCDTO, recordCount);

        Log.SDBLogger.debug("RecordCount : " + recordCount);

        AccountQueryCDTO accountQueryCDTO1 = new AccountQueryCDTO();
        AccountQueryCDTO accountQueryCDTO2 = new AccountQueryCDTO();
        AccountQueryCDTO accountQueryCDTO3 = new AccountQueryCDTO();

        if(recordCount > 0)
        {
            // PrimaryAccountNumber를 조회하기 위하여 CDTO를 만든다.
            accountQueryCDTO1.setAccountNumber(cashCardCDTO.getPrimaryAccountNo());
            accountQueryCDTO1.setBankCode(commonDTO.getBankCode());
            accountQueryCDTO1.setBranchCode(commonDTO.getBranchCode());

            accountQueryCDTO1 = getAccountInfo(accountQueryCDTO1, commonDTO);
        }

        if(recordCount > 1)
        {
            // SecondaryAccountNumber를 조회하기 위하여 CDTO를 만든다.
            accountQueryCDTO2.setAccountNumber(cashCardCDTO.getSecondaryAccountNo());
            accountQueryCDTO2.setBankCode(commonDTO.getBankCode());
            accountQueryCDTO2.setBranchCode(commonDTO.getBranchCode());

            accountQueryCDTO2 = getAccountInfo(accountQueryCDTO2, commonDTO);
        }

        if(recordCount > 2)
        {
            // TenaryAccountNumber를 조회하기 위하여 CDTO를 만든다.
            accountQueryCDTO3.setAccountNumber(cashCardCDTO.getTernaryAccountNo());
            accountQueryCDTO3.setBankCode(commonDTO.getBankCode());
            accountQueryCDTO3.setBranchCode(commonDTO.getBranchCode());

            accountQueryCDTO3 = getAccountInfo(accountQueryCDTO3, commonDTO);
        }

        AccountQueryCDTO targetAccountCDTO = new AccountQueryCDTO();
        targetAccountCDTO.setAccountNumber(cashCardCDTO.getTargetAccountNo());
        targetAccountCDTO.setBankCode(commonDTO.getBankCode());
        targetAccountCDTO.setBranchCode(commonDTO.getBranchCode());

        targetAccountCDTO = getAccountInfo(targetAccountCDTO, commonDTO);

        // cashCardCDTO에 카드 계좌에 대한 정보를 채운다.
        cashCardCDTO.setPrimaryBalance(accountQueryCDTO1.getGrossBalance());
        cashCardCDTO.setPrimaryCurrency(accountQueryCDTO1.getCurrency());

        // Secondary 또는 Tenary Account가 null인 경우에는 Blank로 한다.
        cashCardCDTO = blankAccountNumber(cashCardCDTO);

        // Secondary 또는 Tenary Account의 Currency와 Balance를 공백으로 한다.
        cashCardCDTO = checkBlankSubAccount(cashCardCDTO, accountQueryCDTO2,
                accountQueryCDTO3);
        cashCardCDTO.setRecordCount(recordCount);

        // cashCardCDTO에 targetAccount에 상대고객의 이름을 채운다.
        cashCardCDTO.setTargetCIFName(targetAccountCDTO.getCIFName());

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardCDTO in inquiryCashCard : " +
                                cashCardCDTO.toString());
            Log.SDBLogger.debug("cardPassword : " + cardPassword);
            Log.SDBLogger.debug("atmPassword : " + atmPassword);
        }

        if(commonDTO.getChannelType().equals(Constants.ATM_CHANNEL))
        {
            if(!atmPassword.equals(cardPassword))
            {
                throw new CosesAppException(new CosesExceptionDetail(
                        CashCardErrorConstants.ERR1974,
                        CashCardErrorConstants.ERR1974_MESSAGE));
            }
        }

        // Password No를 암호화 해제하여 할당한다.
        cashCardCDTO.setPasswordNo(cardPassword);

        return cashCardCDTO;
    }*/

    public CashCardCDTO inquiryCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        // 암호화 모듈 생성
        CipherManager cipherManager = CipherManager.getManager();
        // ATM에서 올라온 Password를 얻는다.
        String atmPassword = cashCardCDTO.getPasswordNo();

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        Log.SDBLogger.debug("CashCardCDTO in DAO : " + cashCardCDTO.toString());

        // 원래 저장되어 있는 현금카드의 비밀번호를 decrypt하여 얻는다.
        String cardPassword = cipherManager.decryptAsString(cashCardCDTO.getPasswordNo(),
                                      CipherManager.CUSTOMER);
        // Check record count
        int recordCount = 0;
        recordCount = checkRecordCount(cashCardCDTO, recordCount);

        Log.SDBLogger.debug("RecordCount : " + recordCount);

        AccountQueryCDTO accountQueryCDTO1 = new AccountQueryCDTO();
        AccountQueryCDTO accountQueryCDTO2 = new AccountQueryCDTO();
        AccountQueryCDTO accountQueryCDTO3 = new AccountQueryCDTO();

        if(recordCount > 0)
        {
            // PrimaryAccountNumber를 조회하기 위하여 CDTO를 만든다.
            accountQueryCDTO1.setAccountNumber(cashCardCDTO.getPrimaryAccountNo());
            accountQueryCDTO1.setBankCode(commonDTO.getBankCode());
            accountQueryCDTO1.setBranchCode(commonDTO.getBranchCode());

            accountQueryCDTO1 = getAccountInfo(accountQueryCDTO1, commonDTO);
        }

        if(recordCount > 1)
        {
            // SecondaryAccountNumber를 조회하기 위하여 CDTO를 만든다.
            accountQueryCDTO2.setAccountNumber(cashCardCDTO.getSecondaryAccountNo());
            accountQueryCDTO2.setBankCode(commonDTO.getBankCode());
            accountQueryCDTO2.setBranchCode(commonDTO.getBranchCode());

            accountQueryCDTO2 = getAccountInfo(accountQueryCDTO2, commonDTO);
        }

        if(recordCount > 2)
        {
            // TenaryAccountNumber를 조회하기 위하여 CDTO를 만든다.
            accountQueryCDTO3.setAccountNumber(cashCardCDTO.getTernaryAccountNo());
            accountQueryCDTO3.setBankCode(commonDTO.getBankCode());
            accountQueryCDTO3.setBranchCode(commonDTO.getBranchCode());

            accountQueryCDTO3 = getAccountInfo(accountQueryCDTO3, commonDTO);
        }

        cashCardCDTO.setPrimaryBalance(accountQueryCDTO1.getGrossBalance());
        cashCardCDTO.setPrimaryCurrency(accountQueryCDTO1.getCurrency());

        // Secondary 또는 Tenary Account가 null인 경우에는 Blank로 한다.
        cashCardCDTO = blankAccountNumber(cashCardCDTO);

        // Secondary 또는 Tenary Account의 Currency와 Balance를 공백으로 한다.
        cashCardCDTO = checkBlankSubAccount(cashCardCDTO, accountQueryCDTO2,
                accountQueryCDTO3);
        cashCardCDTO.setRecordCount(recordCount);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardCDTO in inquiryCashCard : " +
                                cashCardCDTO.toString());
            Log.SDBLogger.debug("cardPassword : " + cardPassword);
            Log.SDBLogger.debug("atmPassword : " + atmPassword);
        }

        if(commonDTO.getChannelType().equals(Constants.ATM_CHANNEL))
        {
            if(!atmPassword.equals(cardPassword))
            {
                throw new CosesAppException(new CosesExceptionDetail(
                        CashCardErrorConstants.ERR1974,
                        CashCardErrorConstants.ERR1974_MESSAGE));
            }
        }

        // Password No를 암호화 해제하여 할당한다.
        cashCardCDTO.setPasswordNo(cardPassword);

        return cashCardCDTO;
    }

    private CashCardCDTO checkBlankSubAccount(CashCardCDTO cashCardCDTO,
                                      AccountQueryCDTO accountQueryCDTO2,
                                      AccountQueryCDTO accountQueryCDTO3)
    {
        if(accountQueryCDTO2.getGrossBalance() != null)
        {
            cashCardCDTO.setSecondaryBalance(accountQueryCDTO2.getGrossBalance());
        }
        else
        {
            cashCardCDTO.setSecondaryBalance(Constants.ZERO);
        }

        if(accountQueryCDTO2.getCurrency() != null)
        {
            cashCardCDTO.setSecondaryCurrency(accountQueryCDTO2.getCurrency());
        }
        else
        {
            cashCardCDTO.setSecondaryCurrency(Constants.BLANK);
        }

        if(accountQueryCDTO3.getGrossBalance() != null)
        {
            cashCardCDTO.setTenaryBalance(accountQueryCDTO3.getGrossBalance());
        }
        else
        {
            cashCardCDTO.setTenaryBalance(Constants.ZERO);
        }

        if(accountQueryCDTO3.getCurrency() != null)
        {
            cashCardCDTO.setTenaryCurrency(accountQueryCDTO3.getCurrency());
        }
        else
        {
            cashCardCDTO.setTenaryCurrency(Constants.BLANK);
        }
        return cashCardCDTO;
    }

    private CashCardCDTO blankAccountNumber(CashCardCDTO cashCardCDTO)
    {
        if(cashCardCDTO.getSecondaryAccountNo() == null)
        {
            cashCardCDTO.setSecondaryAccountNo(Constants.BLANK);
        }

        if(cashCardCDTO.getTernaryAccountNo() == null)
        {
            cashCardCDTO.setTernaryAccountNo(Constants.BLANK);
        }
        return cashCardCDTO;
    }

    private AccountQueryCDTO getAccountInfo(AccountQueryCDTO accountQueryCDTO,
                                CosesCommonDTO commonDTO) throws CosesAppException
    {
        try
        {
            IAccountManagement iDep = EJBUtilFacade.getIAccountManagement();
            accountQueryCDTO = iDep.getAccountInfo(accountQueryCDTO, commonDTO);
        }
        catch (RemoteException ex)
        {
            throw new EJBException(ex);
        }
        return accountQueryCDTO;
    }

    private int checkRecordCount(CashCardCDTO cashCardCDTO, int recordCount)
    {
        if(cashCardCDTO.getPrimaryAccountNo() != null)
        {
            recordCount = increaseRecordCount(cashCardCDTO.getPrimaryAccountNo(), recordCount);
        }

        if(cashCardCDTO.getSecondaryAccountNo() != null)
        {
            recordCount = increaseRecordCount(cashCardCDTO.getSecondaryAccountNo(), recordCount);
        }

        if(cashCardCDTO.getTernaryAccountNo() != null)
        {
            recordCount = increaseRecordCount(cashCardCDTO.getTernaryAccountNo(), recordCount);
        }
        return recordCount;
    }

    private int increaseRecordCount(String accountNumber, int recordCount)
    {
        if(!"".equals(accountNumber))
        {
            ++recordCount;
        }
        return recordCount;
    }

    public CashCardCDTO cancelCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

        cashCardDDTO.setLastUpdateDate(commonDTO.getSystemDate());
        cashCardDDTO.setLastUpdateTime(commonDTO.getSystemInTime());
        cashCardDDTO.setLastUpdateUserID(commonDTO.getUserID());
        cashCardDDTO.setExpiryDate(commonDTO.getBusinessDate());
        cashCardDDTO.setMISSendDate(commonDTO.getBusinessDate());
        cashCardDDTO.setStatus(CashCardConstants.CLOSED_STATUS);

        // Password No를 암호화 해제하여 할당한다.
        CipherManager cipherManager = CipherManager.getManager();

        cashCardDDTO.setPasswordNo(cipherManager.encryptAsString(cashCardDDTO.getPasswordNo(),
                                      CipherManager.CUSTOMER));

        ICashCardRuleSB iCashCardRuleSB = EJBUtilFacade.getICashCardRuleSB();
        iCashCardRuleSB.setCashCard(cashCardDDTO, commonDTO);

        cashCardCDTO = DTOConverter.getCashCardCDTO(cashCardCDTO, cashCardDDTO);

        return cashCardCDTO;
    }

    public HotCardListCDTO queryForRegisterHotCard(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        HotCardListCDTO hotCardListCDTO =
                dao.queryForHotCardList(hotCardCDTO, commonDTO);

        return hotCardListCDTO;
    }

    public HotCardListCDTO registerHotCard(HotCardListCDTO hotCardListCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        HotCardListCDTO beforeHotCardListCDTO =
                dao.queryForHotCardList((HotCardCDTO)hotCardListCDTO.get(0), commonDTO);
        int listSize = hotCardListCDTO.size();

        int lastSequenceNo = dao.getLastSequenceNoForRegisterHotCard(
                (HotCardCDTO)hotCardListCDTO.get(0), commonDTO);
        int basicSeq = lastSequenceNo - 1;

        ICashCardRuleSB iCashCardRuleSB = EJBUtilFacade.getICashCardRuleSB();
        for(int i = basicSeq; i < listSize; i++)
        {
            HotCardCDTO hotCardCDTO = (HotCardCDTO)hotCardListCDTO.get(i);

            CashCardCDTO cashCardCDTO = new CashCardCDTO();
            cashCardCDTO.setCardNumber(hotCardCDTO.getCardNumber());

            cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

            if("".equals(hotCardCDTO.getPrimaryAccountNo()))
            {
                hotCardCDTO.setPrimaryAccountNo(cashCardCDTO.getPrimaryAccountNo());
            }

            HotCardDDTO hotCardDDTO = DTOConverter.getHotCardDDTO(hotCardCDTO);

            iCashCardRuleSB.registerHotCard(hotCardDDTO, commonDTO);
        }
        return hotCardListCDTO;
    }

    public HotCardListCDTO releasedHotCard(HotCardListCDTO hotCardListCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("=============================================");
            Log.SDBLogger.debug("HOTCARD LIST : " + hotCardListCDTO.toString());
        }

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        //HotCardListCDTO beforeHotCardListCDTO =
        //        dao.queryForHotCardList((HotCardCDTO)hotCardListCDTO.get(0), commonDTO);
        int listSize = hotCardListCDTO.size();

        Log.SDBLogger.debug("=========>>> size : " + listSize);

        int lastSequenceNo = dao.getLastSequenceNoForRegisterHotCard(
                (HotCardCDTO)hotCardListCDTO.get(0), commonDTO);
        int basicSeq = lastSequenceNo - 2;

        ICashCardRuleSB iCashCardRuleSB = EJBUtilFacade.getICashCardRuleSB();

        HotCardCDTO hotCardCDTO = (HotCardCDTO)hotCardListCDTO.get(basicSeq);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("HotCardCDTO : " + hotCardCDTO.toString());
        }

        HotCardDDTO hotCardDDTO = DTOConverter.getHotCardDDTO(hotCardCDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("HotCardDDTO : " + hotCardDDTO.toString());
        }

        iCashCardRuleSB.releaseHotCard(hotCardDDTO, commonDTO);
        //}
        return hotCardListCDTO;
    }

    public HotCardListCDTO inquiryHotCard(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        HotCardListCDTO hotCardListCDTO =
                dao.queryForHotCardList(hotCardCDTO, commonDTO);

        CashCardCDTO cashCardCDTO = new CashCardCDTO();
        cashCardCDTO.setCardNumber(hotCardCDTO.getCardNumber());

        cashCardCDTO =
                dao.queryForCashCard(cashCardCDTO, commonDTO);

        if(hotCardListCDTO.size() == 0)
        {
            hotCardCDTO.setSequenceNo(1);
            hotCardListCDTO.add(hotCardCDTO);
        }

        return hotCardListCDTO;
    }

    public void validateAccount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Log.SDBLogger.debug("validateAccount Call!!!");
        CashCardDDTO cashCardDDTO = new CashCardDDTO();
        cashCardDDTO.setCardNumber(validateTransactionCDTO.getCardNumber());
        cashCardDDTO.setBankCode(commonDTO.getBankCode());
        cashCardDDTO.setBranchCode(commonDTO.getBranchCode());

        ICashCardRuleSB iCashCardRuleSB = EJBUtilFacade.getICashCardRuleSB();
        cashCardDDTO = iCashCardRuleSB.getCashCardByCardNo(cashCardDDTO, commonDTO);

        if(cashCardDDTO.getInvalidAttemptCnt() > 3)
        {
            throw new CosesAppException(new CosesExceptionDetail(
                            CashCardErrorConstants.ERR1977, CashCardErrorConstants.ERR1977_MESSAGE));
        }

        // Password No를 암호화 해제하여 할당한다.
        CipherManager cipherManager = CipherManager.getManager();

        cashCardDDTO.setPasswordNo(cipherManager.decryptAsString(cashCardDDTO.getPasswordNo(),
                                      CipherManager.CUSTOMER));

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardCDTO : " + cashCardDDTO.getPasswordNo());
            Log.SDBLogger.debug("ValidateTransactionCDTO : "
                                + validateTransactionCDTO.getPasswordNo());
        }
        // 정상적인 카드 고객인지를 확인한다.
        validateAccountNumber(validateTransactionCDTO, cashCardDDTO);

        // Cancel 거래로 올라올 경우에는 비밀 번호 체크를 하지 않도록 한다.
        if(!validateTransactionCDTO.getCancelYN().equals(CashCardConstants.BOOLEAN_TRUE))
        {
            // 비밀 번호를 체크한다.
            if(!validateTransactionCDTO.getPasswordNo().equals(cashCardDDTO.getPasswordNo()))
            {
                ICashCardManagementSB iCashCardManagementSB = null;
                CashCardCDTO cashCardCDTO = new CashCardCDTO();
                try
                {
                    CashCardManagementSBHome cashCardHome =
                            (CashCardManagementSBHome)JNDIService.getInstance().lookup(
                            CashCardManagementSBHome.class);
                    iCashCardManagementSB = cashCardHome.create();

                    cashCardCDTO = DTOConverter.getCashCardCDTO(
                            cashCardCDTO, cashCardDDTO);
                    Log.SDBLogger.debug("================================");
                    Log.SDBLogger.debug("UpdateInvalidAttemptCount Call!!");
                    Log.SDBLogger.debug("================================");
                    cashCardCDTO = iCashCardManagementSB.updateInvaildAttemptCount(cashCardCDTO, commonDTO);
                }
                catch (RemoteException re)
                {
                    throw new EJBException(re);
                }
                catch (CreateException ce)
                {
                    throw new EJBException(ce);
                }
                catch (NamingException ne)
                {
                    throw new EJBException(ne);
                }
                cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

                // 패스워드를 2회 이상 틀린 경우에는 카드를 완전히 사용할 수 없도록 한다.
                if(cashCardDDTO.getInvalidAttemptCnt() > 3)
                {
                    throw new CosesAppException(new CosesExceptionDetail(
                            CashCardErrorConstants.ERR1977, CashCardErrorConstants.ERR1977_MESSAGE));
                }
                else
                {
                    throw new CosesAppException(new CosesExceptionDetail(
                            CashCardErrorConstants.ERR1974, CashCardErrorConstants.ERR1974_MESSAGE));
                }
            }
            else
            {
                // 비밀번호 오류가 2회 내에서 정상적인 입력을 했을 경우에는
                // 이전에 카운드를 다시 0으로 한다.
                if(cashCardDDTO.getInvalidAttemptCnt() < 3)
                {
                    cashCardDDTO.setInvalidAttemptCnt(0);
                }
            }
        }
    }

    private void validateAccountNumber(ValidateTransactionCDTO validateTransactionCDTO, CashCardDDTO cashCardDDTO) throws CosesAppException
    {
        Log.SDBLogger.debug("=====  Validate Account Call!!  ====");
        // ATM에서 올라온 계좌가 정상적인 상태의 Deposit 계좌로 존재하는지를 확인한다.
        if(!((validateTransactionCDTO.getPrimaryAccountNo().equals(
                cashCardDDTO.getPrimaryAccountNo()) ||
                validateTransactionCDTO.getPrimaryAccountNo().equals(
                cashCardDDTO.getSecondaryAccountNo()) ||
                validateTransactionCDTO.getPrimaryAccountNo().equals(
                cashCardDDTO.getTernaryAccountNo()))))
        {
            throw new CosesAppException(new CosesExceptionDetail(
                        CashCardErrorConstants.ERR1978, CashCardErrorConstants.ERR1978_MESSAGE));
        }
    }

    public CashCardCDTO updateInvaildAttemptCount(CashCardCDTO cashCardCDTO, CosesCommonDTO commonDTO)
            throws CosesAppException
    {
        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("===============================================");
            Log.SDBLogger.debug("Password #3 : " + cashCardCDTO.getPasswordNo());
            Log.SDBLogger.debug("InvalidAttemptCnt : " + cashCardCDTO.getInvalidAttemptCnt());
        }
        CipherManager cipherManager1 = CipherManager.getManager();
        cashCardCDTO.setPasswordNo(cipherManager1.encryptAsString(cashCardCDTO.getPasswordNo(),
                CipherManager.CUSTOMER));
        cashCardCDTO.setInvalidAttemptCnt(cashCardCDTO.getInvalidAttemptCnt() + 1);
        cashCardCDTO.setMISSendDate(commonDTO.getBusinessDate());
        Log.SDBLogger.debug("InvalidAttemptCnt#3 : " + cashCardCDTO.getInvalidAttemptCnt());
        Log.SDBLogger.debug("===================================================");

        CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

        ICashCardRuleSB iCashCardRuleSB = EJBUtilFacade.getICashCardRuleSB();
        iCashCardRuleSB.setCashCard(cashCardDDTO, commonDTO);

        return cashCardCDTO;
    }

    public void validateCard(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Log.SDBLogger.debug("ValidateCard Call!!!");
        CashCardCDTO cashCardCDTO = new CashCardCDTO();
        cashCardCDTO.setCardNumber(validateTransactionCDTO.getCardNumber());
        cashCardCDTO.setBankCode(commonDTO.getBankCode());
        //cashCardCDTO.setBranchCode(commonDTO.getBranchCode());

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("validateTransactionCDTO : " + validateTransactionCDTO.toString());
            Log.SDBLogger.debug("cashCardCDTO : " + cashCardCDTO.toString());
        }

        if(!cashCardCDTO.getStatus().equals(CashCardConstants.NORMAL_STATUS))
        {
            throw new CosesAppException(new CosesExceptionDetail(
                    CashCardErrorConstants.ERR1972, CashCardErrorConstants.ERR1972_MESSAGE));
        }

        if(!cashCardCDTO.getIncidentCode().equals(CashCardConstants.NORMAL))
        {
            throw new CosesAppException(new CosesExceptionDetail(
                    CashCardErrorConstants.ERR1973, CashCardErrorConstants.ERR1973_MESSAGE));
        }
    }

    public boolean validateDailyLimitAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Log.SDBLogger.debug("ValidateDailyLimitAmount Call!!!");
        CashCardCDTO cashCardCDTO = new CashCardCDTO();
        cashCardCDTO.setCardNumber(validateTransactionCDTO.getCardNumber());
        cashCardCDTO.setBankCode(commonDTO.getBankCode());
        //cashCardCDTO.setBranchCode(commonDTO.getBranchCode());

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("TransactionAmount : " + validateTransactionCDTO.getTransactionAmount());
            Log.SDBLogger.debug("DailyLimitAmount : " + cashCardCDTO.getDailyLimitAmount());
            Log.SDBLogger.debug("This is a validateDailyLimitAmount!!!");
        }

        if(validateTransactionCDTO.getTransactionAmount().compareTo(
                CashCardConstants.SINGLE_WITHDRAW_LIMIT) > 0)
        {
            Log.SDBLogger.debug("Transaction Amount exceed!!!");
            return false;
        }

        return true;
    }

    public boolean validateDailyAccumAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Log.SDBLogger.debug("validateDailyAccumAmount Call!!!");
        CashCardCDTO cashCardCDTO = new CashCardCDTO();
        cashCardCDTO.setCardNumber(validateTransactionCDTO.getCardNumber());
        cashCardCDTO.setBankCode(commonDTO.getBankCode());
        //cashCardCDTO.setBranchCode(commonDTO.getBranchCode());

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        if(validateTransactionCDTO.getTransactionAmount().compareTo(
                cashCardCDTO.getDailyAccumAmount()) > 0 &&
           validateTransactionCDTO.getCancelYN().equals(CashCardConstants.BOOLEAN_FALSE))
        {
            Log.SDBLogger.debug("Transaction Amount exceed22222!!!");
            return false;
        }
        else if(validateTransactionCDTO.getCancelYN().equals(CashCardConstants.BOOLEAN_TRUE))
        {
            cashCardCDTO.setDailyAccumAmount(
                    cashCardCDTO.getDailyAccumAmount().add(
                    validateTransactionCDTO.getTransactionAmount()));
            CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

            ICashCardRuleSB iCashCardRule = EJBUtilFacade.getICashCardRuleSB();
            iCashCardRule.setCashCard(cashCardDDTO, commonDTO);

            return true;
        }
        else
        {
            if(Log.SDBLogger.isDebugEnabled())
            {
                Log.SDBLogger.debug("DailyAccumAmount before : "
                                    + cashCardCDTO.getDailyAccumAmount());
            }

            cashCardCDTO.setDailyAccumAmount(
                    cashCardCDTO.getDailyAccumAmount().subtract(
                    validateTransactionCDTO.getTransactionAmount()));
            CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

            ICashCardRuleSB iCashCardRule = EJBUtilFacade.getICashCardRuleSB();
            iCashCardRule.setCashCard(cashCardDDTO, commonDTO);

            if(Log.SDBLogger.isDebugEnabled())
            {
                Log.SDBLogger.debug("DailyAccumAmount after : "
                                    + cashCardDDTO.getDailyAccumAmount());
            }

            return true;
        }
    }

    public boolean validateDailyTrfLimitAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardCDTO cashCardCDTO = new CashCardCDTO();
        cashCardCDTO.setCardNumber(validateTransactionCDTO.getCardNumber());
        cashCardCDTO.setBankCode(commonDTO.getBankCode());
        //cashCardCDTO.setBranchCode(commonDTO.getBranchCode());

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("TransactionAmount : " + validateTransactionCDTO.getTransactionAmount());
            Log.SDBLogger.debug("DailyLimitAmount : " + cashCardCDTO.getDailyTrfLimitAmount());
            Log.SDBLogger.debug("This is a validateDailyLimitAmount!!!");
        }

        if(validateTransactionCDTO.getTransactionAmount().compareTo(
                CashCardConstants.SINGLE_TRANSFER_LIMIT) > 0)
        {
            Log.SDBLogger.debug("Transfer Transaction Amount exceed!!!");
            return false;
        }
        return true;
    }

    public boolean validateDailyTrfAccumAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardCDTO cashCardCDTO = new CashCardCDTO();
        cashCardCDTO.setCardNumber(validateTransactionCDTO.getCardNumber());
        cashCardCDTO.setBankCode(commonDTO.getBankCode());
        //cashCardCDTO.setBranchCode(commonDTO.getBranchCode());

        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        cashCardCDTO = dao.queryForCashCard(cashCardCDTO, commonDTO);

        if(validateTransactionCDTO.getTransactionAmount().compareTo(
                cashCardCDTO.getDailyTrfAccumAmount()) > 0)
        {
            return false;
        }
        else
        {
            if(Log.SDBLogger.isDebugEnabled())
            {
                Log.SDBLogger.debug("DailyAccumAmount before : "
                                    + cashCardCDTO.getDailyTrfAccumAmount());
            }

            cashCardCDTO.setDailyTrfAccumAmount(
                    cashCardCDTO.getDailyTrfAccumAmount().subtract(
                    validateTransactionCDTO.getTransactionAmount()));

            CashCardDDTO cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardCDTO);

            ICashCardRuleSB iCashCardRule = EJBUtilFacade.getICashCardRuleSB();
            iCashCardRule.setCashCard(cashCardDDTO, commonDTO);

            if(Log.SDBLogger.isDebugEnabled())
            {
                Log.SDBLogger.debug("DailyAccumAmount after : "
                                    + cashCardDDTO.getDailyTrfAccumAmount());
            }

            return true;
        }
    }
    public CashCardPageCDTO listCashCardNumber(CashCardConditionCDTO queryCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        CashCardPageCDTO cashCardPageCDTO = dao.listForCashCardNumber(queryCDTO, commonDTO);


        return cashCardPageCDTO;
    }

    public CashCardPageCDTO listCashCard(CashCardConditionCDTO queryCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        CashCardPageCDTO cashCardPageCDTO = dao.listForCashCard(queryCDTO, commonDTO);

        return cashCardPageCDTO;
    }

    public HotCardPageCDTO listHotCard(HotCardQueryConditionCDTO queryCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        HotCardPageCDTO hotCardPageCDTO = dao.listForHotCard(queryCDTO, commonDTO);

        return hotCardPageCDTO;
    }

    public CashCardPageCDTO listInvalidAttemptCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();

        CashCardPageCDTO cashCardPageCDTO = dao.listInvalidAttemptCard(conditionCDTO,
                commonDTO);

        return cashCardPageCDTO;
    }

    public BatchJobProcessorResultDTO processBatchTest(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        BatchJobProcessorResultDTO dto = new BatchJobProcessorResultDTO();
        dto.setResultFlag(false);
        try
        {
            dao.testBatch(cashCardCDTO, commonDTO);
            dto.setResultFlag(true);
        }
        catch (Exception ex)
        {
            dto.setResultFlag(false);

            return dto;
        }
        return dto;
    }

    public BatchJobProcessorResultDTO initialDailyAccumBalance(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardDAO dao = CashCardDAOFactory.getInstance().getCashCardDAO();
        BatchJobProcessorResultDTO dto = new BatchJobProcessorResultDTO();
        dto.setResultFlag(false);
        try {
            Log.SDBLogger.debug("initialDailyAccumBalance Start!!");
            dao.updateAccumBalance(cashCardCDTO, commonDTO);
            dto.setResultFlag(true);
            return dto;
        }
        catch (Exception ex) {
            dto.setResultFlag(false);

            return dto;
        }
    }
}