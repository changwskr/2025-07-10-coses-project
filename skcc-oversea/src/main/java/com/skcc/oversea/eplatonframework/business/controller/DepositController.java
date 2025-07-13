package com.skcc.oversea.eplatonframework.business.controller;

import com.skcc.oversea.eplatonframework.business.dto.ServiceResponse;
import com.skcc.oversea.eplatonframework.business.entity.Deposit;
import com.skcc.oversea.eplatonframework.business.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Deposit REST API Controller
 */
@RestController
@RequestMapping("/api/deposit")
public class DepositController extends BaseController {

    @Autowired
    private DepositService depositService;

    /**
     * Get all deposits
     */
    @GetMapping
    public ResponseEntity<ServiceResponse<List<Deposit>>> getAllDeposits() {
        try {
            List<Deposit> deposits = depositService.getAllDeposits();
            return successList(deposits);
        } catch (Exception e) {
            logger.error("Error getting all deposits", e);
            return error("Failed to retrieve deposits");
        }
    }

    /**
     * Get deposit by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<Deposit>> getDepositById(@PathVariable Long id) {
        try {
            Deposit deposit = depositService.getDepositById(id);
            if (deposit != null) {
                return success(deposit);
            } else {
                return error("Deposit not found");
            }
        } catch (Exception e) {
            logger.error("Error getting deposit by ID: {}", id, e);
            return error("Failed to retrieve deposit");
        }
    }

    /**
     * Get deposit by account number
     */
    @GetMapping("/account/{accountNo}")
    public ResponseEntity<ServiceResponse<Deposit>> getDepositByAccountNo(@PathVariable String accountNo) {
        try {
            Deposit deposit = depositService.getDepositByAccountNo(accountNo);
            if (deposit != null) {
                return success(deposit);
            } else {
                return error("Deposit not found");
            }
        } catch (Exception e) {
            logger.error("Error getting deposit by account number: {}", accountNo, e);
            return error("Failed to retrieve deposit");
        }
    }

    /**
     * Get deposits by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ServiceResponse<List<Deposit>>> getDepositsByCustomerId(@PathVariable String customerId) {
        try {
            List<Deposit> deposits = depositService.getDepositsByCustomerId(customerId);
            return successList(deposits);
        } catch (Exception e) {
            logger.error("Error getting deposits by customer ID: {}", customerId, e);
            return error("Failed to retrieve deposits");
        }
    }

    /**
     * Create new deposit
     */
    @PostMapping
    public ResponseEntity<ServiceResponse<Deposit>> createDeposit(@RequestBody Deposit deposit) {
        try {
            Deposit createdDeposit = depositService.createDeposit(deposit);
            return success(createdDeposit, "Deposit created successfully");
        } catch (Exception e) {
            logger.error("Error creating deposit", e);
            return error("Failed to create deposit");
        }
    }

    /**
     * Update deposit
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<Deposit>> updateDeposit(@PathVariable Long id, @RequestBody Deposit deposit) {
        try {
            deposit.setId(id);
            Deposit updatedDeposit = depositService.updateDeposit(deposit);
            if (updatedDeposit != null) {
                return success(updatedDeposit, "Deposit updated successfully");
            } else {
                return error("Deposit not found");
            }
        } catch (Exception e) {
            logger.error("Error updating deposit: {}", id, e);
            return error("Failed to update deposit");
        }
    }

    /**
     * Delete deposit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<Void>> deleteDeposit(@PathVariable Long id) {
        try {
            boolean deleted = depositService.deleteDeposit(id);
            if (deleted) {
                return success(null, "Deposit deleted successfully");
            } else {
                return error("Deposit not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting deposit: {}", id, e);
            return error("Failed to delete deposit");
        }
    }

    /**
     * Get deposits by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ServiceResponse<List<Deposit>>> getDepositsByStatus(@PathVariable String status) {
        try {
            List<Deposit> deposits = depositService.getDepositsByStatus(status);
            return successList(deposits);
        } catch (Exception e) {
            logger.error("Error getting deposits by status: {}", status, e);
            return error("Failed to retrieve deposits");
        }
    }

    /**
     * Get deposits by account type
     */
    @GetMapping("/type/{accountType}")
    public ResponseEntity<ServiceResponse<List<Deposit>>> getDepositsByAccountType(@PathVariable String accountType) {
        try {
            List<Deposit> deposits = depositService.getDepositsByAccountType(accountType);
            return successList(deposits);
        } catch (Exception e) {
            logger.error("Error getting deposits by account type: {}", accountType, e);
            return error("Failed to retrieve deposits");
        }
    }

    /**
     * Get deposits by branch code
     */
    @GetMapping("/branch/{branchCode}")
    public ResponseEntity<ServiceResponse<List<Deposit>>> getDepositsByBranchCode(@PathVariable String branchCode) {
        try {
            List<Deposit> deposits = depositService.getDepositsByBranchCode(branchCode);
            return successList(deposits);
        } catch (Exception e) {
            logger.error("Error getting deposits by branch code: {}", branchCode, e);
            return error("Failed to retrieve deposits");
        }
    }

    /**
     * Update account balance
     */
    @PutMapping("/{id}/balance")
    public ResponseEntity<ServiceResponse<Deposit>> updateAccountBalance(@PathVariable Long id,
            @RequestParam BigDecimal newBalance) {
        try {
            Deposit updatedDeposit = depositService.updateAccountBalance(id, newBalance);
            if (updatedDeposit != null) {
                return success(updatedDeposit, "Account balance updated successfully");
            } else {
                return error("Deposit not found");
            }
        } catch (Exception e) {
            logger.error("Error updating account balance: {}", id, e);
            return error("Failed to update account balance");
        }
    }

    /**
     * Get total balance by customer ID
     */
    @GetMapping("/customer/{customerId}/total-balance")
    public ResponseEntity<ServiceResponse<BigDecimal>> getTotalBalanceByCustomerId(@PathVariable String customerId) {
        try {
            BigDecimal totalBalance = depositService.getTotalBalanceByCustomerId(customerId);
            return success(totalBalance);
        } catch (Exception e) {
            logger.error("Error getting total balance by customer ID: {}", customerId, e);
            return error("Failed to retrieve total balance");
        }
    }

    /**
     * Get matured deposits
     */
    @GetMapping("/matured")
    public ResponseEntity<ServiceResponse<List<Deposit>>> getMaturedDeposits() {
        try {
            List<Deposit> deposits = depositService.getMaturedDeposits();
            return successList(deposits);
        } catch (Exception e) {
            logger.error("Error getting matured deposits", e);
            return error("Failed to retrieve matured deposits");
        }
    }

    /**
     * Update account status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceResponse<Deposit>> updateAccountStatus(@PathVariable Long id,
            @RequestParam String status) {
        try {
            Deposit updatedDeposit = depositService.updateAccountStatus(id, status);
            if (updatedDeposit != null) {
                return success(updatedDeposit, "Account status updated successfully");
            } else {
                return error("Deposit not found");
            }
        } catch (Exception e) {
            logger.error("Error updating account status: {}", id, e);
            return error("Failed to update account status");
        }
    }
}