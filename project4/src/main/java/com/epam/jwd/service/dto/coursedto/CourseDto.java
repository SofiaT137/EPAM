package com.epam.jwd.service.dto.coursedto;

import com.epam.jwd.service.dto.AbstractDto;

import java.sql.Date;
import java.util.Objects;

/**
 * The courseDto class
 */
public class CourseDto extends AbstractDto<Integer> {

    private String name;
    private Date startCourse;
    private Date endCourse;
    private String isTerminated;

    public CourseDto(String name, Date startCourse, Date endCourse,String isTerminated) {
        this.name = name;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
        this.isTerminated = isTerminated;
    }

    public CourseDto(Integer id,String name, Date startCourse, Date endCourse, String isTerminated) {
        this.id = id;
        this.name = name;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
        this.isTerminated = isTerminated;
    }

    public CourseDto(){

    }

    public String getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(String isTerminated) {
        this.isTerminated = isTerminated;
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
        return Objects.equals(getName(), courseDto.getName()) && Objects.equals(getStartCourse(), courseDto.getStartCourse()) && Objects.equals(getEndCourse(), courseDto.getEndCourse()) && Objects.equals(getIsTerminated(), courseDto.getIsTerminated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStartCourse(), getEndCourse(), getIsTerminated());
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startCourse=" + startCourse +
                ", endCourse=" + endCourse +
                ", isTerminated='" + isTerminated + '\'' +
                '}';
    }
}
