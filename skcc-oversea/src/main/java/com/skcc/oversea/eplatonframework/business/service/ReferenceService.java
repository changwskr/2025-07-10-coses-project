package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.reference.transfer.ReferenceDTO;
import java.util.List;

public interface ReferenceService {
    List<ReferenceDTO> findAll();

    ReferenceDTO findById(String referenceId);

    ReferenceDTO save(ReferenceDTO reference);

    void deleteById(String referenceId);

    boolean existsById(String referenceId);
}