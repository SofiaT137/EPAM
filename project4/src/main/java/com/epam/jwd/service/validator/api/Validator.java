package com.epam.jwd.service.validator.api;

import com.epam.jwd.service.exception.ServiceException;


public interface Validator <T>{
    void validate(T dto) throws ServiceException;
}
