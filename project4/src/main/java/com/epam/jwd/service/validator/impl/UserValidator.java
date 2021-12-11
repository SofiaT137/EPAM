package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<UserDto> {

    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);

    private static final String FIRST_AND_LAST_NAME_REGEX = "^[a-zA-Zа-яА-Я '.-]*$";

    private static final  Integer MIN_NAME_LENGTH = 2;
    private static final  Integer MAX_NAME_LENGTH = 20;

    private static final String NAME_LENGTH_EXCEPTION = "The name length must be from 2 to 20 characters";
    private static final String USER_NAME_EXCEPTION = "I can't accept this user, some field(s) are wrong.";

    @Override
    public void validate(UserDto dto) throws ServiceException {
         validateName(dto.getFirstName());
         validateName(dto.getLastName());
    }

    private void validateName(final String name) throws ServiceException {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH){
            LOGGER.error(NAME_LENGTH_EXCEPTION);
            throw new ServiceException(NAME_LENGTH_EXCEPTION);
        }
        Pattern namePattern = Pattern.compile(FIRST_AND_LAST_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        if(!matcher.find()){
            LOGGER.error(USER_NAME_EXCEPTION);
            throw new ServiceException(USER_NAME_EXCEPTION);
        }
    }
}
