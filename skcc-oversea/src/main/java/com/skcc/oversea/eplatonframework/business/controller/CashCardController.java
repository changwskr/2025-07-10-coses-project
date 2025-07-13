package com.skcc.oversea.eplatonframework.business.controller;

import com.skcc.oversea.eplatonframework.business.dto.ServiceResponse;
import com.skcc.oversea.eplatonframework.business.entity.CashCard;
import com.skcc.oversea.eplatonframework.business.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Cash Card REST API Controller
 */
@RestController
@RequestMapping("/api/cashcard")
public class CashCardController extends BaseController {

    @Autowired
    private CashCardService cashCardService;

    /**
     * Get all cash cards
     */
    @GetMapping
    public ResponseEntity<ServiceResponse<List<CashCard>>> getAllCashCards() {
        try {
            List<CashCard> cashCards = cashCardService.getAllCashCards();
            return successList(cashCards);
        } catch (Exception e) {
            logger.error("Error getting all cash cards", e);
            return error("Failed to retrieve cash cards");
        }
    }

    /**
     * Get cash card by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<CashCard>> getCashCardById(@PathVariable Long id) {
        try {
            CashCard cashCard = cashCardService.getCashCardById(id);
            if (cashCard != null) {
                return success(cashCard);
            } else {
                return error("Cash card not found");
            }
        } catch (Exception e) {
            logger.error("Error getting cash card by ID: {}", id, e);
            return error("Failed to retrieve cash card");
        }
    }

    /**
     * Get cash card by card number
     */
    @GetMapping("/card/{cardNo}")
    public ResponseEntity<ServiceResponse<CashCard>> getCashCardByCardNo(@PathVariable String cardNo) {
        try {
            CashCard cashCard = cashCardService.getCashCardByCardNo(cardNo);
            if (cashCard != null) {
                return success(cashCard);
            } else {
                return error("Cash card not found");
            }
        } catch (Exception e) {
            logger.error("Error getting cash card by card number: {}", cardNo, e);
            return error("Failed to retrieve cash card");
        }
    }

    /**
     * Get cash cards by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ServiceResponse<List<CashCard>>> getCashCardsByCustomerId(@PathVariable String customerId) {
        try {
            List<CashCard> cashCards = cashCardService.getCashCardsByCustomerId(customerId);
            return successList(cashCards);
        } catch (Exception e) {
            logger.error("Error getting cash cards by customer ID: {}", customerId, e);
            return error("Failed to retrieve cash cards");
        }
    }

    /**
     * Create new cash card
     */
    @PostMapping
    public ResponseEntity<ServiceResponse<CashCard>> createCashCard(@RequestBody CashCard cashCard) {
        try {
            CashCard createdCashCard = cashCardService.createCashCard(cashCard);
            return success(createdCashCard, "Cash card created successfully");
        } catch (Exception e) {
            logger.error("Error creating cash card", e);
            return error("Failed to create cash card");
        }
    }

    /**
     * Update cash card
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<CashCard>> updateCashCard(@PathVariable Long id,
            @RequestBody CashCard cashCard) {
        try {
            cashCard.setId(id);
            CashCard updatedCashCard = cashCardService.updateCashCard(cashCard);
            if (updatedCashCard != null) {
                return success(updatedCashCard, "Cash card updated successfully");
            } else {
                return error("Cash card not found");
            }
        } catch (Exception e) {
            logger.error("Error updating cash card: {}", id, e);
            return error("Failed to update cash card");
        }
    }

    /**
     * Delete cash card
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<Void>> deleteCashCard(@PathVariable Long id) {
        try {
            boolean deleted = cashCardService.deleteCashCard(id);
            if (deleted) {
                return success(null, "Cash card deleted successfully");
            } else {
                return error("Cash card not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting cash card: {}", id, e);
            return error("Failed to delete cash card");
        }
    }

    /**
     * Get cash cards by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ServiceResponse<List<CashCard>>> getCashCardsByStatus(@PathVariable String status) {
        try {
            List<CashCard> cashCards = cashCardService.getCashCardsByStatus(status);
            return successList(cashCards);
        } catch (Exception e) {
            logger.error("Error getting cash cards by status: {}", status, e);
            return error("Failed to retrieve cash cards");
        }
    }

    /**
     * Get expired cash cards
     */
    @GetMapping("/expired")
    public ResponseEntity<ServiceResponse<List<CashCard>>> getExpiredCashCards() {
        try {
            List<CashCard> cashCards = cashCardService.getExpiredCashCards();
            return successList(cashCards);
        } catch (Exception e) {
            logger.error("Error getting expired cash cards", e);
            return error("Failed to retrieve expired cash cards");
        }
    }

    /**
     * Update card balance
     */
    @PutMapping("/{id}/balance")
    public ResponseEntity<ServiceResponse<CashCard>> updateCardBalance(@PathVariable Long id,
            @RequestParam BigDecimal newBalance) {
        try {
            CashCard updatedCashCard = cashCardService.updateCardBalance(id, newBalance);
            if (updatedCashCard != null) {
                return success(updatedCashCard, "Card balance updated successfully");
            } else {
                return error("Cash card not found");
            }
        } catch (Exception e) {
            logger.error("Error updating card balance: {}", id, e);
            return error("Failed to update card balance");
        }
    }

    /**
     * Block/unblock cash card
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceResponse<CashCard>> updateCardStatus(@PathVariable Long id,
            @RequestParam String status) {
        try {
            CashCard updatedCashCard = cashCardService.updateCardStatus(id, status);
            if (updatedCashCard != null) {
                return success(updatedCashCard, "Card status updated successfully");
            } else {
                return error("Cash card not found");
            }
        } catch (Exception e) {
            logger.error("Error updating card status: {}", id, e);
            return error("Failed to update card status");
        }
    }
}