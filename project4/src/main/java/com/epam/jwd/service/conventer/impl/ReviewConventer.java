package com.epam.jwd.service.conventer.impl;

import com.epam.jwd.repository.model.review.Review;
import com.epam.jwd.service.conventer.api.Conventer;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

public class ReviewConventer implements Conventer<Review, ReviewDto,Integer> {
    @Override
    public Review convert(ReviewDto reviewDto) {
        return new Review(reviewDto.getId(),reviewDto.getUser_id(),reviewDto.getGrade(),reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        return new ReviewDto(review.getId(),review.getUser_id(),review.getGrade(),review.getReview());
    }
}
