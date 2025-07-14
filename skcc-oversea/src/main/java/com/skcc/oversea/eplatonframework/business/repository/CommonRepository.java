package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.Common;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Common Repository
 */
@Repository
public interface CommonRepository extends JpaRepository<Common, Long> {

    /**
     * Find by common code
     */
    Optional<Common> findByCommonCode(String commonCode);

    /**
     * Find by common type
     */
    List<Common> findByCommonType(String commonType);

    /**
     * Find by is active
     */
    List<Common> findByIsActive(Boolean isActive);

    /**
     * Find by common type and is active
     */
    List<Common> findByCommonTypeAndIsActive(String commonType, Boolean isActive);

    /**
     * Find by effective date
     */
    @Query("SELECT c FROM Common c WHERE c.effectiveDate <= :currentDate")
    List<Common> findEffectiveByDate(@Param("currentDate") LocalDate currentDate);

    /**
     * Find by expiry date
     */
    @Query("SELECT c FROM Common c WHERE c.expiryDate >= :currentDate")
    List<Common> findNotExpiredByDate(@Param("currentDate") LocalDate currentDate);

    /**
     * Find by common value
     */
    List<Common> findByCommonValue(String commonValue);

    /**
     * Find by description containing
     */
    @Query("SELECT c FROM Common c WHERE c.description LIKE %:keyword%")
    List<Common> findByDescriptionContaining(@Param("keyword") String keyword);
}