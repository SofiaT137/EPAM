package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.rmi.ServerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator implements Validator<AccountDto> {

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)$";
    private static final String LOGIN_REGEX  = "\\b[a-zA-Z][a-zA-Z0-9\\-._]\\b";

    private final static Integer MIN_PASSWORD_LENGTH = 8;
    private final static Integer MIN_LOGIN_LENGTH = 4;

    private static final String PASSWORD_LENGTH_EXCEPTION = "The length must be more than 8 character";
    private static final String PASSWORD_NAME_EXCEPTION = "Please, enter the valid password.";
    private static final String LOGIN_LENGTH_EXCEPTION = "The length must be more than 4 character";
    private static final String LOGIN_NAME_EXCEPTION = "Please, enter the valid login.";

    @Override
    public void validate(AccountDto dto) throws ServiceException {
         validatePassword(dto.getPassword());
         validateLogin(dto.getLogin());
    }

    private void validatePassword(final String password) throws ServiceException {
        if (password.length() < MIN_PASSWORD_LENGTH){
            throw new ServiceException(PASSWORD_LENGTH_EXCEPTION);
        }
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = passwordPattern.matcher(password);
        if(!matcher.find()){
            throw new ServiceException(PASSWORD_NAME_EXCEPTION);
        }
    }

    private void validateLogin(final String login) throws ServiceException {
        if (login.length() < MIN_LOGIN_LENGTH){
            throw new ServiceException(LOGIN_LENGTH_EXCEPTION);
        }
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = loginPattern.matcher(login);
        if(!matcher.find()){
            throw new ServiceException(LOGIN_NAME_EXCEPTION);
        }
    }
}

