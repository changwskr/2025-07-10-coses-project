package com.chb.coses.framework.business.dao;

import java.util.List;
import java.util.ArrayList;

/**
 * Abstract base class for Data Access Objects in the EPlaton Framework
 */
public abstract class AbstractDAO<T, ID> implements IDAO<T, ID> {

    protected List<T> entities = new ArrayList<>();

    @Override
    public T findById(ID id) {
        // Default implementation - override in subclasses
        return null;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public T save(T entity) {
        entities.add(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        // Default implementation - override in subclasses
        return entity;
    }

    @Override
    public void deleteById(ID id) {
        // Default implementation - override in subclasses
    }

    @Override
    public void delete(T entity) {
        entities.remove(entity);
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id) != null;
    }

    @Override
    public long count() {
        return entities.size();
    }

    /**
     * Get the entity class
     * 
     * @return entity class
     */
    protected abstract Class<T> getEntityClass();

    /**
     * Get the ID class
     * 
     * @return ID class
     */
    protected abstract Class<ID> getIdClass();
}