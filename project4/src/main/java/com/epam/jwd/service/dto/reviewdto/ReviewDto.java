package com.epam.jwd.service.dto.reviewdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class ReviewDto extends AbstractDto<Integer> {

    private int user_id;
    private int course_id;
    private int grade;
    private String review;

    public ReviewDto(int user_id, int course_id, int grade, String review) {
        this.user_id = user_id;
        this.course_id = course_id;
        this.grade = grade;
        this.review = review;
    }

    public ReviewDto(Integer id, int user_id, int course_id, int grade, String review) {
        this.id = id;
        this.user_id = user_id;
        this.course_id = course_id;
        this.grade = grade;
        this.review = review;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
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
        return user_id == reviewDto.getUser_id()
                && course_id == reviewDto.getCourse_id()
                && grade == reviewDto.getGrade()
                && Objects.equals(review, reviewDto.getReview())
                && Objects.equals(id, reviewDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, course_id, grade, review,id);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", course_id=" + course_id +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                '}';
    }
}
