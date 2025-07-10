package com.banking.coses.framework.business;

import com.banking.coses.framework.exception.CosesAppException;
import com.banking.foundation.log.FoundationLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Abstract base class for Entity Beans in the COSES Framework
 * 
 * Provides common functionality for entity management, CRUD operations,
 * and data access in Spring Boot environment.
 */
@Transactional
public abstract class AbstractEntityBean<T, ID> extends AbstractSessionBean {

    @Autowired
    protected ApplicationContext applicationContext;

    protected final FoundationLogger logger = FoundationLogger.getLogger(getClass());

    protected JpaRepository<T, ID> repository;

    public AbstractEntityBean() {
        super();
    }

    /**
     * Set the repository for this entity bean
     */
    protected void setRepository(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * Create a new entity
     */
    public T create(T entity) throws CosesAppException {
        try {
            validateSession();
            validateEntity(entity);

            T savedEntity = repository.save(entity);
            logger.info("Entity created successfully - Type: {}, ID: {}, User: {}",
                    entity.getClass().getSimpleName(), getEntityId(entity), currentUser);

            return savedEntity;
        } catch (Exception e) {
            logger.error("Failed to create entity - Type: {}, Error: {}",
                    entity.getClass().getSimpleName(), e.getMessage());
            throw new CosesAppException("ENTITY_CREATE_ERROR", "Failed to create entity", e);
        }
    }

    /**
     * Find entity by ID
     */
    public Optional<T> findById(ID id) throws CosesAppException {
        try {
            validateSession();
            validateId(id);

            Optional<T> entity = repository.findById(id);
            if (entity.isPresent()) {
                logger.debug("Entity found - Type: {}, ID: {}, User: {}",
                        entity.get().getClass().getSimpleName(), id, currentUser);
            } else {
                logger.debug("Entity not found - Type: {}, ID: {}, User: {}",
                        getEntityTypeName(), id, currentUser);
            }

            return entity;
        } catch (Exception e) {
            logger.error("Failed to find entity - Type: {}, ID: {}, Error: {}",
                    getEntityTypeName(), id, e.getMessage());
            throw new CosesAppException("ENTITY_FIND_ERROR", "Failed to find entity", e);
        }
    }

    /**
     * Find all entities
     */
    public List<T> findAll() throws CosesAppException {
        try {
            validateSession();

            List<T> entities = repository.findAll();
            logger.debug("Found {} entities - Type: {}, User: {}",
                    entities.size(), getEntityTypeName(), currentUser);

            return entities;
        } catch (Exception e) {
            logger.error("Failed to find all entities - Type: {}, Error: {}",
                    getEntityTypeName(), e.getMessage());
            throw new CosesAppException("ENTITY_FIND_ALL_ERROR", "Failed to find all entities", e);
        }
    }

    /**
     * Update an existing entity
     */
    public T update(T entity) throws CosesAppException {
        try {
            validateSession();
            validateEntity(entity);
            validateEntityExists(entity);

            T updatedEntity = repository.save(entity);
            logger.info("Entity updated successfully - Type: {}, ID: {}, User: {}",
                    entity.getClass().getSimpleName(), getEntityId(entity), currentUser);

            return updatedEntity;
        } catch (Exception e) {
            logger.error("Failed to update entity - Type: {}, ID: {}, Error: {}",
                    entity.getClass().getSimpleName(), getEntityId(entity), e.getMessage());
            throw new CosesAppException("ENTITY_UPDATE_ERROR", "Failed to update entity", e);
        }
    }

    /**
     * Delete an entity by ID
     */
    public void deleteById(ID id) throws CosesAppException {
        try {
            validateSession();
            validateId(id);
            validateEntityExists(id);

            repository.deleteById(id);
            logger.info("Entity deleted successfully - Type: {}, ID: {}, User: {}",
                    getEntityTypeName(), id, currentUser);

        } catch (Exception e) {
            logger.error("Failed to delete entity - Type: {}, ID: {}, Error: {}",
                    getEntityTypeName(), id, e.getMessage());
            throw new CosesAppException("ENTITY_DELETE_ERROR", "Failed to delete entity", e);
        }
    }

    /**
     * Delete an entity
     */
    public void delete(T entity) throws CosesAppException {
        try {
            validateSession();
            validateEntity(entity);

            repository.delete(entity);
            logger.info("Entity deleted successfully - Type: {}, ID: {}, User: {}",
                    entity.getClass().getSimpleName(), getEntityId(entity), currentUser);

        } catch (Exception e) {
            logger.error("Failed to delete entity - Type: {}, ID: {}, Error: {}",
                    entity.getClass().getSimpleName(), getEntityId(entity), e.getMessage());
            throw new CosesAppException("ENTITY_DELETE_ERROR", "Failed to delete entity", e);
        }
    }

    /**
     * Check if entity exists by ID
     */
    public boolean existsById(ID id) throws CosesAppException {
        try {
            validateSession();
            validateId(id);

            boolean exists = repository.existsById(id);
            logger.debug("Entity exists check - Type: {}, ID: {}, Exists: {}, User: {}",
                    getEntityTypeName(), id, exists, currentUser);

            return exists;
        } catch (Exception e) {
            logger.error("Failed to check entity existence - Type: {}, ID: {}, Error: {}",
                    getEntityTypeName(), id, e.getMessage());
            throw new CosesAppException("ENTITY_EXISTS_ERROR", "Failed to check entity existence", e);
        }
    }

    /**
     * Count all entities
     */
    public long count() throws CosesAppException {
        try {
            validateSession();

            long count = repository.count();
            logger.debug("Entity count - Type: {}, Count: {}, User: {}",
                    getEntityTypeName(), count, currentUser);

            return count;
        } catch (Exception e) {
            logger.error("Failed to count entities - Type: {}, Error: {}",
                    getEntityTypeName(), e.getMessage());
            throw new CosesAppException("ENTITY_COUNT_ERROR", "Failed to count entities", e);
        }
    }

    /**
     * Validate entity
     */
    protected void validateEntity(T entity) throws CosesAppException {
        if (entity == null) {
            throw new CosesAppException("ENTITY_VALIDATION_ERROR", "Entity cannot be null");
        }
    }

    /**
     * Validate entity ID
     */
    protected void validateId(ID id) throws CosesAppException {
        if (id == null) {
            throw new CosesAppException("ENTITY_VALIDATION_ERROR", "Entity ID cannot be null");
        }
    }

    /**
     * Validate entity exists
     */
    protected void validateEntityExists(T entity) throws CosesAppException {
        ID id = getEntityId(entity);
        if (!existsById(id)) {
            throw new CosesAppException("ENTITY_NOT_FOUND", "Entity does not exist with ID: " + id);
        }
    }

    /**
     * Validate entity exists by ID
     */
    protected void validateEntityExists(ID id) throws CosesAppException {
        if (!existsById(id)) {
            throw new CosesAppException("ENTITY_NOT_FOUND", "Entity does not exist with ID: " + id);
        }
    }

    /**
     * Get entity ID - to be implemented by subclasses
     */
    protected abstract ID getEntityId(T entity);

    /**
     * Get entity type name
     */
    protected String getEntityTypeName() {
        return getClass().getSimpleName();
    }

    /**
     * Get repository
     */
    protected JpaRepository<T, ID> getRepository() {
        return repository;
    }
}