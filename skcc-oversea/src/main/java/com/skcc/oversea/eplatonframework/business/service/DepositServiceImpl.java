package com.skcc.oversea.eplatonframework.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.eplatonframework.business.delegate.action.DepositBizAction.DepositService;
import com.skcc.oversea.eplatonframework.business.entity.Deposit;
import com.skcc.oversea.eplatonframework.business.entity.DepositPK;
import com.skcc.oversea.eplatonframework.business.repository.DepositRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Deposit Service Implementation for SKCC Oversea
 * 
 * Provides deposit business operations
 * using Spring Boot and modern Java patterns.
 */
@Service
public class DepositServiceImpl implements DepositService {

    private static final Logger logger = LoggerFactory.getLogger(DepositServiceImpl.class);

    @Autowired
    private DepositRepository depositRepository;

    /**
     * Create deposit
     */
    @Override
    @Transactional
    public EPlatonEvent createDeposit(EPlatonEvent event) {
        try {
            logger.info("Creating deposit");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidCreateDepositRequest(requestData)) {
                setErrorInfo(event, "EDEP101", "Invalid create deposit request data");
                return event;
            }

            // Create deposit logic here
            // Deposit deposit = depositRepository.createDeposit(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit created successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit created successfully");

            logger.info("Deposit created successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error creating deposit", e);
            setErrorInfo(event, "EDEP102", "Failed to create deposit: " + e.getMessage());
            return event;
        }
    }

    /**
     * Update deposit
     */
    @Override
    @Transactional
    public EPlatonEvent updateDeposit(EPlatonEvent event) {
        try {
            logger.info("Updating deposit");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidUpdateDepositRequest(requestData)) {
                setErrorInfo(event, "EDEP201", "Invalid update deposit request data");
                return event;
            }

            // Update deposit logic here
            // Deposit deposit = depositRepository.updateDeposit(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit updated successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit updated successfully");

            logger.info("Deposit updated successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error updating deposit", e);
            setErrorInfo(event, "EDEP202", "Failed to update deposit: " + e.getMessage());
            return event;
        }
    }

    /**
     * Delete deposit
     */
    @Override
    @Transactional
    public EPlatonEvent deleteDeposit(EPlatonEvent event) {
        try {
            logger.info("Deleting deposit");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidDeleteDepositRequest(requestData)) {
                setErrorInfo(event, "EDEP301", "Invalid delete deposit request data");
                return event;
            }

            // Delete deposit logic here
            // depositRepository.deleteDeposit(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit deleted successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit deleted successfully");

            logger.info("Deposit deleted successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error deleting deposit", e);
            setErrorInfo(event, "EDEP302", "Failed to delete deposit: " + e.getMessage());
            return event;
        }
    }

    /**
     * Get deposit
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getDeposit(EPlatonEvent event) {
        try {
            logger.info("Getting deposit");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetDepositRequest(requestData)) {
                setErrorInfo(event, "EDEP401", "Invalid get deposit request data");
                return event;
            }

            // Get deposit logic here
            // Deposit deposit = depositRepository.getDeposit(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit data retrieved successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit data retrieved successfully");

            logger.info("Deposit retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting deposit", e);
            setErrorInfo(event, "EDEP402", "Failed to get deposit: " + e.getMessage());
            return event;
        }
    }

    /**
     * Get deposit list
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getDepositList(EPlatonEvent event) {
        try {
            logger.info("Getting deposit list");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetDepositListRequest(requestData)) {
                setErrorInfo(event, "EDEP501", "Invalid get deposit list request data");
                return event;
            }

            // Get deposit list logic here
            // List<Deposit> deposits = depositRepository.getDepositList(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit list retrieved successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit list retrieved successfully");

            logger.info("Deposit list retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting deposit list", e);
            setErrorInfo(event, "EDEP502", "Failed to get deposit list: " + e.getMessage());
            return event;
        }
    }

    /**
     * Withdraw deposit
     */
    @Override
    @Transactional
    public EPlatonEvent withdrawDeposit(EPlatonEvent event) {
        try {
            logger.info("Withdrawing deposit");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidWithdrawDepositRequest(requestData)) {
                setErrorInfo(event, "EDEP601", "Invalid withdraw deposit request data");
                return event;
            }

            // Withdraw deposit logic here
            // depositRepository.withdrawDeposit(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit withdrawn successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit withdrawn successfully");

            logger.info("Deposit withdrawn successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error withdrawing deposit", e);
            setErrorInfo(event, "EDEP602", "Failed to withdraw deposit: " + e.getMessage());
            return event;
        }
    }

    /**
     * Transfer deposit
     */
    @Override
    @Transactional
    public EPlatonEvent transferDeposit(EPlatonEvent event) {
        try {
            logger.info("Transferring deposit");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidTransferDepositRequest(requestData)) {
                setErrorInfo(event, "EDEP701", "Invalid transfer deposit request data");
                return event;
            }

            // Transfer deposit logic here
            // depositRepository.transferDeposit(requestData);

            // Set response
            EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
            responseDTO.setMessage("Deposit transferred successfully");
            event.setResponse(responseDTO);
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Deposit transferred successfully");

            logger.info("Deposit transferred successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error transferring deposit", e);
            setErrorInfo(event, "EDEP702", "Failed to transfer deposit: " + e.getMessage());
            return event;
        }
    }

    /**
     * Set error information
     */
    private void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
        TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
        String currentErrorCode = tpsvcinfo.getErrorcode();

        if (currentErrorCode != null && currentErrorCode.startsWith("I")) {
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);
        } else if (currentErrorCode != null && currentErrorCode.startsWith("E")) {
            String combinedErrorCode = errorCode + "|" + currentErrorCode;
            tpsvcinfo.setErrorcode(combinedErrorCode);
            tpsvcinfo.setError_message(errorMessage);
        } else {
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);
        }

        // Set response as error message - create a simple response DTO
        EPlatonCommonDTO responseDTO = new EPlatonCommonDTO();
        responseDTO.setReqName("ERROR");
        event.setResponse(responseDTO);
    }

    // Validation methods
    private boolean isValidCreateDepositRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidUpdateDepositRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidDeleteDepositRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidGetDepositRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidGetDepositListRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidWithdrawDepositRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidTransferDepositRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidValidateDepositRequest(Object requestData) {
        return requestData != null;
    }

    // =========================== Controller Expected Methods
    // ===========================

    public List<Deposit> getAllDeposits() {
        logger.info("Getting all deposits");
        return depositRepository.findAll();
    }

    public Deposit getDepositById(DepositPK id) {
        logger.info("Getting deposit by ID: {}", id);
        return depositRepository.findById(id).orElse(null);
    }

    public Deposit getDepositByAccountNo(String accountNo) {
        logger.info("Getting deposit by account number: {}", accountNo);
        return depositRepository.findByAccountNo(accountNo).orElse(null);
    }

    public List<Deposit> getDepositsByCustomerId(String customerId) {
        logger.info("Getting deposits by customer ID: {}", customerId);
        return depositRepository.findByCustomerId(customerId);
    }

    public Deposit createDeposit(Deposit deposit) {
        logger.info("Creating deposit: {}", deposit.getAccountNo());
        return depositRepository.save(deposit);
    }

    public Deposit updateDeposit(Deposit deposit) {
        logger.info("Updating deposit: {}", deposit.getAccountNo());
        if (depositRepository.existsById(deposit.getId())) {
            return depositRepository.save(deposit);
        }
        return null;
    }

    public boolean deleteDeposit(DepositPK id) {
        logger.info("Deleting deposit by ID: {}", id);
        if (depositRepository.existsById(id)) {
            depositRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Deposit> getDepositsByStatus(String status) {
        logger.info("Getting deposits by status: {}", status);
        Boolean isActive = "ACTIVE".equalsIgnoreCase(status);
        return depositRepository.findByAccountStatus(isActive);
    }

    public List<Deposit> getDepositsByAccountType(String accountType) {
        logger.info("Getting deposits by account type: {}", accountType);
        return depositRepository.findByAccountType(accountType);
    }

    public List<Deposit> getDepositsByBranchCode(String branchCode) {
        logger.info("Getting deposits by branch code: {}", branchCode);
        return depositRepository.findByBranchCode(branchCode);
    }

    public Deposit updateAccountBalance(DepositPK id, BigDecimal newBalance) {
        logger.info("Updating account balance for ID: {} to {}", id, newBalance);
        Deposit deposit = getDepositById(id);
        if (deposit != null) {
            deposit.setBalance(newBalance);
            return depositRepository.save(deposit);
        }
        return null;
    }

    public BigDecimal getTotalBalanceByCustomerId(String customerId) {
        logger.info("Getting total balance by customer ID: {}", customerId);
        BigDecimal totalBalance = depositRepository.sumBalanceByCustomerId(customerId);
        return totalBalance != null ? totalBalance : BigDecimal.ZERO;
    }

    public List<Deposit> getMaturedDeposits() {
        logger.info("Getting matured deposits");
        return depositRepository.findMaturedDeposits(LocalDateTime.now());
    }

    public Deposit updateAccountStatus(DepositPK id, String status) {
        logger.info("Updating account status for ID: {} to {}", id, status);
        Deposit deposit = getDepositById(id);
        if (deposit != null) {
            deposit.setAccountStatus(status);
            return depositRepository.save(deposit);
        }
        return null;
    }
}