
package com.kdb.oversea.cashCard.business.cashCardRule;

import java.rmi.RemoteException;
import javax.ejb.*;

import com.chb.coses.foundation.log.Log;
import com.chb.coses.foundation.security.CipherManager;
import com.chb.coses.cosesFramework.transfer.ModifyDTO;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;

import com.kdb.oversea.cashCard.business.constants.CashCardConstants;
import com.kdb.oversea.cashCard.business.cashCard.model.*;
import com.kdb.oversea.cashCard.business.cashCard.ICashCardSB;
import com.kdb.oversea.cashCard.business.cashCardRule.helper.*;

import com.chb.coses.reference.transfer.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.business.constants.*;
import com.chb.coses.reference.business.facade.*;

public class CashCardRuleSBBean
        extends com.chb.coses.framework.business.AbstractSessionBean
        implements ICashCardRuleSB
{
    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
    }
//=============================================================================//
    public CashCardDDTO getCashCardInfo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();
        cashCardDDTO = iCashCardSB.getCashCardInfo(cashCardDDTO, commonDTO);

        // Password No를 암호화 해제하여 할당한다.
        CipherManager cipherManager = CipherManager.getManager();

        cashCardDDTO.setPasswordNo(cipherManager.decryptAsString(cashCardDDTO.getPasswordNo(),
                                      CipherManager.CUSTOMER));

        return cashCardDDTO;
    }

    public CashCardDDTO getCashCardByCardNo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();
        cashCardDDTO = iCashCardSB.findCashCardInfoByCardNo(cashCardDDTO, commonDTO);

        return cashCardDDTO;
    }

    public CashCardDDTO getCashCardForRegister(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        // System Parameter에 등록된 Setup 정보를 가져온다.
        SystemParameterCDTO systemParameterCDTO1 = new SystemParameterCDTO();
        systemParameterCDTO1.setKind(CashCardConstants.DAILY_LIMIT_INFO); // "CARDLIMIT"

        systemParameterCDTO1 = getSystemParameter(systemParameterCDTO1, commonDTO);

        SystemParameterCDTO systemParameterCDTO2 = new SystemParameterCDTO();
        systemParameterCDTO2.setKind(CashCardConstants.DAILY_FEE_LIMIT_INFO); // "CARDFEELIM"

        systemParameterCDTO2 = getSystemParameter(systemParameterCDTO2, commonDTO);

        SystemParameterCDTO systemParameterCDTO3 = new SystemParameterCDTO();
        systemParameterCDTO3.setKind(CashCardConstants.DAILY_ACCUM_INFO); // "CARDACCUM"

        systemParameterCDTO3 = getSystemParameter(systemParameterCDTO3, commonDTO);

        // 일일 지급 제한 금액/1회 지급 제한 금액/지급 가능 통화 등의 내용을 입력한다.
        cashCardDDTO.setDailyLimitCcy(systemParameterCDTO1.getCharValue1());
        cashCardDDTO.setDailyLimitAmount(systemParameterCDTO3.getFloatValue1());
        cashCardDDTO.setDailyTrfLimitCcy(systemParameterCDTO1.getCharValue2());
        cashCardDDTO.setDailyTrfLimitAmount(systemParameterCDTO3.getFloatValue2());
        cashCardDDTO.setFeeCcy(systemParameterCDTO2.getCharValue1());
        cashCardDDTO.setFeeAmount(systemParameterCDTO2.getFloatValue1());
        cashCardDDTO.setDailyAccumAmount(systemParameterCDTO3.getFloatValue1());
        cashCardDDTO.setDailyTrfAccumAmount(systemParameterCDTO3.getFloatValue2());
        cashCardDDTO.setDailyAccumResetDate(commonDTO.getBusinessDate());
        cashCardDDTO.setDailyAccumResetTime(commonDTO.getSystemInTime());

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug(">>>>>>>>>>CashCardDDTO : " + cashCardDDTO.toString());
        }

        return cashCardDDTO;
    }

    private SystemParameterCDTO getSystemParameter(SystemParameterCDTO
            systemParameterCDTO, CosesCommonDTO commonDTO) throws CosesAppException
    {
        systemParameterCDTO.setBankCode(commonDTO.getBankCode());
        systemParameterCDTO.setBranchCode(commonDTO.getBranchCode());
        systemParameterCDTO.setSystem(CommonSystemConstants.SYSTEM_CASH_CARD_MANAGEMENT);
        systemParameterCDTO.setGroupCode(CashCardConstants.SYS_PARAM_SETUP);

        try
        {
            ICommonManagementSB iCommon = EJBUtilRule.getICommonManagementSB();
            systemParameterCDTO = iCommon.getSystemParameter(systemParameterCDTO);
        }
        catch(CosesAppException cae)
        {
            throw cae;
        }
        catch (RemoteException ex)
        {
            throw new EJBException(ex);
        }
        return systemParameterCDTO;
    }

    public CashCardDDTO makeCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Log.SDBLogger.debug("CashCardDDTO : " + cashCardDDTO.toString());

        String password = cashCardDDTO.getPasswordNo();

        // Password No를 암호화하여 할당한다.
        CipherManager cipherManager = CipherManager.getManager();

        cashCardDDTO.setPasswordNo(cipherManager.encryptAsString(
                cashCardDDTO.getPasswordNo(), CipherManager.CUSTOMER));

        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();
        cashCardDDTO = iCashCardSB.makeCashCard(cashCardDDTO, commonDTO);

        cashCardDDTO.setPasswordNo(password);

        return cashCardDDTO;
    }

    public CashCardDDTO setCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();
        cashCardDDTO = iCashCardSB.setCashCard(cashCardDDTO, commonDTO);

        return cashCardDDTO;
    }

    public CashCardDDTO modifyCashCard(ModifyDTO modifyDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();

        CashCardDDTO afterCashCard
        = (CashCardDDTO)modifyDTO.getAfter();

        CashCardDDTO beforeCashCard = new CashCardDDTO();
        beforeCashCard.setBankCode(afterCashCard.getBankCode());
        beforeCashCard.setCardNumber(afterCashCard.getCardNumber());
        beforeCashCard.setPrimaryAccountNo(afterCashCard.getPrimaryAccountNo());
        beforeCashCard.setSequenceNo(afterCashCard.getSequenceNo());

        beforeCashCard = iCashCardSB.getCashCardInfo(beforeCashCard, commonDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("BeforeCashCard : " + beforeCashCard.toString());
        }

        // Password No를 비교하여 변경된 경우에는 다시 암호화하여 할당한다.
        CipherManager cipherManager = CipherManager.getManager();

        String beforePassword = cipherManager.decryptAsString(beforeCashCard.getPasswordNo(),
                                      CipherManager.CUSTOMER);

        if(!beforePassword.equals(afterCashCard.getPasswordNo()))
        {
            afterCashCard.setPasswordNo(cipherManager.encryptAsString(
                    afterCashCard.getPasswordNo(), CipherManager.CUSTOMER));
            afterCashCard.setInvalidAttemptCnt(0);
        }
        else
        {
            afterCashCard.setPasswordNo(cipherManager.encryptAsString(
                    beforePassword, CipherManager.CUSTOMER));
        }

        modifyDTO.setBefore(beforeCashCard);

        // 변경된 필드가 있는지 확인한다. 없으면 바로 method를 종료한다.
        if(modifyDTO.isModified())
        {
            afterCashCard.setLastUpdateDate(commonDTO.getSystemDate());
            afterCashCard.setLastUpdateTime(commonDTO.getSystemInTime());
            afterCashCard.setLastUpdateUserID(commonDTO.getUserID());
            afterCashCard.setMISSendDate(commonDTO.getBusinessDate());

            if(Log.SDBLogger.isDebugEnabled())
            {
                Log.SDBLogger.debug("AfterCashCard : " + afterCashCard.toString());
            }

            iCashCardSB.setCashCard(afterCashCard, commonDTO);
            // Core 시스템의 audit 시스템을 위한 테이블에 변경된 정보를 등록한다.
            registerLedgerAudit(modifyDTO);
        }
        return afterCashCard;
    }

    public HotCardDDTO getHotCardInfo(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();
        hotCardDDTO = iCashCardSB.getHotCardInfo(hotCardDDTO, commonDTO);

        return hotCardDDTO;
    }

    public HotCardDDTO registerHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();

        CashCardDDTO cashCardDDTO = new CashCardDDTO();
        cashCardDDTO.setBankCode(commonDTO.getBankCode());
        cashCardDDTO.setBranchCode(commonDTO.getBranchCode());
        cashCardDDTO.setCardNumber(hotCardDDTO.getCardNumber());

        cashCardDDTO = iCashCardSB.findCashCardInfoByCardNo(cashCardDDTO, commonDTO);

        cashCardDDTO.setStatus(CashCardConstants.PROBLEMED_STATUS);
        cashCardDDTO.setIncidentCode(hotCardDDTO.getIncidentCode());
        cashCardDDTO.setLastUpdateDate(commonDTO.getSystemDate());
        cashCardDDTO.setLastUpdateTime(commonDTO.getSystemInTime());
        cashCardDDTO.setLastUpdateUserID(commonDTO.getUserID());
        cashCardDDTO.setMISSendDate(commonDTO.getBusinessDate());

        iCashCardSB.setCashCard(cashCardDDTO, commonDTO);

        hotCardDDTO.setRegisterDate(commonDTO.getBusinessDate());
        hotCardDDTO.setRegisterTime(commonDTO.getSystemInTime());
        hotCardDDTO.setRegisterBy(commonDTO.getUserID());

        iCashCardSB.makeHotCard(hotCardDDTO, commonDTO);

        return hotCardDDTO;
    }

    public HotCardDDTO releaseHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();

        CashCardDDTO cashCardDDTO = new CashCardDDTO();
        cashCardDDTO.setBankCode(commonDTO.getBankCode());
        cashCardDDTO.setBranchCode(commonDTO.getBranchCode());
        cashCardDDTO.setCardNumber(hotCardDDTO.getCardNumber());

        cashCardDDTO = iCashCardSB.findCashCardInfoByCardNo(cashCardDDTO, commonDTO);

        cashCardDDTO.setStatus(CashCardConstants.NORMAL_STATUS);
        cashCardDDTO.setIncidentCode(hotCardDDTO.getIncidentCode());
        cashCardDDTO.setLastUpdateDate(commonDTO.getSystemDate());
        cashCardDDTO.setLastUpdateTime(commonDTO.getSystemInTime());
        cashCardDDTO.setLastUpdateUserID(commonDTO.getUserID());
        cashCardDDTO.setMISSendDate(commonDTO.getBusinessDate());

        iCashCardSB.setCashCard(cashCardDDTO, commonDTO);

        //hotCardDDTO.setReleasedDate(commonDTO.getBusinessDate());
        hotCardDDTO.setReleasedTime(commonDTO.getSystemInTime());
        hotCardDDTO.setReleasedBy(commonDTO.getUserID());

        iCashCardSB.releaseHotCard(hotCardDDTO, commonDTO);

        return hotCardDDTO;
    }

    private void registerLedgerAudit(ModifyDTO modifyDTO) throws CosesAppException
    {
        try
        {
            // Core common의 registerLedgerAudit()을 호출해서 modifyDTO를 넘긴다.
            ICommonManagementSB iCommonManagement = EJBUtilRule.getICommonManagementSB();
            iCommonManagement.registerLedgerAudit(modifyDTO);
        }
        catch (RemoteException re)
        {
            throw new EJBException(re);
        }
    }
}