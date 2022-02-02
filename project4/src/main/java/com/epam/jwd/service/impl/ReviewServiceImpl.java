package com.epam.jwd.service.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.impl.ReviewDaoImpl;
import com.epam.jwd.dao.model.review.Review;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.converter.impl.ReviewConverter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.impl.ReviewValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The review service
 */
public class ReviewServiceImpl implements Service<ReviewDto,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(ReviewServiceImpl.class);

    private final Dao<Review,Integer> reviewDao = new ReviewDaoImpl();
    private final Validator<ReviewDto> reviewValidator= new ReviewValidator();
    private final Converter<Review, ReviewDto, Integer> reviewConverter = new ReviewConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String REVIEW_NOT_FOUND_EXCEPTION = "This review is not found!";
    private static final String CANNOT_FIND_REVIEW_EXCEPTION = "I can't find this review by its user_id";

    @Override
    public ReviewDto create(ReviewDto value) throws ServiceException {
        reviewValidator.validate(value);
        Review review = reviewConverter.convert(value);
        reviewDao.save(review);
        return reviewConverter.convert(review);
    }

    @Override
    public Boolean update(ReviewDto value) throws ServiceException {
        reviewValidator.validate(value);
        return reviewDao.update(reviewConverter.convert(value));
    }

    @Override
    public Boolean delete(ReviewDto value) throws ServiceException{
        reviewValidator.validate(value);
        return reviewDao.delete(reviewConverter.convert(value));
    }

    @Override
    public ReviewDto getById(Integer id) throws ServiceException {
        if (id == null) {
            LOGGER.error(ID_IS_NULL_EXCEPTION);
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Review review = reviewDao.findById(id);
        if (review == null){
            LOGGER.error(REVIEW_NOT_FOUND_EXCEPTION);
            throw new ServiceException(REVIEW_NOT_FOUND_EXCEPTION);
        }
        return reviewConverter.convert(review);
    }


    @Override
    public List<ReviewDto> findAll() throws ServiceException {
        List<Review> daoGetAll = reviewDao.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        if (!(daoGetAll.isEmpty())){
            daoGetAll.forEach(review -> reviewDtoList.add(reviewConverter.convert(review)));
        }
        return reviewDtoList;
    }

    /**
     * Find review by User ID
     * @param userId User ID
     * @return list of ReviewDto
     */
    public List<ReviewDto> filterReview(int userId) {
        List<Review> daoGetAll = ((ReviewDaoImpl) reviewDao).findReviewByUserId(userId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            LOGGER.error(CANNOT_FIND_REVIEW_EXCEPTION);
            throw new ServiceException(CANNOT_FIND_REVIEW_EXCEPTION);
        }
        daoGetAll.forEach(review -> reviewDtoList.add(reviewConverter.convert(review)));
        return reviewDtoList;
    }

    /**
     *  Find review by Course ID and User ID
     * @param courseId Course ID
     * @param userId User ID
     * @return ReviewDto object
     */
    public ReviewDto findReviewByCourseIdAndUserId(int courseId,int userId) {
        Review review = ((ReviewDaoImpl) reviewDao).findReviewByCourseIdAndUserId(courseId,userId);
        return reviewConverter.convert(review);
    }

    /**
     * Find review by Course ID
     * @param courseId Course ID
     * @return list of ReviewDto objects
     */
    public List<ReviewDto> findReviewByCourseId(int courseId) {
        List<Review> daoGetAll = ((ReviewDaoImpl) reviewDao).findReviewByCourseId(courseId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            LOGGER.error(CANNOT_FIND_REVIEW_EXCEPTION);
            throw new ServiceException(CANNOT_FIND_REVIEW_EXCEPTION);
        }
        daoGetAll.forEach(review -> reviewDtoList.add(reviewConverter.convert(review)));
        return reviewDtoList;
    }

}


