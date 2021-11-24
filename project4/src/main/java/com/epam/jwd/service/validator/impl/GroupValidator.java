package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupValidator implements Validator<GroupDto> {

    private static final String GROUP_NAME_REGEX  = "^[A-Za-z0-9,.'-]+$";

    private final static Integer MIN_NAME_LENGTH = 3;
    private final static Integer MAX_NAME_LENGTH = 20;

    private static final String NAME_LENGTH_EXCEPTION = "The length must be more than 3 character and less that 20 characters";
    private static final String GROUP_NAME_EXCEPTION = "Please, enter the valid group name.";

    @Override
    public void validate(GroupDto dto) throws ServiceException {
        validateName(dto.getName());
    }

    private void validateName(final String name) throws ServiceException {
        if (name.length() < MIN_NAME_LENGTH && name.length() > MAX_NAME_LENGTH){
            throw new ServiceException(NAME_LENGTH_EXCEPTION);
        }
        Pattern passwordPattern = Pattern.compile(GROUP_NAME_REGEX);
        Matcher matcher = passwordPattern.matcher(name);
        if(!matcher.find()){
            throw new ServiceException(GROUP_NAME_EXCEPTION);
        }
    }
}
