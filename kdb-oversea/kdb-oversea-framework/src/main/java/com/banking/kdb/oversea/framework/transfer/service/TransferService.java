package com.banking.kdb.oversea.framework.transfer.service;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.logej.LOGEJ;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transfer.dao.TransferDAO;
import com.banking.kdb.oversea.framework.transfer.model.Transfer;
import com.banking.kdb.oversea.framework.transaction.tpmutil.TPMUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transfer Service for KDB Oversea Framework
 * 
 * Provides high-level transfer processing services.
 */
@Service
@Transactional
public class TransferService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TransferService.class);

    @Autowired
    private TransferDAO transferDAO;

    /**
     * Create a new transfer
     */
    public Transfer createTransfer(Transfer transfer) {
        logger.info("Creating transfer - Type: {}, Amount: {}, Currency: {}",
                transfer.getTransferType(), transfer.getAmount(), transfer.getCurrency());

        // Generate transfer ID if not provided
        if (CommonUtil.isNullOrEmpty(transfer.getTransferId())) {
            transfer.setTransferId(TPMUtil.generateTransactionId());
        }

        // Generate reference number if not provided
        if (CommonUtil.isNullOrEmpty(transfer.getReferenceNumber())) {
            transfer.setReferenceNumber(TPMUtil.generateReferenceNumber());
        }

        // Start TPM monitoring
        TPMUtil.startTransactionMonitoring(transfer.getTransferId());

        try {
            // Set default values
            transfer.setStatus(FrameworkConstants.TRANSFER_STATUS_INITIATED);
            transfer.setCreatedDateTime(LocalDateTime.now());

            // Validate transfer
            if (!validateTransfer(transfer)) {
                throw new IllegalArgumentException("Transfer validation failed");
            }

            // Save transfer
            Transfer savedTransfer = transferDAO.save(transfer);

            // Update TPM monitoring
            TPMUtil.updateTransactionMonitoring(transfer.getTransferId(), "CREATED",
                    FrameworkConstants.TRANSFER_STATUS_INITIATED);

            // Log transfer creation
            LOGEJ.logTransactionOperation("TRANSFER_CREATE", savedTransfer.getTransferId(),
                    "Transfer created successfully");

            logger.info("Transfer created successfully - ID: {}", savedTransfer.getTransferId());
            return savedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transfer.getTransferId(),
                    FrameworkConstants.ERROR_CODE_SYSTEM_ERROR, e.getMessage());

            // Complete monitoring with error
            TPMUtil.completeTransactionMonitoring(transfer.getTransferId(),
                    FrameworkConstants.TRANSFER_STATUS_FAILED);

            logger.error("Failed to create transfer - Error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create transfer: " + e.getMessage(), e);
        }
    }

    /**
     * Find transfer by ID
     */
    public Optional<Transfer> findTransferById(String transferId) {
        logger.debug("Finding transfer by ID: {}", transferId);
        return transferDAO.findByTransferId(transferId);
    }

    /**
     * Find transfers by source customer ID
     */
    public List<Transfer> findTransfersBySourceCustomerId(String sourceCustomerId) {
        logger.debug("Finding transfers by source customer ID: {}", sourceCustomerId);
        return transferDAO.findBySourceCustomerId(sourceCustomerId);
    }

    /**
     * Find transfers by destination customer ID
     */
    public List<Transfer> findTransfersByDestinationCustomerId(String destinationCustomerId) {
        logger.debug("Finding transfers by destination customer ID: {}", destinationCustomerId);
        return transferDAO.findByDestinationCustomerId(destinationCustomerId);
    }

    /**
     * Find transfers by source account
     */
    public List<Transfer> findTransfersBySourceAccount(String sourceAccount) {
        logger.debug("Finding transfers by source account: {}", sourceAccount);
        return transferDAO.findBySourceAccount(sourceAccount);
    }

    /**
     * Find transfers by destination account
     */
    public List<Transfer> findTransfersByDestinationAccount(String destinationAccount) {
        logger.debug("Finding transfers by destination account: {}", destinationAccount);
        return transferDAO.findByDestinationAccount(destinationAccount);
    }

    /**
     * Find transfers by status
     */
    public List<Transfer> findTransfersByStatus(String status) {
        logger.debug("Finding transfers by status: {}", status);
        return transferDAO.findByStatus(status);
    }

    /**
     * Find transfers by type
     */
    public List<Transfer> findTransfersByType(String transferType) {
        logger.debug("Finding transfers by type: {}", transferType);
        return transferDAO.findByTransferType(transferType);
    }

    /**
     * Find transfers by amount range
     */
    public List<Transfer> findTransfersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        logger.debug("Finding transfers by amount range: {} - {}", minAmount, maxAmount);
        return transferDAO.findByAmountRange(minAmount, maxAmount);
    }

    /**
     * Find transfers by date range
     */
    public List<Transfer> findTransfersByDateRange(String startDate, String endDate) {
        logger.debug("Finding transfers by date range: {} - {}", startDate, endDate);
        LocalDateTime start = CommonUtil.parseDateTime(startDate);
        LocalDateTime end = CommonUtil.parseDateTime(endDate);

        if (start == null || end == null) {
            throw new IllegalArgumentException("Invalid date format");
        }

        return transferDAO.findByCreatedDateRange(start, end);
    }

    /**
     * Update transfer status
     */
    public Transfer updateTransferStatus(String transferId, String status, String modifiedBy) {
        logger.info("Updating transfer status - ID: {}, Status: {}", transferId, status);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "STATUS_UPDATE", status);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setStatus(status);
            transfer.setModifiedBy(modifiedBy);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log status update
            LOGEJ.logTransactionOperation("TRANSFER_STATUS_UPDATE", transferId,
                    "Status updated to: " + status + " by " + modifiedBy);

            logger.info("Transfer status updated successfully - ID: {}, Status: {}",
                    transferId, status);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_UPDATE_ERROR, e.getMessage());

            logger.error("Failed to update transfer status - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update transfer status: " + e.getMessage(), e);
        }
    }

    /**
     * Update authorization status
     */
    public Transfer updateAuthorizationStatus(String transferId, String authorizationStatus, String authorizedBy) {
        logger.info("Updating authorization status - ID: {}, Status: {}", transferId, authorizationStatus);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "AUTHORIZATION", authorizationStatus);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setAuthorizationStatus(authorizationStatus);
            transfer.setAuthorizedBy(authorizedBy);
            transfer.setAuthorizationDate(LocalDateTime.now());
            transfer.setModifiedBy(authorizedBy);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log authorization update
            LOGEJ.logTransactionOperation("TRANSFER_AUTHORIZATION_UPDATE", transferId,
                    "Authorization status updated to: " + authorizationStatus);

            logger.info("Transfer authorization status updated successfully - ID: {}, Status: {}",
                    transferId, authorizationStatus);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_AUTHORIZATION_ERROR, e.getMessage());

            logger.error("Failed to update authorization status - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update authorization status: " + e.getMessage(), e);
        }
    }

    /**
     * Update validation status
     */
    public Transfer updateValidationStatus(String transferId, String validationStatus, String validationMessage) {
        logger.info("Updating validation status - ID: {}, Status: {}", transferId, validationStatus);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "VALIDATION", validationStatus);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setValidationStatus(validationStatus);
            transfer.setValidationMessage(validationMessage);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log validation update
            LOGEJ.logTransactionOperation("TRANSFER_VALIDATION_UPDATE", transferId,
                    "Validation status updated to: " + validationStatus);

            logger.info("Transfer validation status updated successfully - ID: {}, Status: {}",
                    transferId, validationStatus);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_VALIDATION_ERROR, e.getMessage());

            logger.error("Failed to update validation status - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update validation status: " + e.getMessage(), e);
        }
    }

    /**
     * Update routing status
     */
    public Transfer updateRoutingStatus(String transferId, String routingStatus, String routingType) {
        logger.info("Updating routing status - ID: {}, Status: {}", transferId, routingStatus);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "ROUTING", routingStatus);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setRoutingStatus(routingStatus);
            transfer.setRoutingType(routingType);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log routing update
            LOGEJ.logTransactionOperation("TRANSFER_ROUTING_UPDATE", transferId,
                    "Routing status updated to: " + routingStatus);

            logger.info("Transfer routing status updated successfully - ID: {}, Status: {}",
                    transferId, routingStatus);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_ROUTING_ERROR, e.getMessage());

            logger.error("Failed to update routing status - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update routing status: " + e.getMessage(), e);
        }
    }

    /**
     * Update queue status
     */
    public Transfer updateQueueStatus(String transferId, String queueStatus, String queueType) {
        logger.info("Updating queue status - ID: {}, Status: {}", transferId, queueStatus);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "QUEUE", queueStatus);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setQueueStatus(queueStatus);
            transfer.setQueueType(queueType);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log queue update
            LOGEJ.logTransactionOperation("TRANSFER_QUEUE_UPDATE", transferId,
                    "Queue status updated to: " + queueStatus);

            logger.info("Transfer queue status updated successfully - ID: {}, Status: {}",
                    transferId, queueStatus);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_QUEUE_ERROR, e.getMessage());

            logger.error("Failed to update queue status - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update queue status: " + e.getMessage(), e);
        }
    }

    /**
     * Update error information
     */
    public Transfer updateErrorInfo(String transferId, String errorCode, String errorMessage) {
        logger.info("Updating error info - ID: {}, Error: {}", transferId, errorCode);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "ERROR", FrameworkConstants.TRANSFER_STATUS_FAILED);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setErrorCode(errorCode);
            transfer.setErrorMessage(errorMessage);
            transfer.setStatus(FrameworkConstants.TRANSFER_STATUS_FAILED);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log error update
            LOGEJ.logTransactionOperation("TRANSFER_ERROR_UPDATE", transferId,
                    "Error updated: " + errorCode + " - " + errorMessage);

            logger.info("Transfer error info updated successfully - ID: {}, Error: {}",
                    transferId, errorCode);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_SYSTEM_ERROR, e.getMessage());

            logger.error("Failed to update error info - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update error info: " + e.getMessage(), e);
        }
    }

    /**
     * Update processing time
     */
    public Transfer updateProcessingTime(String transferId, Long processingTimeMs) {
        logger.debug("Updating processing time - ID: {}, Time: {}ms", transferId, processingTimeMs);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setProcessingTimeMs(processingTimeMs);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log processing time update
            LOGEJ.logPerformanceMetric("transfer_processing_time", processingTimeMs.toString(), "ms");

            logger.debug("Transfer processing time updated successfully - ID: {}, Time: {}ms",
                    transferId, processingTimeMs);
            return updatedTransfer;

        } catch (Exception e) {
            logger.error("Failed to update processing time - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to update processing time: " + e.getMessage(), e);
        }
    }

    /**
     * Retry failed transfer
     */
    public Transfer retryTransfer(String transferId, String modifiedBy) {
        logger.info("Retrying transfer - ID: {}", transferId);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "RETRY", FrameworkConstants.TRANSFER_STATUS_PENDING);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();

            if (transfer.getRetryCount() >= transfer.getMaxRetryAttempts()) {
                throw new IllegalStateException("Maximum retry attempts exceeded for transfer: " + transferId);
            }

            transfer.setRetryCount(transfer.getRetryCount() + 1);
            transfer.setStatus(FrameworkConstants.TRANSFER_STATUS_PENDING);
            transfer.setErrorCode(null);
            transfer.setErrorMessage(null);
            transfer.setModifiedBy(modifiedBy);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Log retry
            LOGEJ.logTransactionOperation("TRANSFER_RETRY", transferId,
                    "Transfer retry attempt: " + transfer.getRetryCount());

            logger.info("Transfer retry successful - ID: {}, Attempt: {}",
                    transferId, transfer.getRetryCount());
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_RETRY_ERROR, e.getMessage());

            logger.error("Failed to retry transfer - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to retry transfer: " + e.getMessage(), e);
        }
    }

    /**
     * Cancel transfer
     */
    public Transfer cancelTransfer(String transferId, String reason, String modifiedBy) {
        logger.info("Cancelling transfer - ID: {}, Reason: {}", transferId, reason);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "CANCELLED", FrameworkConstants.TRANSFER_STATUS_CANCELLED);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setStatus(FrameworkConstants.TRANSFER_STATUS_CANCELLED);
            transfer.setErrorMessage(reason);
            transfer.setModifiedBy(modifiedBy);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Complete monitoring
            TPMUtil.completeTransactionMonitoring(transferId, FrameworkConstants.TRANSFER_STATUS_CANCELLED);

            // Log cancellation
            LOGEJ.logTransactionOperation("TRANSFER_CANCEL", transferId,
                    "Transfer cancelled: " + reason);

            logger.info("Transfer cancelled successfully - ID: {}, Reason: {}",
                    transferId, reason);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_CANCEL_ERROR, e.getMessage());

            logger.error("Failed to cancel transfer - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to cancel transfer: " + e.getMessage(), e);
        }
    }

    /**
     * Complete transfer
     */
    public Transfer completeTransfer(String transferId, String modifiedBy) {
        logger.info("Completing transfer - ID: {}", transferId);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transferId, "COMPLETED", FrameworkConstants.TRANSFER_STATUS_COMPLETED);

        try {
            Optional<Transfer> optionalTransfer = transferDAO.findByTransferId(transferId);
            if (optionalTransfer.isEmpty()) {
                throw new IllegalArgumentException("Transfer not found: " + transferId);
            }

            Transfer transfer = optionalTransfer.get();
            transfer.setStatus(FrameworkConstants.TRANSFER_STATUS_COMPLETED);
            transfer.setModifiedBy(modifiedBy);
            transfer.setModifiedDateTime(LocalDateTime.now());

            Transfer updatedTransfer = transferDAO.save(transfer);

            // Complete monitoring
            TPMUtil.completeTransactionMonitoring(transferId, FrameworkConstants.TRANSFER_STATUS_COMPLETED);

            // Log completion
            LOGEJ.logTransactionOperation("TRANSFER_COMPLETE", transferId,
                    "Transfer completed successfully");

            logger.info("Transfer completed successfully - ID: {}", transferId);
            return updatedTransfer;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transferId,
                    FrameworkConstants.ERROR_CODE_COMPLETION_ERROR, e.getMessage());

            logger.error("Failed to complete transfer - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            throw new RuntimeException("Failed to complete transfer: " + e.getMessage(), e);
        }
    }

    /**
     * Validate transfer
     */
    public boolean validateTransfer(Transfer transfer) {
        logger.debug("Validating transfer - ID: {}", transfer.getTransferId());

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transfer.getTransferId(), "VALIDATION",
                FrameworkConstants.VALIDATION_STATUS_PENDING);

        try {
            // Basic validation
            if (transfer.getAmount() == null || transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                logger.warn("Transfer validation failed - Invalid amount: {}", transfer.getAmount());
                return false;
            }

            if (CommonUtil.isNullOrEmpty(transfer.getCurrency())) {
                logger.warn("Transfer validation failed - Missing currency");
                return false;
            }

            if (CommonUtil.isNullOrEmpty(transfer.getTransferType())) {
                logger.warn("Transfer validation failed - Missing transfer type");
                return false;
            }

            if (CommonUtil.isNullOrEmpty(transfer.getSourceAccount())) {
                logger.warn("Transfer validation failed - Missing source account");
                return false;
            }

            if (CommonUtil.isNullOrEmpty(transfer.getDestinationAccount())) {
                logger.warn("Transfer validation failed - Missing destination account");
                return false;
            }

            // Update TPM monitoring
            TPMUtil.updateTransactionMonitoring(transfer.getTransferId(), "VALIDATION",
                    FrameworkConstants.VALIDATION_STATUS_PASS);

            logger.debug("Transfer validation completed - ID: {}, Valid: true", transfer.getTransferId());
            return true;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transfer.getTransferId(),
                    FrameworkConstants.ERROR_CODE_VALIDATION_ERROR, e.getMessage());

            logger.error("Transfer validation failed - ID: {}, Error: {}",
                    transfer.getTransferId(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * Find pending transfers
     */
    public List<Transfer> findPendingTransfers() {
        logger.debug("Finding pending transfers");
        return transferDAO.findPendingRetryTransfers(LocalDateTime.now());
    }

    /**
     * Find transfers requiring authorization
     */
    public List<Transfer> findTransfersRequiringAuthorization() {
        logger.debug("Finding transfers requiring authorization");
        return transferDAO.findTransfersRequiringAuthorization();
    }

    /**
     * Find high priority transfers
     */
    public List<Transfer> findHighPriorityTransfers() {
        logger.debug("Finding high priority transfers");
        return transferDAO.findHighPriorityTransfers();
    }

    /**
     * Find slow transfers
     */
    public List<Transfer> findSlowTransfers(Long thresholdMs) {
        logger.debug("Finding slow transfers - Threshold: {}ms", thresholdMs);
        return transferDAO.findSlowTransfers(thresholdMs);
    }

    /**
     * Find failed transfers
     */
    public List<Transfer> findFailedTransfers() {
        logger.debug("Finding failed transfers");
        return transferDAO.findByStatus(FrameworkConstants.TRANSFER_STATUS_FAILED);
    }

    /**
     * Find retry transfers
     */
    public List<Transfer> findRetryTransfers() {
        logger.debug("Finding retry transfers");
        return transferDAO.findPendingRetryTransfers(LocalDateTime.now());
    }

    /**
     * Get transfer statistics
     */
    public Object getTransferStatistics(String sourceCustomerId, String period) {
        logger.debug("Getting transfer statistics - Customer: {}, Period: {}", sourceCustomerId, period);

        // This would typically involve aggregating transfer data
        // For now, return basic statistics
        return Map.of(
                "sourceCustomerId", sourceCustomerId,
                "period", period,
                "totalTransfers", transferDAO.countBySourceCustomerId(sourceCustomerId),
                "totalAmount", transferDAO.sumAmountBySourceCustomerId(sourceCustomerId),
                "timestamp", LocalDateTime.now());
    }

    /**
     * Get transfer summary
     */
    public Object getTransferSummary(String sourceCustomerId, String sourceAccount, String period) {
        logger.debug("Getting transfer summary - Customer: {}, Account: {}, Period: {}",
                sourceCustomerId, sourceAccount, period);

        // This would typically involve aggregating transfer data
        // For now, return basic summary
        return Map.of(
                "sourceCustomerId", sourceCustomerId,
                "sourceAccount", sourceAccount,
                "period", period,
                "totalTransfers", transferDAO.countBySourceCustomerId(sourceCustomerId),
                "totalAmount", transferDAO.sumAmountBySourceCustomerId(sourceCustomerId),
                "timestamp", LocalDateTime.now());
    }

    /**
     * Get transfer monitoring data
     */
    public Object getTransferMonitoringData(String transferId) {
        logger.debug("Getting transfer monitoring data - ID: {}", transferId);

        return Map.of(
                "transferId", transferId,
                "monitor", TPMUtil.getTransactionMonitor(transferId),
                "metrics", TPMUtil.getTransactionMetrics(transferId));
    }

    /**
     * Clear all monitoring data
     */
    public void clearAllMonitoringData() {
        logger.info("Clearing all monitoring data");
        TPMUtil.clearAllMonitoringData();
    }

    /**
     * Reset system statistics
     */
    public void resetSystemStatistics() {
        logger.info("Resetting system statistics");
        TPMUtil.resetSystemStatistics();
    }

    /**
     * Shutdown transfer service
     */
    public void shutdown() {
        logger.info("Shutting down transfer service");

        // Clear monitoring data
        TPMUtil.clearAllMonitoringData();

        logger.info("Transfer service shutdown completed");
    }
}