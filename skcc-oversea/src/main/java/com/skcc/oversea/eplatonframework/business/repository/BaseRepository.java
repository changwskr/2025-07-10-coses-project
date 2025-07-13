package com.skcc.oversea.eplatonframework.business.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Base Repository Interface for SKCC Oversea
 * 
 * Defines common repository operations that all repositories should support.
 */
public interface BaseRepository<T, ID> {

    /**
     * Save entity
     */
    T save(T entity);

    /**
     * Save all entities
     */
    List<T> saveAll(List<T> entities);

    /**
     * Find by ID
     */
    Optional<T> findById(ID id);

    /**
     * Find all entities
     */
    List<T> findAll();

    /**
     * Find all entities with pagination
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Find by criteria
     */
    List<T> findByCriteria(Object criteria);

    /**
     * Delete by ID
     */
    void deleteById(ID id);

    /**
     * Delete entity
     */
    void delete(T entity);

    /**
     * Delete all entities
     */
    void deleteAll(List<T> entities);

    /**
     * Check if entity exists by ID
     */
    boolean existsById(ID id);

    /**
     * Count all entities
     */
    long count();

    /**
     * Count by criteria
     */
    long countByCriteria(Object criteria);

    /**
     * Validate entity
     */
    boolean validateEntity(T entity);

    /**
     * Audit entity operation
     */
    void auditOperation(String operation, T entity, String userId);
}