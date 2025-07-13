package com.skcc.oversea.eplatonframework.business.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.skcc.oversea.foundation.logej.LOGEJ;

import java.util.List;
import java.util.Optional;

/**
 * Base Repository Implementation for SKCC Oversea
 * 
 * Provides common repository functionality for all repositories.
 */
public abstract class BaseRepositoryImpl<T, ID> implements BaseRepository<T, ID> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Save entity
     */
    @Override
    @Transactional
    public T save(T entity) {
        try {
            logger.debug("Saving entity: {}", entity);

            if (!validateEntity(entity)) {
                throw new IllegalArgumentException("Invalid entity");
            }

            T savedEntity = performSave(entity);

            auditOperation("SAVE", savedEntity, getCurrentUserId());

            logger.debug("Entity saved successfully: {}", savedEntity);
            return savedEntity;

        } catch (Exception e) {
            logger.error("Error saving entity: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Save all entities
     */
    @Override
    @Transactional
    public List<T> saveAll(List<T> entities) {
        try {
            logger.debug("Saving {} entities", entities.size());

            for (T entity : entities) {
                if (!validateEntity(entity)) {
                    throw new IllegalArgumentException("Invalid entity in batch");
                }
            }

            List<T> savedEntities = performSaveAll(entities);

            for (T entity : savedEntities) {
                auditOperation("SAVE_ALL", entity, getCurrentUserId());
            }

            logger.debug("All entities saved successfully");
            return savedEntities;

        } catch (Exception e) {
            logger.error("Error saving entities: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Find by ID
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        try {
            logger.debug("Finding entity by ID: {}", id);

            Optional<T> entity = performFindById(id);

            if (entity.isPresent()) {
                auditOperation("FIND_BY_ID", entity.get(), getCurrentUserId());
            }

            logger.debug("Entity found: {}", entity.isPresent());
            return entity;

        } catch (Exception e) {
            logger.error("Error finding entity by ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Find all entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        try {
            logger.debug("Finding all entities");

            List<T> entities = performFindAll();

            logger.debug("Found {} entities", entities.size());
            return entities;

        } catch (Exception e) {
            logger.error("Error finding all entities: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Find all entities with pagination
     */
    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        try {
            logger.debug("Finding entities with pagination: {}", pageable);

            Page<T> page = performFindAll(pageable);

            logger.debug("Found {} entities on page {}", page.getContent().size(), page.getNumber());
            return page;

        } catch (Exception e) {
            logger.error("Error finding entities with pagination: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Find by criteria
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findByCriteria(Object criteria) {
        try {
            logger.debug("Finding entities by criteria: {}", criteria);

            List<T> entities = performFindByCriteria(criteria);

            logger.debug("Found {} entities by criteria", entities.size());
            return entities;

        } catch (Exception e) {
            logger.error("Error finding entities by criteria: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Delete by ID
     */
    @Override
    @Transactional
    public void deleteById(ID id) {
        try {
            logger.debug("Deleting entity by ID: {}", id);

            Optional<T> entity = findById(id);
            if (entity.isPresent()) {
                auditOperation("DELETE_BY_ID", entity.get(), getCurrentUserId());
            }

            performDeleteById(id);

            logger.debug("Entity deleted successfully");

        } catch (Exception e) {
            logger.error("Error deleting entity by ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Delete entity
     */
    @Override
    @Transactional
    public void delete(T entity) {
        try {
            logger.debug("Deleting entity: {}", entity);

            auditOperation("DELETE", entity, getCurrentUserId());

            performDelete(entity);

            logger.debug("Entity deleted successfully");

        } catch (Exception e) {
            logger.error("Error deleting entity: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Delete all entities
     */
    @Override
    @Transactional
    public void deleteAll(List<T> entities) {
        try {
            logger.debug("Deleting {} entities", entities.size());

            for (T entity : entities) {
                auditOperation("DELETE_ALL", entity, getCurrentUserId());
            }

            performDeleteAll(entities);

            logger.debug("All entities deleted successfully");

        } catch (Exception e) {
            logger.error("Error deleting entities: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Check if entity exists by ID
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        try {
            logger.debug("Checking if entity exists by ID: {}", id);

            boolean exists = performExistsById(id);

            logger.debug("Entity exists: {}", exists);
            return exists;

        } catch (Exception e) {
            logger.error("Error checking if entity exists by ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Count all entities
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        try {
            logger.debug("Counting all entities");

            long count = performCount();

            logger.debug("Entity count: {}", count);
            return count;

        } catch (Exception e) {
            logger.error("Error counting entities: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Count by criteria
     */
    @Override
    @Transactional(readOnly = true)
    public long countByCriteria(Object criteria) {
        try {
            logger.debug("Counting entities by criteria: {}", criteria);

            long count = performCountByCriteria(criteria);

            logger.debug("Entity count by criteria: {}", count);
            return count;

        } catch (Exception e) {
            logger.error("Error counting entities by criteria: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Validate entity
     */
    @Override
    public boolean validateEntity(T entity) {
        if (entity == null) {
            return false;
        }

        // Subclasses should override this method to provide specific validation
        return true;
    }

    /**
     * Audit entity operation
     */
    @Override
    public void auditOperation(String operation, T entity, String userId) {
        try {
            String auditMessage = String.format(
                    "Operation: %s, Entity: %s, User: %s, Repository: %s",
                    operation, entity, userId, getClass().getSimpleName());

            LOGEJ.logAudit(generateTransactionId(), operation, "SUCCESS", auditMessage);

        } catch (Exception e) {
            logger.warn("Failed to audit operation: {}", e.getMessage());
        }
    }

    // Abstract methods that subclasses must implement
    protected abstract T performSave(T entity);

    protected abstract List<T> performSaveAll(List<T> entities);

    protected abstract Optional<T> performFindById(ID id);

    protected abstract List<T> performFindAll();

    protected abstract Page<T> performFindAll(Pageable pageable);

    protected abstract List<T> performFindByCriteria(Object criteria);

    protected abstract void performDeleteById(ID id);

    protected abstract void performDelete(T entity);

    protected abstract void performDeleteAll(List<T> entities);

    protected abstract boolean performExistsById(ID id);

    protected abstract long performCount();

    protected abstract long performCountByCriteria(Object criteria);

    // Helper methods
    protected String getCurrentUserId() {
        // TODO: Implement user context retrieval
        return "SYSTEM";
    }

    protected String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
    }
}