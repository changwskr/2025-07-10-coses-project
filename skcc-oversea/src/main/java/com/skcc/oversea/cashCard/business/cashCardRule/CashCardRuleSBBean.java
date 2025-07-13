
package com.skcc.oversea.cashCard.business.cashCardRule;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skcc.oversea.foundation.log.Log;
import com.chb.coses.foundation.security.CipherManager;
import com.chb.coses.cosesFramework.transfer.ModifyDTO;
import com.skcc.oversea.framework.transfer.CosesCommonDTO;
import com.skcc.oversea.framework.exception.CosesAppException;

import com.skcc.oversea.cashCard.business.constants.CashCardConstants;
import com.skcc.oversea.cashCard.business.cashCard.model.*;
import com.skcc.oversea.cashCard.business.cashCard.ICashCardSB;
import com.skcc.oversea.cashCard.business.cashCardRule.helper.*;

import com.chb.coses.reference.transfer.*;
import com.skcc.oversea.common.transfer.*;
import com.skcc.oversea.common.business.facade.*;
import com.skcc.oversea.common.business.constants.*;
import com.skcc.oversea.reference.business.facade.*;

/**
 * Cash Card Rule Service for SKCC Oversea
 * Spring Boot service replacing EJB session bean
 */
@Service
@Transactional
public class CashCardRuleSBBean implements ICashCardRuleSB {

    private static final Logger logger = LoggerFactory.getLogger(CashCardRuleSBBean.class);

    @Autowired
    private ICashCardSB cashCardService;

    @Autowired
    private ICommonManagementSB commonManagementService;

    /**
     * Get cash card information
     */
    @Transactional(readOnly = true)
    public CashCardDDTO getCashCardInfo(CashCardDDTO cashCardDDTO, CosesCommonDTO commonDTO)
            throws CosesAppException {
        try {
            cashCardDDTO = cashCardService.getCashCardInfo(cashCardDDTO, commonDTO);

            // Decrypt password number
            CipherManager cipherManager = CipherManager.getManager();
            cashCardDDTO.setPasswordNo(cipherManager.decryptAsString(cashCardDDTO.getPasswordNo(),
                    CipherManager.CUSTOMER));

            return cashCardDDTO;
        } catch (Exception e) {
            logger.error("Error getting cash card info", e);
            throw new CosesAppException("Failed to get cash card info", e);
        }
    }

    /**
     * Get cash card by card number
     */
    @Transactional(readOnly = true)
    public CashCardDDTO getCashCardByCardNo(CashCardDDTO cashCardDDTO, CosesCommonDTO commonDTO)
            throws CosesAppException {
        try {
            cashCardDDTO = cashCardService.findCashCardInfoByCardNo(cashCardDDTO, commonDTO);
            return cashCardDDTO;
        } catch (Exception e) {
            logger.error("Error getting cash card by card number", e);
            throw new CosesAppException("Failed to get cash card by card number", e);
        }
    }

    /**
     * Get cash card for registration
     */
    @Transactional(readOnly = true)
    public CashCardDDTO getCashCardForRegister(CashCardDDTO cashCardDDTO, CosesCommonDTO commonDTO)
            throws CosesAppException {
        try {
            // Get setup information from system parameters
            SystemParameterCDTO systemParameterCDTO1 = new SystemParameterCDTO();
            systemParameterCDTO1.setKind(CashCardConstants.DAILY_LIMIT_INFO); // "CARDLIMIT"
            systemParameterCDTO1 = getSystemParameter(systemParameterCDTO1, commonDTO);

            SystemParameterCDTO systemParameterCDTO2 = new SystemParameterCDTO();
            systemParameterCDTO2.setKind(CashCardConstants.DAILY_FEE_LIMIT_INFO); // "CARDFEELIM"
            systemParameterCDTO2 = getSystemParameter(systemParameterCDTO2, commonDTO);

            SystemParameterCDTO systemParameterCDTO3 = new SystemParameterCDTO();
            systemParameterCDTO3.setKind(CashCardConstants.DAILY_ACCUM_INFO); // "CARDACCUM"
            systemParameterCDTO3 = getSystemParameter(systemParameterCDTO3, commonDTO);

            // Set daily limit amount, transfer limit amount, available currency, etc.
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

            logger.debug("CashCardDDTO : {}", cashCardDDTO.toString());

            return cashCardDDTO;
        } catch (Exception e) {
            logger.error("Error getting cash card for register", e);
            throw new CosesAppException("Failed to get cash card for register", e);
        }
    }

