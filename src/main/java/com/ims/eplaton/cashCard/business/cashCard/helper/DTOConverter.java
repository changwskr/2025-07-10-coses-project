package com.ims.eplaton.cashCard.business.cashCard.helper;
//test 1
//test 2

import com.chb.coses.foundation.log.Log;

import com.ims.eplaton.cashCard.business.cashCard.model.*;
import com.ims.eplaton.cashCard.business.cashCard.entity.*;

public class DTOConverter
{
    public static CashCardDDTO getCashCardDDTO(CashCardEB cashCardEB,
            CashCardDDTO cashCardDDTO)
    {
        cashCardDDTO.setBankCode(cashCardEB.getBankCode());
        cashCardDDTO.setBankType(cashCardEB.getBankType());
        cashCardDDTO.setBranchCode(cashCardEB.getBranchCode());
        //cashCardDDTO.setCardHolderName(cashCardEB.getCardHolderName());
        cashCardDDTO.setCardNumber(cashCardEB.getCardNumber());
        cashCardDDTO.setCIFNo(cashCardEB.getCifNo());
        cashCardDDTO.setCIFName(cashCardEB.getCifName());
        cashCardDDTO.setDailyAccumAmount(cashCardEB.getDailyAccumAmount());
        cashCardDDTO.setDailyAccumResetDate(cashCardEB.getDailyAccumResetDate());
        cashCardDDTO.setDailyAccumResetTime(cashCardEB.getDailyAccumResetTime());
        cashCardDDTO.setDailyLimitAmount(cashCardEB.getDailyLimitAmount());
        cashCardDDTO.setDailyLimitCcy(cashCardEB.getDailyLimitCcy());
        cashCardDDTO.setDailyTrfAccumAmount(cashCardEB.getDailyTrfAccumAmount());
        cashCardDDTO.setDailyTrfLimitAmount(cashCardEB.getDailyTrfLimitAmount());
        cashCardDDTO.setDailyTrfLimitCcy(cashCardEB.getDailyTrfLimitCcy());
        cashCardDDTO.setEffectiveDate(cashCardEB.getEffectiveDate());
        cashCardDDTO.setExpiryDate(cashCardEB.getExpiryDate());
        cashCardDDTO.setFeeAmount(cashCardEB.getFeeAmount());
        cashCardDDTO.setFeeCcy(cashCardEB.getFeeCcy());
        cashCardDDTO.setFeeWaive(cashCardEB.getFeeWaive());
        cashCardDDTO.setIncidentCode(cashCardEB.getIncidentCode());
        cashCardDDTO.setInvalidAttemptCnt(cashCardEB.getInvalidAttemptCnt());
        cashCardDDTO.setLastUpdateDate(cashCardEB.getLastUpdateDate());
        cashCardDDTO.setLastUpdateTime(cashCardEB.getLastUpdateTime());
        cashCardDDTO.setLastUpdateUserID(cashCardEB.getLastUpdateUserId());
        cashCardDDTO.setMISSendDate(cashCardEB.getMisSendDate());
        cashCardDDTO.setPasswordNo(cashCardEB.getPasswordNo());
        cashCardDDTO.setPrimaryAccountNo(cashCardEB.getPrimaryAccountNo());
        cashCardDDTO.setRegisterBy(cashCardEB.getRegisterBy());
        cashCardDDTO.setRegisterDate(cashCardEB.getRegisterDate());
        cashCardDDTO.setRegisterTime(cashCardEB.getRegisterTime());
        cashCardDDTO.setRemark(cashCardEB.getRemark());
        cashCardDDTO.setSecondaryAccountNo(cashCardEB.getSecondaryAccountNo());
        cashCardDDTO.setSequenceNo(cashCardEB.getSequenceNo());
        cashCardDDTO.setStatus(cashCardEB.getStatus());
        cashCardDDTO.setTernaryAccountNo(cashCardEB.getTernaryAccountNo());
        cashCardDDTO.setType(cashCardEB.getType());
        cashCardDDTO.setIssueDate(cashCardEB.getIssueDate());

        return cashCardDDTO;
    }

    public static void setCashCardDDTO(CashCardDDTO cashCardDDTO, CashCardEB cashCardEB)
    {
        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("CashCardDDTO : " + cashCardDDTO.toString());
        }

