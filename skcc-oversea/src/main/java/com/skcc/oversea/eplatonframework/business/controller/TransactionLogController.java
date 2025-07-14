package com.skcc.oversea.eplatonframework.business.controller;

import com.skcc.oversea.eplatonframework.business.dto.ServiceResponse;
import com.skcc.oversea.eplatonframework.business.entity.TransactionLog;
import com.skcc.oversea.eplatonframework.business.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import com.skcc.oversea.eplatonframework.business.dto.EPlatonCommonDTO;

/**
 * Transaction Log REST API Controller
 */
@RestController
@RequestMapping("/api/transaction-log")
public class TransactionLogController extends BaseController {

    @Autowired
    private TransactionLogService transactionLogService;

    /**
     * Get all transaction logs
     */
    @GetMapping
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getAllTransactionLogs() {
        try {
            List<TransactionLog> logs = transactionLogService.getAllTransactionLogs();
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting all transaction logs", e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction log by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<TransactionLog>> getTransactionLogById(@PathVariable Long id) {
        try {
            TransactionLog log = transactionLogService.getTransactionLogById(id);
            if (log != null) {
                return success(log);
            } else {
                return error("Transaction log not found");
            }
        } catch (Exception e) {
            logger.error("Error getting transaction log by ID: {}", id, e);
            return error("Failed to retrieve transaction log");
        }
    }

    /**
     * Get transaction log by transaction ID
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<ServiceResponse<TransactionLog>> getTransactionLogByTransactionId(
            @PathVariable String transactionId) {
        try {
            TransactionLog log = transactionLogService.getTransactionLogByTransactionId(transactionId);
            if (log != null) {
                return success(log);
            } else {
                return error("Transaction log not found");
            }
        } catch (Exception e) {
            logger.error("Error getting transaction log by transaction ID: {}", transactionId, e);
            return error("Failed to retrieve transaction log");
        }
    }

    /**
     * Get transaction logs by user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsByUserId(
            @PathVariable String userId) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsByUserId(userId);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by user ID: {}", userId, e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs by system name
     */
    @GetMapping("/system/{systemName}")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsBySystemName(
            @PathVariable String systemName) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsBySystemName(systemName);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by system name: {}", systemName, e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsByDateRange(startDate, endDate);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by date range", e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs by business date
     */
    @GetMapping("/business-date/{businessDate}")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsByBusinessDate(
            @PathVariable String businessDate) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsByBusinessDate(businessDate);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by business date: {}", businessDate, e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs by error code
     */
    @GetMapping("/error/{errorCode}")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsByErrorCode(
            @PathVariable String errorCode) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsByErrorCode(errorCode);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by error code: {}", errorCode, e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs by channel type
     */
    @GetMapping("/channel/{channelType}")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsByChannelType(
            @PathVariable String channelType) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsByChannelType(channelType);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by channel type: {}", channelType, e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs by bank and branch
     */
    @GetMapping("/bank/{bankCode}/branch/{branchCode}")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getTransactionLogsByBankAndBranch(
            @PathVariable String bankCode, @PathVariable String branchCode) {
        try {
            List<TransactionLog> logs = transactionLogService.getTransactionLogsByBankAndBranch(bankCode, branchCode);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting transaction logs by bank and branch: {} - {}", bankCode, branchCode, e);
            return error("Failed to retrieve transaction logs");
        }
    }

    /**
     * Get transaction logs with response time greater than threshold
     */
    @GetMapping("/slow-transactions")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getSlowTransactions(@RequestParam Long threshold) {
        try {
            List<TransactionLog> logs = transactionLogService.getSlowTransactions(threshold);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting slow transactions with threshold: {}", threshold, e);
            return error("Failed to retrieve slow transactions");
        }
    }

    /**
     * Get transaction count by system name and date range
     */
    @GetMapping("/count/system/{systemName}")
    public ResponseEntity<ServiceResponse<Long>> getTransactionCountBySystemAndDateRange(
            @PathVariable String systemName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            Long count = transactionLogService.getTransactionCountBySystemAndDateRange(systemName, startDate, endDate);
            return success(count);
        } catch (Exception e) {
            logger.error("Error getting transaction count by system and date range", e);
            return error("Failed to retrieve transaction count");
        }
    }

    /**
     * Get latest transactions by user ID
     */
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<ServiceResponse<List<TransactionLog>>> getLatestTransactionsByUserId(
            @PathVariable String userId) {
        try {
            List<TransactionLog> logs = transactionLogService.getLatestTransactionsByUserId(userId);
            return successList(logs);
        } catch (Exception e) {
            logger.error("Error getting latest transactions by user ID: {}", userId, e);
            return error("Failed to retrieve latest transactions");
        }
    }

    /**
     * Create new transaction log
     */
    @PostMapping
    public ResponseEntity<ServiceResponse<TransactionLog>> createTransactionLog(
            @RequestBody TransactionLog transactionLog) {
        try {
            TransactionLog createdLog = transactionLogService.createTransactionLog(transactionLog);
            return success(createdLog, "Transaction log created successfully");
        } catch (Exception e) {
            logger.error("Error creating transaction log", e);
            return error("Failed to create transaction log");
        }
    }

    /**
     * Update transaction log
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<TransactionLog>> updateTransactionLog(@PathVariable Long id,
            @RequestBody TransactionLog transactionLog) {
        try {
            transactionLog.setId(id);
            TransactionLog updatedLog = transactionLogService.updateTransactionLog(transactionLog);
            if (updatedLog != null) {
                return success(updatedLog, "Transaction log updated successfully");
            } else {
                return error("Transaction log not found");
            }
        } catch (Exception e) {
            logger.error("Error updating transaction log: {}", id, e);
            return error("Failed to update transaction log");
        }
    }

    /**
     * Delete transaction log
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<Void>> deleteTransactionLog(@PathVariable Long id) {
        try {
            boolean deleted = transactionLogService.deleteTransactionLog(id);
            if (deleted) {
                EPlatonCommonDTO response = new EPlatonCommonDTO();
                response.setErrorCode("I0000");
                response.setErrorMessage("Transaction log deleted successfully");
                event.setResponse(response);
                return success(null, "Transaction log deleted successfully");
            } else {
                return error("Transaction log not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting transaction log: {}", id, e);
            return error("Failed to delete transaction log");
        }
    }
}