package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.impl.CourseDaoImpl;
import com.epam.jwd.dao.model.review.Review;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

/**
 * Class for convert of review
 */
public class ReviewConverter implements Converter<Review, ReviewDto,Integer> {

    CourseDaoImpl courseDAOImpl = new CourseDaoImpl();

    @Override
    public Review convert(ReviewDto reviewDto) {
        int courseId = courseDAOImpl.findCourseByName(reviewDto.getCourseName()).get(0).getId();
        return new Review(reviewDto.getId(),reviewDto.getUserId(),courseId,reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        String courseName = courseDAOImpl.findById(review.getCourseId()).getName();
        return new ReviewDto(review.getId(),review.getUserId(),courseName,review.getGrade(),review.getComment());
    }
}
