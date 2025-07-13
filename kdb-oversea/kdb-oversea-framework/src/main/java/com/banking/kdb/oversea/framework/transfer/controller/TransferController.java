package com.banking.kdb.oversea.framework.transfer.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transfer.model.Transfer;
import com.banking.kdb.oversea.framework.transfer.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Transfer Controller for KDB Oversea Framework
 * 
 * Provides REST endpoints for transfer operations.
 */
@RestController
@RequestMapping("/api/v1/transfers")
@Tag(name = "Transfer Management", description = "Transfer processing and management APIs")
public class TransferController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TransferController.class);

    @Autowired
    private TransferService transferService;

    /**
     * Create a new transfer
     */
    @PostMapping
    @Operation(summary = "Create a new transfer", description = "Creates a new transfer with the provided details")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> createTransfer(
            @Parameter(description = "Transfer details", required = true) @Valid @RequestBody Transfer transfer) {

        logger.info("Creating transfer - Type: {}, Amount: {}",
                transfer.getTransferType(), transfer.getAmount());

        try {
            Transfer createdTransfer = transferService.createTransfer(transfer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
        } catch (Exception e) {
            logger.error("Failed to create transfer - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfer by ID
     */
    @GetMapping("/{transferId}")
    @Operation(summary = "Get transfer by ID", description = "Retrieves a transfer by its ID")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<Transfer> getTransferById(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId) {

        logger.debug("Getting transfer by ID: {}", transferId);

        try {
            return transferService.findTransferById(transferId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to get transfer - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by source customer ID
     */
    @GetMapping("/source-customer/{sourceCustomerId}")
    @Operation(summary = "Get transfers by source customer ID", description = "Retrieves all transfers from a source customer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersBySourceCustomerId(
            @Parameter(description = "Source customer ID", required = true) @PathVariable String sourceCustomerId) {

        logger.debug("Getting transfers by source customer ID: {}", sourceCustomerId);

        try {
            List<Transfer> transfers = transferService.findTransfersBySourceCustomerId(sourceCustomerId);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by source customer ID - Customer: {}, Error: {}",
                    sourceCustomerId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by destination customer ID
     */
    @GetMapping("/destination-customer/{destinationCustomerId}")
    @Operation(summary = "Get transfers by destination customer ID", description = "Retrieves all transfers to a destination customer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersByDestinationCustomerId(
            @Parameter(description = "Destination customer ID", required = true) @PathVariable String destinationCustomerId) {

        logger.debug("Getting transfers by destination customer ID: {}", destinationCustomerId);

        try {
            List<Transfer> transfers = transferService.findTransfersByDestinationCustomerId(destinationCustomerId);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by destination customer ID - Customer: {}, Error: {}",
                    destinationCustomerId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by source account
     */
    @GetMapping("/source-account/{sourceAccount}")
    @Operation(summary = "Get transfers by source account", description = "Retrieves all transfers from a source account")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersBySourceAccount(
            @Parameter(description = "Source account number", required = true) @PathVariable String sourceAccount) {

        logger.debug("Getting transfers by source account: {}", sourceAccount);

        try {
            List<Transfer> transfers = transferService.findTransfersBySourceAccount(sourceAccount);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by source account - Account: {}, Error: {}",
                    sourceAccount, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by destination account
     */
    @GetMapping("/destination-account/{destinationAccount}")
    @Operation(summary = "Get transfers by destination account", description = "Retrieves all transfers to a destination account")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersByDestinationAccount(
            @Parameter(description = "Destination account number", required = true) @PathVariable String destinationAccount) {

        logger.debug("Getting transfers by destination account: {}", destinationAccount);

        try {
            List<Transfer> transfers = transferService.findTransfersByDestinationAccount(destinationAccount);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by destination account - Account: {}, Error: {}",
                    destinationAccount, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get transfers by status", description = "Retrieves all transfers with a specific status")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersByStatus(
            @Parameter(description = "Transfer status", required = true) @PathVariable String status) {

        logger.debug("Getting transfers by status: {}", status);

        try {
            List<Transfer> transfers = transferService.findTransfersByStatus(status);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by status - Status: {}, Error: {}",
                    status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by type
     */
    @GetMapping("/type/{transferType}")
    @Operation(summary = "Get transfers by type", description = "Retrieves all transfers of a specific type")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersByType(
            @Parameter(description = "Transfer type", required = true) @PathVariable String transferType) {

        logger.debug("Getting transfers by type: {}", transferType);

        try {
            List<Transfer> transfers = transferService.findTransfersByType(transferType);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by type - Type: {}, Error: {}",
                    transferType, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by amount range
     */
    @GetMapping("/amount-range")
    @Operation(summary = "Get transfers by amount range", description = "Retrieves transfers within an amount range")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersByAmountRange(
            @Parameter(description = "Minimum amount", required = true) @RequestParam BigDecimal minAmount,
            @Parameter(description = "Maximum amount", required = true) @RequestParam BigDecimal maxAmount) {

        logger.debug("Getting transfers by amount range: {} - {}", minAmount, maxAmount);

        try {
            List<Transfer> transfers = transferService.findTransfersByAmountRange(minAmount, maxAmount);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by amount range - Min: {}, Max: {}, Error: {}",
                    minAmount, maxAmount, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers by date range
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get transfers by date range", description = "Retrieves transfers within a date range")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getTransfersByDateRange(
            @Parameter(description = "Start date", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        logger.debug("Getting transfers by date range: {} - {}", startDate, endDate);

        try {
            List<Transfer> transfers = transferService.findTransfersByDateRange(
                    startDate.toString(), endDate.toString());
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers by date range - Start: {}, End: {}, Error: {}",
                    startDate, endDate, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update transfer status
     */
    @PutMapping("/{transferId}/status")
    @Operation(summary = "Update transfer status", description = "Updates the status of a transfer")
    @PreAuthorize("hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUTHORIZER')")
    public ResponseEntity<Transfer> updateTransferStatus(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "New status", required = true) @RequestParam String status,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Updating transfer status - ID: {}, Status: {}, By: {}", transferId, status, modifiedBy);

        try {
            Transfer updatedTransfer = transferService.updateTransferStatus(transferId, status, modifiedBy);
            return ResponseEntity.ok(updatedTransfer);
        } catch (Exception e) {
            logger.error("Failed to update transfer status - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update authorization status
     */
    @PutMapping("/{transferId}/authorization")
    @Operation(summary = "Update authorization status", description = "Updates the authorization status of a transfer")
    @PreAuthorize("hasRole('TRANSFER_AUTHORIZER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> updateAuthorizationStatus(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Authorization status", required = true) @RequestParam String authorizationStatus,
            @Parameter(description = "Authorized by", required = true) @RequestParam String authorizedBy) {

        logger.info("Updating authorization status - ID: {}, Status: {}", transferId, authorizationStatus);

        try {
            Transfer updatedTransfer = transferService.updateAuthorizationStatus(transferId, authorizationStatus,
                    authorizedBy);
            return ResponseEntity.ok(updatedTransfer);
        } catch (Exception e) {
            logger.error("Failed to update authorization status - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update validation status
     */
    @PutMapping("/{transferId}/validation")
    @Operation(summary = "Update validation status", description = "Updates the validation status of a transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> updateValidationStatus(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Validation status", required = true) @RequestParam String validationStatus,
            @Parameter(description = "Validation message") @RequestParam(required = false) String validationMessage) {

        logger.info("Updating validation status - ID: {}, Status: {}", transferId, validationStatus);

        try {
            Transfer updatedTransfer = transferService.updateValidationStatus(transferId, validationStatus,
                    validationMessage);
            return ResponseEntity.ok(updatedTransfer);
        } catch (Exception e) {
            logger.error("Failed to update validation status - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update routing status
     */
    @PutMapping("/{transferId}/routing")
    @Operation(summary = "Update routing status", description = "Updates the routing status of a transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> updateRoutingStatus(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Routing status", required = true) @RequestParam String routingStatus,
            @Parameter(description = "Routing type") @RequestParam(required = false) String routingType) {

        logger.info("Updating routing status - ID: {}, Status: {}", transferId, routingStatus);

        try {
            Transfer updatedTransfer = transferService.updateRoutingStatus(transferId, routingStatus, routingType);
            return ResponseEntity.ok(updatedTransfer);
        } catch (Exception e) {
            logger.error("Failed to update routing status - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update queue status
     */
    @PutMapping("/{transferId}/queue")
    @Operation(summary = "Update queue status", description = "Updates the queue status of a transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> updateQueueStatus(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Queue status", required = true) @RequestParam String queueStatus,
            @Parameter(description = "Queue type") @RequestParam(required = false) String queueType) {

        logger.info("Updating queue status - ID: {}, Status: {}", transferId, queueStatus);

        try {
            Transfer updatedTransfer = transferService.updateQueueStatus(transferId, queueStatus, queueType);
            return ResponseEntity.ok(updatedTransfer);
        } catch (Exception e) {
            logger.error("Failed to update queue status - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retry failed transfer
     */
    @PostMapping("/{transferId}/retry")
    @Operation(summary = "Retry failed transfer", description = "Retries a failed transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> retryTransfer(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Retrying transfer - ID: {}", transferId);

        try {
            Transfer retriedTransfer = transferService.retryTransfer(transferId, modifiedBy);
            return ResponseEntity.ok(retriedTransfer);
        } catch (Exception e) {
            logger.error("Failed to retry transfer - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cancel transfer
     */
    @PostMapping("/{transferId}/cancel")
    @Operation(summary = "Cancel transfer", description = "Cancels a transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> cancelTransfer(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Reason for cancellation", required = true) @RequestParam String reason,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Cancelling transfer - ID: {}, Reason: {}", transferId, reason);

        try {
            Transfer cancelledTransfer = transferService.cancelTransfer(transferId, reason, modifiedBy);
            return ResponseEntity.ok(cancelledTransfer);
        } catch (Exception e) {
            logger.error("Failed to cancel transfer - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Complete transfer
     */
    @PostMapping("/{transferId}/complete")
    @Operation(summary = "Complete transfer", description = "Completes a transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Transfer> completeTransfer(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Completing transfer - ID: {}", transferId);

        try {
            Transfer completedTransfer = transferService.completeTransfer(transferId, modifiedBy);
            return ResponseEntity.ok(completedTransfer);
        } catch (Exception e) {
            logger.error("Failed to complete transfer - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Validate transfer
     */
    @PostMapping("/{transferId}/validate")
    @Operation(summary = "Validate transfer", description = "Validates a transfer")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Map<String, Object>> validateTransfer(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId) {

        logger.info("Validating transfer - ID: {}", transferId);

        try {
            return transferService.findTransferById(transferId)
                    .map(transfer -> {
                        boolean isValid = transferService.validateTransfer(transfer);
                        return ResponseEntity.ok(Map.of(
                                "transferId", transferId,
                                "valid", isValid,
                                "timestamp", LocalDateTime.now()));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to validate transfer - ID: {}, Error: {}", transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get pending transfers
     */
    @GetMapping("/pending")
    @Operation(summary = "Get pending transfers", description = "Retrieves all pending transfers")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getPendingTransfers() {
        logger.debug("Getting pending transfers");

        try {
            List<Transfer> transfers = transferService.findPendingTransfers();
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get pending transfers - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfers requiring authorization
     */
    @GetMapping("/requiring-authorization")
    @Operation(summary = "Get transfers requiring authorization", description = "Retrieves transfers that require authorization")
    @PreAuthorize("hasRole('TRANSFER_AUTHORIZER') or hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<List<Transfer>> getTransfersRequiringAuthorization() {
        logger.debug("Getting transfers requiring authorization");

        try {
            List<Transfer> transfers = transferService.findTransfersRequiringAuthorization();
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get transfers requiring authorization - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get high priority transfers
     */
    @GetMapping("/high-priority")
    @Operation(summary = "Get high priority transfers", description = "Retrieves high priority transfers")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getHighPriorityTransfers() {
        logger.debug("Getting high priority transfers");

        try {
            List<Transfer> transfers = transferService.findHighPriorityTransfers();
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get high priority transfers - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get failed transfers
     */
    @GetMapping("/failed")
    @Operation(summary = "Get failed transfers", description = "Retrieves failed transfers")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getFailedTransfers() {
        logger.debug("Getting failed transfers");

        try {
            List<Transfer> transfers = transferService.findFailedTransfers();
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get failed transfers - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get retry transfers
     */
    @GetMapping("/retry")
    @Operation(summary = "Get retry transfers", description = "Retrieves transfers scheduled for retry")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<List<Transfer>> getRetryTransfers() {
        logger.debug("Getting retry transfers");

        try {
            List<Transfer> transfers = transferService.findRetryTransfers();
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Failed to get retry transfers - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfer statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get transfer statistics", description = "Retrieves transfer statistics")
    @PreAuthorize("hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<Object> getTransferStatistics(
            @Parameter(description = "Source customer ID") @RequestParam(required = false) String sourceCustomerId,
            @Parameter(description = "Period") @RequestParam(required = false) String period) {

        logger.debug("Getting transfer statistics - Customer: {}, Period: {}", sourceCustomerId, period);

        try {
            Object statistics = transferService.getTransferStatistics(sourceCustomerId, period);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            logger.error("Failed to get transfer statistics - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfer summary
     */
    @GetMapping("/summary")
    @Operation(summary = "Get transfer summary", description = "Retrieves transfer summary")
    @PreAuthorize("hasRole('TRANSFER_USER') or hasRole('TRANSFER_ADMIN') or hasRole('TRANSFER_AUDITOR')")
    public ResponseEntity<Object> getTransferSummary(
            @Parameter(description = "Source customer ID") @RequestParam(required = false) String sourceCustomerId,
            @Parameter(description = "Source account number") @RequestParam(required = false) String sourceAccount,
            @Parameter(description = "Period") @RequestParam(required = false) String period) {

        logger.debug("Getting transfer summary - Customer: {}, Account: {}, Period: {}",
                sourceCustomerId, sourceAccount, period);

        try {
            Object summary = transferService.getTransferSummary(sourceCustomerId, sourceAccount, period);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Failed to get transfer summary - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transfer monitoring data
     */
    @GetMapping("/{transferId}/monitoring")
    @Operation(summary = "Get transfer monitoring data", description = "Retrieves monitoring data for a transfer")
    @PreAuthorize("hasRole('TRANSFER_ADMIN')")
    public ResponseEntity<Object> getTransferMonitoringData(
            @Parameter(description = "Transfer ID", required = true) @PathVariable String transferId) {

        logger.debug("Getting transfer monitoring data - ID: {}", transferId);

        try {
            Object monitoringData = transferService.getTransferMonitoringData(transferId);
            return ResponseEntity.ok(monitoringData);
        } catch (Exception e) {
            logger.error("Failed to get transfer monitoring data - ID: {}, Error: {}",
                    transferId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Performs a health check on the transfer service")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.debug("Performing health check");

        try {
            Map<String, Object> health = Map.of(
                    "status", "UP",
                    "service", "Transfer Service",
                    "timestamp", LocalDateTime.now(),
                    "version", FrameworkConstants.FRAMEWORK_VERSION);
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            logger.error("Health check failed - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                    "status", "DOWN",
                    "service", "Transfer Service",
                    "timestamp", LocalDateTime.now(),
                    "error", e.getMessage()));
        }
    }
}