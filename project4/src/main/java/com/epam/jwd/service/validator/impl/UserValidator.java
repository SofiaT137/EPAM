package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.validator.api.Validator;

import java.rmi.ServerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<UserDto> {

    private static String FIRST_AND_LAST_NAME_REGEX = "^[a-zA-Z '.-]*$";

    private final static Integer MIN_NAME_LENGTH = 2;
    private final static Integer MAX_NAME_LENGTH = 20;

    private static final String NAME_LENGTH_EXCEPTION = "The name length must be from 2 to 20 characters";

    @Override
    public boolean validate(UserDto dto) throws ServerException {
        return validateName(dto.getFirst_name())
                && validateName(dto.getLast_name());
    }

    private boolean validateName(final String name) throws ServerException {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH){
            throw new ServerException(NAME_LENGTH_EXCEPTION);
        }
        Pattern namePattern = Pattern.compile(FIRST_AND_LAST_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        return matcher.find();
    }
}
