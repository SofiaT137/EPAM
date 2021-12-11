package com.epam.jwd.service.converter.impl;

import com.epam.jwd.DAO.impl.CourseDAO;
import com.epam.jwd.DAO.model.review.Review;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

public class ReviewConverter implements Converter<Review, ReviewDto,Integer> {

    CourseDAO courseDAO = new CourseDAO();

    @Override
    public Review convert(ReviewDto reviewDto) {
        int courseId = courseDAO.filterCourse(reviewDto.getCourseName()).get(0).getId();
        return new Review(reviewDto.getId(),reviewDto.getUserId(),courseId,reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        String courseName = courseDAO.findById(review.getCourseId()).getName();
        return new ReviewDto(review.getId(),review.getUserId(),courseName,review.getGrade(),review.getReview());
    }
}
