package com.epam.jwd.service.validator.api;

import java.rmi.ServerException;

public interface Validator <T>{
    boolean validate(T dto) throws ServerException;
}
