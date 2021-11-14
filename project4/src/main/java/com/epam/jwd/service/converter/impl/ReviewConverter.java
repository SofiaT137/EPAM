package com.epam.jwd.service.converter.impl;

import com.epam.jwd.DAO.impl.CourseDAO;
import com.epam.jwd.DAO.model.review.Review;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

public class ReviewConverter implements Converter<Review, ReviewDto,Integer> {

    CourseDAO courseDAO = new CourseDAO();

    @Override
    public Review convert(ReviewDto reviewDto) {
        int course_id = courseDAO.filterCourse(reviewDto.getCourse_name()).get(0).getId();
        return new Review(reviewDto.getId(),reviewDto.getUser_id(),course_id,reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        String course_name = courseDAO.findById(review.getCourse_id()).getName();
        return new ReviewDto(review.getId(),review.getUser_id(),course_name,review.getGrade(),review.getReview());
    }
}
