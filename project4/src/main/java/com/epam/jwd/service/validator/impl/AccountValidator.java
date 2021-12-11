package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator implements Validator<AccountDto> {

    private static final Logger LOGGER = LogManager.getLogger(AccountValidator.class);

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$";
    private static final String LOGIN_REGEX  = "^[A-Za-z0-9,.'-]+$";

    private static final Integer MIN_PASSWORD_LENGTH = 8;
    private static final  Integer MIN_LOGIN_LENGTH = 4;

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
            LOGGER.error(PASSWORD_LENGTH_EXCEPTION);
            throw new ServiceException(PASSWORD_LENGTH_EXCEPTION);
        }
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = passwordPattern.matcher(password);
        if(!matcher.find()){
            LOGGER.error(PASSWORD_NAME_EXCEPTION);
            throw new ServiceException(PASSWORD_NAME_EXCEPTION);
        }
    }

    private void validateLogin(final String login) throws ServiceException {
        if (login.length() < MIN_LOGIN_LENGTH){
            LOGGER.error(LOGIN_LENGTH_EXCEPTION);
            throw new ServiceException(LOGIN_LENGTH_EXCEPTION);
        }
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = loginPattern.matcher(login);
        if(!matcher.find()){
            LOGGER.error(LOGIN_NAME_EXCEPTION);
            throw new ServiceException(LOGIN_NAME_EXCEPTION);
        }
    }
}

