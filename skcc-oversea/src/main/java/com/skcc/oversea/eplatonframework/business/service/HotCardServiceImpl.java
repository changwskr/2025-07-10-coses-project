package com.skcc.oversea.eplatonframework.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skcc.oversea.eplatonframework.business.entity.HotCard;
import com.skcc.oversea.eplatonframework.business.repository.HotCardRepository;

import java.util.List;

/**
 * Hot Card Service Implementation for SKCC Oversea
 * 
 * Provides hot card business operations
 * using Spring Boot and modern Java patterns.
 */
@Service
public class HotCardServiceImpl implements HotCardService {

    private static final Logger logger = LoggerFactory.getLogger(HotCardServiceImpl.class);

    @Autowired
    private HotCardRepository hotCardRepository;

    @Override
    public List<HotCard> findAll() {
        logger.info("Finding all hot cards");
        return hotCardRepository.findAll();
    }

    @Override
    public HotCard findById(String hotCardId) {
        logger.info("Finding hot card by ID: {}", hotCardId);
        return hotCardRepository.findById(Long.valueOf(hotCardId)).orElse(null);
    }

    @Override
    public HotCard save(HotCard hotCard) {
        logger.info("Saving hot card: {}", hotCard.getCardNo());
        return hotCardRepository.save(hotCard);
    }

    @Override
    public void deleteById(String hotCardId) {
        logger.info("Deleting hot card by ID: {}", hotCardId);
        hotCardRepository.deleteById(Long.valueOf(hotCardId));
    }

    @Override
    public boolean existsById(String hotCardId) {
        return hotCardRepository.existsById(Long.valueOf(hotCardId));
    }

    @Override
    public List<HotCard> getAllHotCards() {
        logger.info("Getting all hot cards");
        return hotCardRepository.findAll();
    }

    @Override
    public HotCard getHotCardById(Long id) {
        logger.info("Getting hot card by ID: {}", id);
        return hotCardRepository.findById(id).orElse(null);
    }

    @Override
    public HotCard getHotCardByCardNo(String cardNo) {
        logger.info("Getting hot card by card number: {}", cardNo);
        return hotCardRepository.findByCardNo(cardNo).orElse(null);
    }

    @Override
    public List<HotCard> getHotCardsByCustomerId(String customerId) {
        logger.info("Getting hot cards by customer ID: {}", customerId);
        return hotCardRepository.findByCustomerId(customerId);
    }

    @Override
    public HotCard createHotCard(HotCard hotCard) {
        logger.info("Creating hot card: {}", hotCard.getCardNo());
        return hotCardRepository.save(hotCard);
    }

    @Override
    public HotCard updateHotCard(HotCard hotCard) {
        logger.info("Updating hot card: {}", hotCard.getCardNo());
        if (hotCardRepository.existsById(hotCard.getId())) {
            return hotCardRepository.save(hotCard);
        }
        return null;
    }

    @Override
    public boolean deleteHotCard(Long id) {
        logger.info("Deleting hot card by ID: {}", id);
        if (hotCardRepository.existsById(id)) {
            hotCardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<HotCard> getHotCardsByStatus(String status) {
        logger.info("Getting hot cards by status: {}", status);
        return hotCardRepository.findByCardStatus(status);
    }

    @Override
    public HotCard updateHotCardStatus(Long id, String status) {
        logger.info("Updating hot card status for ID: {} to {}", id, status);
        HotCard hotCard = getHotCardById(id);
        if (hotCard != null) {
            hotCard.setCardStatus(status);
            return hotCardRepository.save(hotCard);
        }
        return null;
    }
}