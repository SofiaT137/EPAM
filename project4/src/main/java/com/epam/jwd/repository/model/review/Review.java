package com.epam.jwd.repository.model.review;

import com.epam.jwd.repository.model.AbstractEntity;
import com.epam.jwd.repository.model.user.User;
import com.epam.jwd.repository.model.course.Course;

import java.util.Objects;

public class Review extends AbstractEntity<Integer> {

    private User user;
    private Course course;
    private int grade;
    private String review;

    public Review(User user, Course course, int grade, String review) {
        this.user = user;
        this.course = course;
        this.grade = grade;
        this.review = review;
    }

    public Review(Integer id, User user, Course course, int grade, String review) {
        super(id);
        this.user = user;
        this.course = course;
        this.grade = grade;
        this.review = review;
    }

    public Review(User user, Course course, int grade) {
        this.user = user;
        this.course = course;
        this.grade = grade;
    }

    public Review() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        Review review = (Review) o;
        return getGrade() == review.getGrade()
                && Objects.equals(user, review.getUser())
                && Objects.equals(course, review.getCourse())
                && Objects.equals(id,review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course, grade, id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", course=" + course +
                ", grade=" + grade +
                '}';
    }
}
