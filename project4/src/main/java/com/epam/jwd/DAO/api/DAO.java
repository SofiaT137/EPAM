package com.epam.jwd.DAO.api;

import com.epam.jwd.DAO.model.AbstractEntity;

import java.util.List;

/**
 * Interface for data access object
 * @param <T> object of model
 * @param <K> id of DAO object
 */
public interface DAO <T extends AbstractEntity<K>,K> {

    /**
     * Save object of model
     * @param entity object
     * @return id saved object
     */
    K save(T entity) ;

    /**
     * Update
     * @param entity object
     * @return was true
     */
    Boolean update(T entity) ;

    /**
     * delete object
     * @param entity entity
     * @return true, when deleted
     */
    Boolean delete(T entity);
    
    List<T> findAll();
    T findById(K id);
}
