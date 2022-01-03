package com.epam.jwd.service.converter.impl;

import com.epam.jwd.Dao.impl.CourseDao;
import com.epam.jwd.Dao.model.review.Review;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

/**
 * Class for convert of review
 */
public class ReviewConverter implements Converter<Review, ReviewDto,Integer> {

    CourseDao courseDAO = new CourseDao();

    @Override
    public Review convert(ReviewDto reviewDto) {
        int courseId = courseDAO.findCourseByName(reviewDto.getCourseName()).get(0).getId();
        return new Review(reviewDto.getId(),reviewDto.getUserId(),courseId,reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        String courseName = courseDAO.findById(review.getCourseId()).getName();
        return new ReviewDto(review.getId(),review.getUserId(),courseName,review.getGrade(),review.getComment());
    }
}
