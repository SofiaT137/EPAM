package com.epam.jwd.service.dto.reviewdto;

import com.epam.jwd.DAO.model.course.Course;
import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class ReviewDto extends AbstractDto<Integer> {

    private int user_id;
    private String course_name;
    private int grade;
    private String review;

    public ReviewDto(int user_id, String course_name, int grade, String review) {
        this.user_id = user_id;
        this.course_name = course_name;
        this.grade = grade;
        this.review = review;
    }

    public ReviewDto(Integer id,int user_id, String course_name, int grade, String review) {
        this.id = id;
        this.user_id = user_id;
        this.course_name = course_name;
        this.grade = grade;
        this.review = review;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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
        return user_id == reviewDto.user_id
                && grade == reviewDto.grade
                && Objects.equals(course_name, reviewDto.course_name)
                && Objects.equals(review, reviewDto.review)
                && Objects.equals(id,reviewDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, course_name, grade, review);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", course_name='" + course_name + '\'' +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                '}';
    }
}
