package com.epam.jwd.dao;

import com.epam.jwd.dao.model.AbstractEntity;

import java.util.List;

public interface UserDao <T extends AbstractEntity<K>,K>  {

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

