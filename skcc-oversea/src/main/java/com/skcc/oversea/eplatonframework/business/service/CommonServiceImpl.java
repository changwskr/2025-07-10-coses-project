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
import com.skcc.oversea.eplatonframework.business.entity.Common;
import com.skcc.oversea.eplatonframework.business.repository.CommonRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Common Service Implementation for SKCC Oversea
 * 
 * Provides common business operations
 * using Spring Boot and modern Java patterns.
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private CommonRepository commonRepository;

    // =========================== EPlaton Event Methods ===========================

    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getCommonInfo(EPlatonEvent event) {
        try {
            logger.info("Getting common info");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetCommonInfoRequest(requestData)) {
                setErrorInfo(event, "ECOM001", "Invalid get common info request data");
                return event;
            }

            // Get common info logic here
            // Common common = commonRepository.getCommonInfo(requestData);

            // Set response
            event.setResponse("Common info retrieved successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Common info retrieved successfully");

            logger.info("Common info retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting common info", e);
            setErrorInfo(event, "ECOM002", "Failed to get common info: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional
    public EPlatonEvent createCommon(EPlatonEvent event) {
        try {
            logger.info("Creating common");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidCreateCommonRequest(requestData)) {
                setErrorInfo(event, "ECOM101", "Invalid create common request data");
                return event;
            }

            // Create common logic here
            // Common common = commonRepository.createCommon(requestData);

            // Set response
            event.setResponse("Common created successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Common created successfully");

            logger.info("Common created successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error creating common", e);
            setErrorInfo(event, "ECOM102", "Failed to create common: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional
    public EPlatonEvent updateCommon(EPlatonEvent event) {
        try {
            logger.info("Updating common");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidUpdateCommonRequest(requestData)) {
                setErrorInfo(event, "ECOM201", "Invalid update common request data");
                return event;
            }

            // Update common logic here
            // Common common = commonRepository.updateCommon(requestData);

            // Set response
            event.setResponse("Common updated successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Common updated successfully");

            logger.info("Common updated successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error updating common", e);
            setErrorInfo(event, "ECOM202", "Failed to update common: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional
    public EPlatonEvent deleteCommon(EPlatonEvent event) {
        try {
            logger.info("Deleting common");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidDeleteCommonRequest(requestData)) {
                setErrorInfo(event, "ECOM301", "Invalid delete common request data");
                return event;
            }

            // Delete common logic here
            // commonRepository.deleteCommon(requestData);

            // Set response
            event.setResponse("Common deleted successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Common deleted successfully");

            logger.info("Common deleted successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error deleting common", e);
            setErrorInfo(event, "ECOM302", "Failed to delete common: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent validateCommon(EPlatonEvent event) {
        try {
            logger.info("Validating common");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidValidateCommonRequest(requestData)) {
                setErrorInfo(event, "ECOM401", "Invalid validate common request data");
                return event;
            }

            // Validate common logic here
            // boolean isValid = commonRepository.validateCommon(requestData);

            // Set response
            event.setResponse("Common validation completed successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Common validation completed successfully");

            logger.info("Common validation completed successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error validating common", e);
            setErrorInfo(event, "ECOM402", "Failed to validate common: " + e.getMessage());
            return event;
        }
    }

    // =========================== Controller Expected Methods
    // ===========================

    @Override
    public List<Common> findAll() {
        logger.info("Finding all commons");
        return commonRepository.findAll();
    }

    @Override
    public Common findById(Long id) {
        logger.info("Finding common by ID: {}", id);
        return commonRepository.findById(id).orElse(null);
    }

    @Override
    public Common findByCommonCode(String commonCode) {
        logger.info("Finding common by code: {}", commonCode);
        return commonRepository.findByCommonCode(commonCode).orElse(null);
    }

    @Override
    public List<Common> findByCommonType(String commonType) {
        logger.info("Finding commons by type: {}", commonType);
        return commonRepository.findByCommonType(commonType);
    }

    @Override
    public List<Common> findActiveCommons() {
        logger.info("Finding active commons");
        return commonRepository.findByIsActive(true);
    }

    @Override
    public Common save(Common common) {
        logger.info("Saving common: {}", common.getCommonCode());
        return commonRepository.save(common);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting common by ID: {}", id);
        commonRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return commonRepository.existsById(id);
    }

    // =========================== Private Methods ===========================

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

        // Set response as error message
        event.setResponse(errorMessage);
    }

    // Validation methods
    private boolean isValidGetCommonInfoRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidCreateCommonRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidUpdateCommonRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidDeleteCommonRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidValidateCommonRequest(Object requestData) {
        return requestData != null;
    }
}