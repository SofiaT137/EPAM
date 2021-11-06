package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.rmi.ServerException;
import java.util.List;

public interface Service<K extends AbstractDto<U>,U> {

    K create(K value) throws ServiceException;
    Boolean update(K value) throws ServiceException, ServerException;
    Boolean delete(K value) throws ServiceException, ServerException;
    K getById(U id) throws ServiceException;
    List<K> getAll() throws ServiceException;
}
