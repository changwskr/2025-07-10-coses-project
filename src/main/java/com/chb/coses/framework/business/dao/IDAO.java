package com.chb.coses.framework.business.dao;

import java.util.List;

/**
 * Base interface for all Data Access Objects in the EPlaton Framework
 */
public interface IDAO<T, ID> {

    /**
     * Find entity by ID
     * 
     * @param id entity ID
     * @return entity or null if not found
     */
    T findById(ID id);

    /**
     * Find all entities
     * 
     * @return list of all entities
     */
    List<T> findAll();

    /**
     * Save entity
     * 
     * @param entity entity to save
     * @return saved entity
     */
    T save(T entity);

    /**
     * Update entity
     * 
     * @param entity entity to update
     * @return updated entity
     */
    T update(T entity);

    /**
     * Delete entity by ID
     * 
     * @param id entity ID
     */
    void deleteById(ID id);

    /**
     * Delete entity
     * 
     * @param entity entity to delete
     */
    void delete(T entity);

    /**
     * Check if entity exists by ID
     * 
     * @param id entity ID
     * @return true if exists, false otherwise
     */
    boolean existsById(ID id);

    /**
     * Count all entities
     * 
     * @return total count
     */
    long count();
}