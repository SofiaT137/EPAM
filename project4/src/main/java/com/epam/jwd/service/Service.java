package com.epam.jwd.service;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * Interface Service
 * @param <K>
 * @param <U>
 */
public interface Service<K extends AbstractDto<U>,U> {

    /**
     * Create Dto Object
     * @param value Dto Object
     * @return Dto Object
     * @throws ServiceException exception
     */
    K create(K value) throws ServiceException;

    /**
     * Update Dto Object
     * @param value Dto Object
     * @return Boolean result
     * @throws ServiceException exception
     */
    Boolean update(K value) throws ServiceException;

    /**
     * Delete Dto Object
     * @param value Dto Object
     * @return Boolean result
     * @throws ServiceException exception
     */
    Boolean delete(K value) throws ServiceException;

    /**
     * Get Dto Object by ID
     * @param id Dto Object ID
     * @return Dto Object
     * @throws ServiceException exception
     */
    K getById(U id) throws ServiceException;

    /**
     * Get all Dto Objects
     * @return list of Dto Objects
     * @throws ServiceException exception
     */
    List<K> findAll();
}
