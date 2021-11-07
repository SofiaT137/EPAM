package com.epam.jwd.DAO.api;

import com.epam.jwd.DAO.model.AbstractEntity;

import java.util.List;

public interface DAO <T extends AbstractEntity<K>,K> {
    K save(T entity);
    Boolean update(T entity);
    Boolean delete(T entity);
    List<T> findAll();
    T findById(K id);
}
