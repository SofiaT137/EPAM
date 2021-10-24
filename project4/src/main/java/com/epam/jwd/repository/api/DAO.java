package com.epam.jwd.repository.api;

import com.epam.jwd.repository.model.AbstractEntity;

import java.util.List;

public interface DAO <T extends AbstractEntity<K>,K> {
    T save(T entity);
    Boolean update(T entity);
    Boolean delete(T entity);
    List<T> findAll();
    T findById(K id);
}
