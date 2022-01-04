package com.epam.jwd.dao;

import com.epam.jwd.dao.model.AbstractEntity;

import java.util.List;

/**
 * Interface for data access object
 * @param <T> object of model
 * @param <K> id of DAO object
 */
public interface Dao<T extends AbstractEntity<K>,K> {

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

    /**
     * Find all objects
     * @return list of all the objects
     */
    List<T> findAll();

    /**
     * Find Object by its id
     * @param id Object's id
     * @return Object
     */
    T findById(K id);
}
