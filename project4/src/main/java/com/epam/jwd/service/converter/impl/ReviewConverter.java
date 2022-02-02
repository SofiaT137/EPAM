package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.UserDao;
import com.epam.jwd.dao.impl.CourseDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.review.Review;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

/**
 * Class for convert of review
 */
public class ReviewConverter implements Converter<Review, ReviewDto,Integer> {

    CourseDaoImpl courseDAOImpl = new CourseDaoImpl();
    UserDaoImpl userDaoImpl = new UserDaoImpl();

    @Override
    public Review convert(ReviewDto reviewDto) {
        int courseId = courseDAOImpl.findCourseByName(reviewDto.getCourseName()).get(0).getId();
        String firstName = reviewDto.getFirstName();
        String lastName = reviewDto.getLastName();
        int userId = (userDaoImpl.findUserByFirstNameAndLastName(firstName,lastName)).get(0).getId();
        return new Review(reviewDto.getId(),userId,courseId,reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        String courseName = courseDAOImpl.findById(review.getCourseId()).getName();
        String firstName = userDaoImpl.findById(review.getUserId()).getFirstName();
        String lastName = userDaoImpl.findById(review.getUserId()).getLastName();
        return new ReviewDto(review.getId(),firstName,lastName,courseName,review.getGrade(),review.getComment());
    }
}
