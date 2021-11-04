package com.epam.jwd.service.conventer.impl;

import com.epam.jwd.repository.model.course.Course;
import com.epam.jwd.service.conventer.api.Conventer;
import com.epam.jwd.service.dto.coursedto.CourseDto;

public class CourseConventer implements Conventer<Course, CourseDto,Integer> {
    @Override
    public Course convert(CourseDto courseDto) {
        return new Course(courseDto.getId(),courseDto.getName(),courseDto.getStartCourse(),courseDto.getEndCourse());
    }

    @Override
    public CourseDto convert(Course course) {
        return new CourseDto(course.getId(),course.getName(),course.getStartCourse(),course.getEndCourse());
    }
}
