package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.validator.api.Validator;

import java.rmi.ServerException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseValidator implements Validator<CourseDto> {

    private static String COURSE_NAME_REGEX = "^[a-zA-Z '.-]*$";

    private final static Integer MIN_NAME_LENGTH = 2;
    private final static Integer MAX_NAME_LENGTH = 30;

    private static final String NAME_LENGTH_EXCEPTION = "The name length must be from 2 to 30 characters";
    private static final String DATE_SUBTRACTION_EXCEPTION = "The name length must be from 2 to 30 characters";


    @Override
    public boolean validate(CourseDto dto) throws ServerException {
        return validateName(dto.getName())
                && validateDate(dto.getStartCourse(),dto.getEndCourse());
    }

    private boolean validateName(final String name) throws ServerException {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH){
            throw new ServerException(NAME_LENGTH_EXCEPTION);
        }
        Pattern namePattern = Pattern.compile(COURSE_NAME_REGEX);
        Matcher matcher = namePattern.matcher(name);
        return matcher.find();
    }

    private boolean validateDate(final Date start_date,final Date end_date) throws ServerException {
        if (!(start_date.after(end_date))){
            throw new ServerException(DATE_SUBTRACTION_EXCEPTION);
        }
        return true;
    }
}