package com.skcc.oversea.framework.business.dao;

import java.util.List;

public interface IDAO<T, ID> {
    T findById(ID id);

    List<T> findAll();

    T save(T entity);

    void delete(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);

    long count();
}