package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The review validator
 */

public class ReviewValidator implements Validator<ReviewDto> {

    private static final Logger LOGGER = LogManager.getLogger(ReviewValidator.class);

    private static final Integer MIN_GRADE_VALUE = 0;
    private static final Integer MAX_GRADE_VALUE = 10;
    private static final Integer MAX_REVIEW_LENGTH = 300;

    private static final String GRADE_VALUE_EXCEPTION = "The grade must be from 0 to 10";
    private static final String REVIEW_LENGTH_EXCEPTION = "The review must not be longer than 300 characters";

    @Override
    public void validate(ReviewDto dto) throws ServiceException {
       validateGrade(dto.getGrade());
       validateReview(dto.getReview());
    }


    private void validateGrade(final Integer grade) throws ServiceException  {
        if (grade < MIN_GRADE_VALUE || grade > MAX_GRADE_VALUE){
            LOGGER.error(GRADE_VALUE_EXCEPTION);
            throw new ServiceException(GRADE_VALUE_EXCEPTION);
        }
    }


    private void validateReview(final String review) throws ServiceException  {
        if (review.length() > MAX_REVIEW_LENGTH){
            LOGGER.error(REVIEW_LENGTH_EXCEPTION);
            throw new ServiceException(REVIEW_LENGTH_EXCEPTION);
        }
    }
}
