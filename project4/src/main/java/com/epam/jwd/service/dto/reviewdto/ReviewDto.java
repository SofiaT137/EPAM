package com.epam.jwd.service.dto.reviewdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

/**
 * The ReviewDto class
 */
public class ReviewDto extends AbstractDto<Integer> {

    private String firstName;
    private String lastName;
    private String courseName;
    private int grade;
    private String review;

    public ReviewDto(String firstName,String lastName, String courseName, int grade, String review) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.grade = grade;
        this.review = review;
    }

    public ReviewDto(Integer id,String firstName,String lastName, String courseName, int grade, String review) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseName = courseName;
        this.grade = grade;
        this.review = review;
    }

    public ReviewDto(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return getGrade() == reviewDto.getGrade()
                && Objects.equals(getFirstName(), reviewDto.getFirstName())
                && Objects.equals(getLastName(), reviewDto.getLastName())
                && Objects.equals(getCourseName(), reviewDto.getCourseName())
                && Objects.equals(getReview(), reviewDto.getReview());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getFirstName(), getLastName(), getCourseName(), getGrade(), getReview());
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                '}';
    }
}
