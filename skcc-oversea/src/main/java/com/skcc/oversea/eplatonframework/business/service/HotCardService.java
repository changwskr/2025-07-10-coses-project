package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.business.entity.HotCard;
import java.util.List;

public interface HotCardService {
    // Basic CRUD operations
    List<HotCard> findAll();

    HotCard findById(String hotCardId);

    HotCard save(HotCard hotCard);

    void deleteById(String hotCardId);

    boolean existsById(String hotCardId);

    // Controller expected methods
    List<HotCard> getAllHotCards();

    HotCard getHotCardById(Long id);

    HotCard getHotCardByCardNo(String cardNo);

    List<HotCard> getHotCardsByCustomerId(String customerId);

    HotCard createHotCard(HotCard hotCard);

    HotCard updateHotCard(HotCard hotCard);

    boolean deleteHotCard(Long id);

    List<HotCard> getHotCardsByStatus(String status);

    HotCard updateHotCardStatus(Long id, String status);
}