        //cashCardEB.setBranchCode(cashCardDDTO.getBranchCode());
        //cashCardEB.setCardHolderName(cashCardDDTO.getCardHolderName());
        //cashCardEB.setCardNumber(cashCardDDTO.getCardNumber());
        cashCardEB.setCifNo(cashCardDDTO.getCIFNo());
        cashCardEB.setCifName(cashCardDDTO.getCIFName());
        cashCardEB.setDailyAccumAmount(cashCardDDTO.getDailyAccumAmount());
        cashCardEB.setDailyAccumResetDate(cashCardDDTO.getDailyAccumResetDate());
        cashCardEB.setDailyAccumResetTime(cashCardDDTO.getDailyAccumResetTime());
        cashCardEB.setDailyLimitAmount(cashCardDDTO.getDailyLimitAmount());
        cashCardEB.setDailyLimitCcy(cashCardDDTO.getDailyLimitCcy());
        cashCardEB.setDailyTrfAccumAmount(cashCardDDTO.getDailyTrfAccumAmount());
        cashCardEB.setDailyTrfLimitAmount(cashCardDDTO.getDailyTrfLimitAmount());
        cashCardEB.setDailyTrfLimitCcy(cashCardDDTO.getDailyTrfLimitCcy());
        cashCardEB.setEffectiveDate(cashCardDDTO.getEffectiveDate());
        cashCardEB.setExpiryDate(cashCardDDTO.getExpiryDate());
        cashCardEB.setFeeAmount(cashCardDDTO.getFeeAmount());
        cashCardEB.setFeeCcy(cashCardDDTO.getFeeCcy());
        cashCardEB.setFeeWaive(cashCardDDTO.getFeeWaive());
        cashCardEB.setIncidentCode(cashCardDDTO.getIncidentCode());
        cashCardEB.setInvalidAttemptCnt(cashCardDDTO.getInvalidAttemptCnt());
        cashCardEB.setLastUpdateDate(cashCardDDTO.getLastUpdateDate());
        cashCardEB.setLastUpdateTime(cashCardDDTO.getLastUpdateTime());
        cashCardEB.setLastUpdateUserId(cashCardDDTO.getLastUpdateUserID());
        cashCardEB.setMisSendDate(cashCardDDTO.getMISSendDate());
        cashCardEB.setPasswordNo(cashCardDDTO.getPasswordNo());
        cashCardEB.setRegisterBy(cashCardDDTO.getRegisterBy());
        cashCardEB.setRegisterDate(cashCardDDTO.getRegisterDate());
        cashCardEB.setRegisterTime(cashCardDDTO.getRegisterTime());
        cashCardEB.setRemark(cashCardDDTO.getRemark());
        cashCardEB.setSecondaryAccountNo(cashCardDDTO.getSecondaryAccountNo());
        cashCardEB.setStatus(cashCardDDTO.getStatus());
        cashCardEB.setTernaryAccountNo(cashCardDDTO.getTernaryAccountNo());
        cashCardEB.setIssueDate(cashCardDDTO.getIssueDate());
        cashCardEB.setMisSendDate(cashCardDDTO.getMISSendDate());
    }

    public static HotCardDDTO getHotCardCDTO(HotCardEB hotCardEB, HotCardDDTO hotCardDDTO)
    {
        //hotCardDDTO.setCardHolderName(hotCardEB.getCardHolderName());
        hotCardDDTO.setCardNumber(hotCardEB.getCardNumber());
        hotCardDDTO.setCIFNo(hotCardEB.getCifNo());
        hotCardDDTO.setCIFName(hotCardEB.getCifName());
        //hotCardDDTO.setEffectiveDate(hotCardEB.getEffectiveDate());
        //hotCardDDTO.setExpiryDate(hotCardEB.getExpiryDate());
        hotCardDDTO.setIncidentCode(hotCardEB.getIncidentCode());
        hotCardDDTO.setPrimaryAccountNo(hotCardEB.getPrimaryAccountNo());
        hotCardDDTO.setRegisterBy(hotCardEB.getRegisterBy());
        hotCardDDTO.setRegisterDate(hotCardEB.getRegisterDate());
        hotCardDDTO.setRegisterTime(hotCardEB.getRegisterTime());
        hotCardDDTO.setRemark(hotCardEB.getRemark());
        hotCardDDTO.setSequenceNo(hotCardEB.getSequenceNo());
        hotCardDDTO.setStatus(hotCardEB.getStatus());
        hotCardDDTO.setReleasedBy(hotCardEB.getReleasedBy());
        hotCardDDTO.setReleasedDate(hotCardEB.getReleasedDate());
        hotCardDDTO.setReleasedTime(hotCardEB.getReleasedTime());

        return hotCardDDTO;
    }

    public static void setHotCardDDTO(HotCardDDTO hotCardDDTO, HotCardEB hotCardEB)
    {
        //hotCardEB.setCardHolderName(hotCardDDTO.getCardHolderName());
        hotCardEB.setCifNo(hotCardDDTO.getCIFNo());
        hotCardEB.setCifName(hotCardDDTO.getCIFName());
        //hotCardEB.setEffectiveDate(hotCardDDTO.getEffectiveDate());
        //hotCardEB.setExpiryDate(hotCardDDTO.getExpiryDate());
        hotCardEB.setIncidentCode(hotCardDDTO.getIncidentCode());
        hotCardEB.setPrimaryAccountNo(hotCardDDTO.getPrimaryAccountNo());
        hotCardEB.setRegisterBy(hotCardDDTO.getRegisterBy());
        hotCardEB.setRegisterDate(hotCardDDTO.getRegisterDate());
        hotCardEB.setRegisterTime(hotCardDDTO.getRegisterTime());
        hotCardEB.setRemark(hotCardDDTO.getRemark());
        hotCardEB.setStatus(hotCardDDTO.getStatus());
        hotCardEB.setReleasedBy(hotCardDDTO.getReleasedBy());
        hotCardEB.setReleasedDate(hotCardDDTO.getReleasedDate());
        hotCardEB.setReleasedTime(hotCardDDTO.getReleasedTime());
    }
}
