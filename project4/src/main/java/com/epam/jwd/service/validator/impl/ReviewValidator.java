package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.validator.api.Validator;

import java.rmi.ServerException;

public class ReviewValidator implements Validator<ReviewDto> {

    private final static Integer MIN_GRADE_VALUE = 0;
    private final static Integer MAX_GRADE_VALUE = 10;
    private final static Integer MAX_REVIEW_LENGTH = 300;

    private static final String GRADE_VALUE_EXCEPTION = "The grade must be from 0 to 10";
    private static final String REVIEW_LENGTH_EXCEPTION = "The review must not be longer than 300 characters";

    @Override
    public boolean validate(ReviewDto dto) throws ServerException {
        return false;
    }

    private boolean validateGrade(final Integer grade) throws ServerException {
        if (grade < MIN_GRADE_VALUE || grade > MAX_GRADE_VALUE){
            throw new ServerException(GRADE_VALUE_EXCEPTION);
        }
        return true;
    }

    private boolean validateReview(final String review) throws ServerException {
        if (review.length() > MAX_REVIEW_LENGTH){
            throw new ServerException(REVIEW_LENGTH_EXCEPTION);
        }
        return true;
    }
}
