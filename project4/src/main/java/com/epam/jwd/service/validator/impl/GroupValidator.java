package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The group validator
 */
public class GroupValidator implements Validator<GroupDto> {

    private static final Logger LOGGER = LogManager.getLogger(GroupValidator.class);

    private static final String GROUP_NAME_REGEX  = "^[A-Za-z0-9,.'-]+$";

    private static final Integer MIN_NAME_LENGTH = 3;
    private static final Integer MAX_NAME_LENGTH = 20;

    private static final String NAME_LENGTH_EXCEPTION = "The length must be more than 3 character and less that 20 characters";
    private static final String GROUP_NAME_EXCEPTION = "Please, enter the valid group name.";

    @Override
    public void validate(GroupDto dto) throws ServiceException {
        validateName(dto.getName());
    }
    /**
     * Validate name method
     * @param name group name
     * @throws ServiceException exception
     */
    private void validateName(final String name) throws ServiceException {
        if (name.length() < MIN_NAME_LENGTH && name.length() > MAX_NAME_LENGTH){
            LOGGER.error(NAME_LENGTH_EXCEPTION);
            throw new ServiceException(NAME_LENGTH_EXCEPTION);
        }
        Pattern passwordPattern = Pattern.compile(GROUP_NAME_REGEX);
        Matcher matcher = passwordPattern.matcher(name);
        if(!matcher.find()){
            LOGGER.error(GROUP_NAME_EXCEPTION);
            throw new ServiceException(GROUP_NAME_EXCEPTION);
        }
    }
}
