package com.skcc.oversea.eplatonframework.business.controller;

import com.skcc.oversea.eplatonframework.business.dto.ServiceResponse;
import com.skcc.oversea.eplatonframework.business.entity.HotCard;
import com.skcc.oversea.eplatonframework.business.service.HotCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.skcc.oversea.eplatonframework.business.entity.HotCardPK;

/**
 * Hot Card REST API Controller
 */
@RestController
@RequestMapping("/api/hotcard")
public class HotCardController extends BaseController {

    @Autowired
    private HotCardService hotCardService;

    /**
     * Get all hot cards
     */
    @GetMapping
    public ResponseEntity<ServiceResponse<List<HotCard>>> getAllHotCards() {
        try {
            List<HotCard> hotCards = hotCardService.getAllHotCards();
            return successList(hotCards);
        } catch (Exception e) {
            logger.error("Error getting all hot cards", e);
            return error("Failed to retrieve hot cards");
        }
    }

    /**
     * Get hot card by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<HotCard>> getHotCardById(@PathVariable Long id) {
        try {
            HotCard hotCard = hotCardService.getHotCardById(id);
            if (hotCard != null) {
                return success(hotCard);
            } else {
                return error("Hot card not found");
            }
        } catch (Exception e) {
            logger.error("Error getting hot card by ID: {}", id, e);
            return error("Failed to retrieve hot card");
        }
    }

    /**
     * Get hot card by card number
     */
    @GetMapping("/card/{cardNo}")
    public ResponseEntity<ServiceResponse<HotCard>> getHotCardByCardNo(@PathVariable String cardNo) {
        try {
            HotCard hotCard = hotCardService.getHotCardByCardNo(cardNo);
            if (hotCard != null) {
                return success(hotCard);
            } else {
                return error("Hot card not found");
            }
        } catch (Exception e) {
            logger.error("Error getting hot card by card number: {}", cardNo, e);
            return error("Failed to retrieve hot card");
        }
    }

    /**
     * Get hot cards by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ServiceResponse<List<HotCard>>> getHotCardsByCustomerId(@PathVariable String customerId) {
        try {
            List<HotCard> hotCards = hotCardService.getHotCardsByCustomerId(customerId);
            return successList(hotCards);
        } catch (Exception e) {
            logger.error("Error getting hot cards by customer ID: {}", customerId, e);
            return error("Failed to retrieve hot cards");
        }
    }

    /**
     * Create new hot card
     */
    @PostMapping
    public ResponseEntity<ServiceResponse<HotCard>> createHotCard(@RequestBody HotCard hotCard) {
        try {
            HotCard createdHotCard = hotCardService.createHotCard(hotCard);
            return success(createdHotCard, "Hot card created successfully");
        } catch (Exception e) {
            logger.error("Error creating hot card", e);
            return error("Failed to create hot card");
        }
    }

    /**
     * Update hot card
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<HotCard>> updateHotCard(@PathVariable Long id,
            @RequestBody HotCard hotCard) {
        try {
            HotCardPK primaryKey = new HotCardPK();
            primaryKey.setSequenceNo(id.intValue());
            hotCard.setPrimaryKey(primaryKey);
            HotCard updatedHotCard = hotCardService.updateHotCard(hotCard);
            if (updatedHotCard != null) {
                return success(updatedHotCard, "Hot card updated successfully");
            } else {
                return error("Hot card not found");
            }
        } catch (Exception e) {
            logger.error("Error updating hot card: {}", id, e);
            return error("Failed to update hot card");
        }
    }

    /**
     * Delete hot card
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse<Void>> deleteHotCard(@PathVariable Long id) {
        try {
            boolean deleted = hotCardService.deleteHotCard(id);
            if (deleted) {
                return success(null, "Hot card deleted successfully");
            } else {
                return error("Hot card not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting hot card: {}", id, e);
            return error("Failed to delete hot card");
        }
    }

    /**
     * Get hot cards by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ServiceResponse<List<HotCard>>> getHotCardsByStatus(@PathVariable String status) {
        try {
            List<HotCard> hotCards = hotCardService.getHotCardsByStatus(status);
            return successList(hotCards);
        } catch (Exception e) {
            logger.error("Error getting hot cards by status: {}", status, e);
            return error("Failed to retrieve hot cards");
        }
    }

    /**
     * Block/unblock hot card
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceResponse<HotCard>> updateHotCardStatus(@PathVariable Long id,
            @RequestParam String status) {
        try {
            HotCard updatedHotCard = hotCardService.updateHotCardStatus(id, status);
            if (updatedHotCard != null) {
                return success(updatedHotCard, "Hot card status updated successfully");
            } else {
                return error("Hot card not found");
            }
        } catch (Exception e) {
            logger.error("Error updating hot card status: {}", id, e);
            return error("Failed to update hot card status");
        }
    }
}