package com.epam.jwd.service.converter.impl;

import com.epam.jwd.repository.model.review.Review;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

public class ReviewConverter implements Converter<Review, ReviewDto,Integer> {
    @Override
    public Review convert(ReviewDto reviewDto) {
        return new Review(reviewDto.getId(),reviewDto.getUser_id(),reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        return new ReviewDto(review.getId(),review.getUser_id(),review.getGrade(),review.getReview());
    }
}
