package com.epam.jwd.service.dto.reviewdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class ReviewDto extends AbstractDto<Integer> {

    private int userId;
    private String courseName;
    private int grade;
    private String review;

    public ReviewDto(int userId, String courseName, int grade, String review) {
        this.userId = userId;
        this.courseName = courseName;
        this.grade = grade;
        this.review = review;
    }

    public ReviewDto(Integer id,int userId, String courseName, int grade, String review) {
        this.id = id;
        this.userId = userId;
        this.courseName = courseName;
        this.grade = grade;
        this.review = review;
    }

    public ReviewDto(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewDto)) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return userId == reviewDto.userId
                && grade == reviewDto.grade
                && Objects.equals(courseName, reviewDto.courseName)
                && Objects.equals(review, reviewDto.review)
                && Objects.equals(id,reviewDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, courseName, grade, review);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", user_id=" + userId +
                ", course_name='" + courseName + '\'' +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                '}';
    }
}
