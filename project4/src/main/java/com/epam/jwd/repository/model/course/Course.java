package com.epam.jwd.repository.model.course;

import com.epam.jwd.repository.model.AbstractEntity;

import java.util.Date;
import java.util.Objects;

public class Course extends AbstractEntity<Integer> {

    private String name;
    private Date startCourse;
    private Date endCourse;

    public Course(Integer id, String name, Date startCourse, Date endCourse) {
        super(id);
        this.name = name;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
    }

    public Course(String name, Date startCourse, Date endCourse) {
        this.name = name;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
    }

    public Course(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartCourse() {
        return startCourse;
    }

    public void setStartCourse(Date startCourse) {
        this.startCourse = startCourse;
    }

    public Date getEndCourse() {
        return endCourse;
    }

    public void setEndCourse(Date endCourse) {
        this.endCourse = endCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.getName())
                && Objects.equals(startCourse, course.getStartCourse())
                && Objects.equals(endCourse, course.getEndCourse())
                && Objects.equals(id,course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startCourse, endCourse,id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startCourse=" + startCourse +
                ", endCourse=" + endCourse +
                '}';
    }
}
