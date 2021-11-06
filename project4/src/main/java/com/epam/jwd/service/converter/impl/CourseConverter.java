package com.epam.jwd.service.converter.impl;

import com.epam.jwd.repository.model.course.Course;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.coursedto.CourseDto;

public class CourseConverter implements Converter<Course, CourseDto,Integer> {
    @Override
    public Course convert(CourseDto courseDto) {
        return new Course(courseDto.getId(),courseDto.getName(),courseDto.getStartCourse(),courseDto.getEndCourse());
    }

    @Override
    public CourseDto convert(Course course) {
        return new CourseDto(course.getId(),course.getName(),course.getStartCourse(),course.getEndCourse());
    }
}
