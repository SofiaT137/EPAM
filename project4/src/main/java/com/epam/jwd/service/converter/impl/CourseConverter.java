package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.course.Course;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Class for convert of course
 */

public class CourseConverter implements Converter<Course, CourseDto,Integer> {
    @Override
    public Course convert(CourseDto courseDto) {
        return new Course(courseDto.getId(),courseDto.getName(),courseDto.getStartCourse(),courseDto.getEndCourse());
    }

    @Override
    public CourseDto convert(Course course) {
        Date tomorrow = Date.valueOf(LocalDate.now().plusDays(1));
        String isTerminated = course.getEndCourse().before(tomorrow) ? "Yes":"No";
        return new CourseDto(course.getId(),course.getName(),course.getStartCourse(),course.getEndCourse(),isTerminated);
    }
}
