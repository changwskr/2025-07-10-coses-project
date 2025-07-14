package com.skcc.oversea.reference.business.facade;

import java.util.List;

public interface IReferenceManagementSB {

    List<Object> getReferenceData(String referenceType);

    Object getReferenceById(String referenceId);

    void updateReference(Object reference);

    void deleteReference(String referenceId);

    boolean existsReference(String referenceId);

}