package com.chb.coses.framework.business.dao;

/**
 * Abstract base class for DAO Factory in the EPlaton Framework
 */
public abstract class AbstractDAOFactory {

    /**
     * Get DAO instance for the specified entity type
     * 
     * @param entityClass entity class
     * @param <T>         entity type
     * @param <ID>        ID type
     * @return DAO instance
     */
    public abstract <T, ID> IDAO<T, ID> getDAO(Class<T> entityClass);

    /**
     * Get DAO instance for the specified entity type and ID type
     * 
     * @param entityClass entity class
     * @param idClass     ID class
     * @param <T>         entity type
     * @param <ID>        ID type
     * @return DAO instance
     */
    public abstract <T, ID> IDAO<T, ID> getDAO(Class<T> entityClass, Class<ID> idClass);

    /**
     * Check if DAO exists for the specified entity type
     * 
     * @param entityClass entity class
     * @return true if DAO exists, false otherwise
     */
    public abstract boolean hasDAO(Class<?> entityClass);

    /**
     * Register DAO for the specified entity type
     * 
     * @param entityClass entity class
     * @param dao         DAO instance
     * @param <T>         entity type
     * @param <ID>        ID type
     */
    public abstract <T, ID> void registerDAO(Class<T> entityClass, IDAO<T, ID> dao);
}