    /**
     * Get system parameter
     */
    private SystemParameterCDTO getSystemParameter(SystemParameterCDTO systemParameterCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException {
        try {
            systemParameterCDTO.setBankCode(commonDTO.getBankCode());
            systemParameterCDTO.setBranchCode(commonDTO.getBranchCode());
            systemParameterCDTO.setSystem(CommonSystemConstants.SYSTEM_CASH_CARD_MANAGEMENT);
            systemParameterCDTO.setGroupCode(CashCardConstants.SYS_PARAM_SETUP);

            systemParameterCDTO = commonManagementService.getSystemParameter(systemParameterCDTO);
            return systemParameterCDTO;
        } catch (Exception e) {
            logger.error("Error getting system parameter", e);
            throw new CosesAppException("Failed to get system parameter", e);
        }
    }

    /**
     * Make cash card
     */
    @Transactional(readOnly = false)
    public CashCardDDTO makeCashCard(CashCardDDTO cashCardDDTO, CosesCommonDTO commonDTO)
            throws CosesAppException {
        try {
            logger.debug("CashCardDDTO : {}", cashCardDDTO.toString());

            String password = cashCardDDTO.getPasswordNo();

            // Encrypt password number
            CipherManager cipherManager = CipherManager.getManager();
            cashCardDDTO.setPasswordNo(cipherManager.encryptAsString(cashCardDDTO.getPasswordNo(),
                    CipherManager.CUSTOMER));

            cashCardDDTO = cashCardService.makeCashCard(cashCardDDTO, commonDTO);
            cashCardDDTO.setPasswordNo(password);

            return cashCardDDTO;
        } catch (Exception e) {
            logger.error("Error making cash card", e);
            throw new CosesAppException("Failed to make cash card", e);
        }
    }

    /**
     * Set cash card
     */
    @Transactional(readOnly = false)
    public CashCardDDTO setCashCard(CashCardDDTO cashCardDDTO, CosesCommonDTO commonDTO)
            throws CosesAppException {
        try {
            cashCardDDTO = cashCardService.setCashCard(cashCardDDTO, commonDTO);
            return cashCardDDTO;
        } catch (Exception e) {
            logger.error("Error setting cash card", e);
            throw new CosesAppException("Failed to set cash card", e);
        }
    }

    /**
     * Modify cash card
     */
    @Transactional(readOnly = false)
    public CashCardDDTO modifyCashCard(ModifyDTO modifyDTO, CosesCommonDTO commonDTO)
            throws CosesAppException {
        try {
            CashCardDDTO afterCashCard = (CashCardDDTO) modifyDTO.getAfter();

            CashCardDDTO beforeCashCard = new CashCardDDTO();
            beforeCashCard.setBankCode(afterCashCard.getBankCode());
            beforeCashCard.setCardNumber(afterCashCard.getCardNumber());
            beforeCashCard.setPrimaryAccountNo(afterCashCard.getPrimaryAccountNo());
            beforeCashCard.setSequenceNo(afterCashCard.getSequenceNo());

            beforeCashCard = cashCardService.getCashCardInfo(beforeCashCard, commonDTO);

            logger.debug("BeforeCashCard : {}", beforeCashCard.toString());

            // Compare password numbers and re-encrypt if changed
            CipherManager cipherManager = CipherManager.getManager();
            String beforePassword = cipherManager.decryptAsString(beforeCashCard.getPasswordNo(),
                    CipherManager.CUSTOMER);

            if (!beforePassword.equals(afterCashCard.getPasswordNo())) {
                afterCashCard.setPasswordNo(cipherManager.encryptAsString(afterCashCard.getPasswordNo(),
                        CipherManager.CUSTOMER));
                afterCashCard.setInvalidAttemptCnt(0);
            } else {
                afterCashCard.setPasswordNo(cipherManager.encryptAsString(beforePassword,
                        CipherManager.CUSTOMER));
            }

            modifyDTO.setBefore(beforeCashCard);

            // Check if any fields have been modified
            if (modifyDTO.isModified()) {
                afterCashCard.setLastUpdateDate(commonDTO.getSystemDate());
                afterCashCard.setLastUpdateTime(commonDTO.getSystemInTime());
                afterCashCard.setLastUpdateUserID(commonDTO.getUserID());
                afterCashCard.setMISSendDate(commonDTO.getBusinessDate());

                logger.debug("AfterCashCard : {}", afterCashCard.toString());

                cashCardService.modifyCashCard(modifyDTO, commonDTO);
            }

            return afterCashCard;
        } catch (Exception e) {
            logger.error("Error modifying cash card", e);
            throw new CosesAppException("Failed to modify cash card", e);
        }
    }

    public HotCardDDTO getHotCardInfo(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException {
        ICashCardSB iCashCardSB = EJBUtilRule.getICashCardSB();
        hotCardDDTO = iCashCardSB.getHotCardInfo(hotCardDDTO, commonDTO);

        return hotCardDDTO;
    }

    public HotCardDDTO registerHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException {
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
            CosesCommonDTO commonDTO) throws CosesAppException {
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

        // hotCardDDTO.setReleasedDate(commonDTO.getBusinessDate());
        hotCardDDTO.setReleasedTime(commonDTO.getSystemInTime());
        hotCardDDTO.setReleasedBy(commonDTO.getUserID());

        iCashCardSB.releaseHotCard(hotCardDDTO, commonDTO);

        return hotCardDDTO;
    }

    private void registerLedgerAudit(ModifyDTO modifyDTO) throws CosesAppException {
        try {
            // Core common의 registerLedgerAudit()을 호출해서 modifyDTO를 넘긴다.
            ICommonManagementSB iCommonManagement = EJBUtilRule.getICommonManagementSB();
            iCommonManagement.registerLedgerAudit(modifyDTO);
        } catch (RemoteException re) {
            throw new EJBException(re);
        }
    }
}
