package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The course validator
 */
public class CourseValidator implements Validator<CourseDto> {

    private static final Logger LOGGER = LogManager.getLogger(CourseValidator.class);

    private static final String COURSE_NAME_REGEX = "^[a-zA-Zа-яА-Я\\s'+.-]*$";

    private static final  Integer MIN_NAME_LENGTH = 2;
    private static final  Integer MAX_NAME_LENGTH = 30;

    private static final String NAME_LENGTH_EXCEPTION = "The name length must be from 2 to 30 characters.";
    private static final String DATE_SUBTRACTION_EXCEPTION = "The course's end date must be greater than start date.";
    private static final String COURSE_NAME_EXCEPTION = "Please, enter the valid course name.";


    @Override
    public void validate(CourseDto dto) throws ServiceException {
        validateName(dto.getName());
        validateDate(dto.getStartCourse(),dto.getEndCourse());
    }

    /**
     * Validate name method
     * @param name course name
     * @throws ServiceException exception
     */
    private void validateName(final String name) throws ServiceException {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH){
            LOGGER.error(NAME_LENGTH_EXCEPTION);
            throw new ServiceException(NAME_LENGTH_EXCEPTION);
        }
        Pattern namePattern = Pattern.compile(COURSE_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        if(!matcher.find()){
            LOGGER.error(COURSE_NAME_EXCEPTION);
            throw new ServiceException(COURSE_NAME_EXCEPTION);
        }
    }

    /**
     * Validate date method
     * @param startDate start date of the course
     * @param endDate end date of the course
     * @throws ServiceException exception
     */
    private void validateDate(final Date startDate,final Date endDate) throws ServiceException {
        if (startDate.after(endDate)){
            LOGGER.error(DATE_SUBTRACTION_EXCEPTION);
            throw new ServiceException(DATE_SUBTRACTION_EXCEPTION);
        }
    }
}