package com.epam.jwd.repository.model.review;

import com.epam.jwd.repository.model.AbstractEntity;

import java.util.Objects;


public class Review extends AbstractEntity<Integer> {

    private int user_id;
    private int course_id;
    private int grade;
    private String review;

    public Review() {
    }

    public Review(Integer id, int user_id, int course_id, int grade, String review) {
        super(id);
        this.user_id = user_id;
        this.course_id = course_id;
        this.grade = grade;
        this.review = review;
    }

    public Review(int user_id, int course_id, int grade, String review) {
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
        if (!(o instanceof Review)) return false;
        Review review1 = (Review) o;
        return user_id == review1.getUser_id()
                && course_id == review1.getCourse_id()
                && grade == review1.getGrade()
                && Objects.equals(review, review1.getReview())
                && Objects.equals(id,review1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, course_id, grade, review,id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", course_id=" + course_id +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                '}';
    }
}
