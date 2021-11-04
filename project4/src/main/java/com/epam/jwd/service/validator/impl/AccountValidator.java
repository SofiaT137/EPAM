package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.validator.api.Validator;

import java.rmi.ServerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator implements Validator<AccountDto> {

    private static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)$";
    private static String LOGIN_REGEX  = "\\b[a-zA-Z][a-zA-Z0-9\\-._]\\b";

    private final static Integer MIN_PASSWORD_LENGTH = 8;
    private final static Integer MIN_LOGIN_LENGTH = 4;

    private static final String PASSWORD_LENGTH_EXCEPTION = "The length must be more than 8 character";
    private static final String LOGIN_LENGTH_EXCEPTION = "The length must be more than 4 character";

    @Override
    public boolean validate(AccountDto dto) throws ServerException {
        return validatePassword(dto.getPassword())
                && validateLogin(dto.getLogin());
    }

    private boolean validatePassword(final String password) throws ServerException {
        if (password.length() < MIN_PASSWORD_LENGTH){
            throw new ServerException(PASSWORD_LENGTH_EXCEPTION);
        }
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.find();
    }

    private boolean validateLogin(final String login) throws ServerException {
        if (login.length() < MIN_LOGIN_LENGTH){
            throw new ServerException(LOGIN_LENGTH_EXCEPTION);
        }
        Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = loginPattern.matcher(login);
        return matcher.find();
    }
}

