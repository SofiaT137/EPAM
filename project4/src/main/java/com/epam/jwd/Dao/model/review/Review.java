package com.epam.jwd.Dao.model.review;

import com.epam.jwd.Dao.model.AbstractEntity;

import java.util.Objects;


public class Review extends AbstractEntity<Integer> {

    private int userId;
    private int courseId;
    private int grade;
    private String comment;

    public Review() {
    }

    public Review(Integer id, int userId, int courseId, int grade, String comment) {
        super(id);
        this.userId = userId;
        this.courseId = courseId;
        this.grade = grade;
        this.comment = comment;
    }

    public Review(int userId, int courseId, int grade, String comment) {
        this.userId = userId;
        this.courseId = courseId;
        this.grade = grade;
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review1 = (Review) o;
        return userId == review1.getUserId()
                && courseId == review1.getCourseId()
                && grade == review1.getGrade()
                && Objects.equals(comment, review1.getComment())
                && Objects.equals(id,review1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId, grade, comment,id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user_id=" + userId +
                ", course_id=" + courseId +
                ", grade=" + grade +
                ", review='" + comment + '\'' +
                '}';
    }
}
