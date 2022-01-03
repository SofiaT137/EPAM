package com.epam.jwd.service.validator;

import com.epam.jwd.service.exception.ServiceException;


/**
 * Validator
 * @param <T>
 */
public interface Validator <T>{
    /**
     * Validate method
     * @param dto Data Transfer Object
     * @throws ServiceException exception
     */
    void validate(T dto) throws ServiceException;
}
