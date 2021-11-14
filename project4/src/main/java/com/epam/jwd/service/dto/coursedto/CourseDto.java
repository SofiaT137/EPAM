package com.epam.jwd.service.dto.coursedto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Date;
import java.util.Objects;

public class CourseDto extends AbstractDto<Integer> {

    private String name;
    private Date startCourse;
    private Date endCourse;

    public CourseDto(String name, Date startCourse, Date endCourse) {
        this.name = name;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
    }

    public CourseDto(Integer id,String name, Date startCourse, Date endCourse) {
        this.id = id;
        this.name = name;
        this.startCourse = startCourse;
        this.endCourse = endCourse;

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
        if (!(o instanceof CourseDto)) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(name, courseDto.getName())
                && Objects.equals(startCourse, courseDto.getStartCourse())
                && Objects.equals(endCourse, courseDto.getEndCourse())
                && Objects.equals(id,courseDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startCourse, endCourse,id);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startCourse=" + startCourse +
                ", endCourse=" + endCourse +
                '}';
    }
}
