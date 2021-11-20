package com.epam.jwd.service.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.impl.CourseDAO;
import com.epam.jwd.DAO.model.course.Course;
import com.epam.jwd.DAO.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CourseConverter;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CourseValidator;
import com.epam.jwd.service.validator.impl.UserValidator;


import java.util.ArrayList;
import java.util.List;

public class CourseService implements Service<CourseDto,Integer> {

    private final DAO<Course,Integer> courseDAO = new CourseDAO();
    private final Validator<CourseDto> courseValidator = new CourseValidator();
    private final Validator<UserDto> userDtoValidator = new UserValidator();
    private final Converter<Course, CourseDto, Integer> courseConverter = new CourseConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String COURSE_NOT_FOUND_EXCEPTION = "This course is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any course.";
    private static final String CANNOT_FIND_COURSE_EXCEPTION = "I can't find this course by its name";
    private static final String CANNOT_FIND_USER_EXCEPTION = "I can't find any course for this user";


    @Override
    public CourseDto create(CourseDto value) throws ServiceException {
        courseValidator.validate(value);
        Course course = courseConverter.convert(value);
        courseDAO.save(course);
        return courseConverter.convert(course);
    }

    public List<CourseDto> getUserAvailableCourses(String first_name,String last_name){
        List<Course> courseList = ((CourseDAO) courseDAO).getUserAvailableCourses(first_name,last_name);
        List<CourseDto> courseDtoList = new ArrayList<>();
//        if (courseList.isEmpty()){
//            throw new ServiceException(CANNOT_FIND_USER_EXCEPTION);
//        }
        courseList.forEach(course -> courseDtoList.add(courseConverter.convert(course)));
        return courseDtoList;
    }

    public Boolean deleteUserFromCourse(CourseDto courseDto, UserDto userDto){
        courseValidator.validate(courseDto);
        userDtoValidator.validate(userDto);
       return ((CourseDAO)courseDAO).deleteUserFromCourse(courseDto.getName(),userDto.getFirst_name(),userDto.getLast_name());
    }

    public Boolean addUserIntoCourse(CourseDto courseDto, UserDto userDto) {
        courseValidator.validate(courseDto);
        userDtoValidator.validate(userDto);
        return ((CourseDAO)courseDAO).addUserIntoCourse(courseDto.getName(),userDto.getFirst_name(),userDto.getLast_name());
    }

    @Override
    public Boolean update(CourseDto value) throws ServiceException {
        courseValidator.validate(value);
        return courseDAO.update(courseConverter.convert(value));
    }

    @Override
    public Boolean delete(CourseDto value) throws ServiceException{
        courseValidator.validate(value);
        return courseDAO.delete(courseConverter.convert(value));
    }

    @Override
    public CourseDto getById(Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Course course = courseDAO.findById(id);
        if (course == null){
            throw new ServiceException(COURSE_NOT_FOUND_EXCEPTION);
        }
        return courseConverter.convert(course);
    }

    @Override
    public List<CourseDto> getAll() throws ServiceException {
        List<Course> daoGetAll = courseDAO.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoGetAll.forEach(course -> courseDtoList.add(courseConverter.convert(course)));
        return courseDtoList;
    }


    public List<CourseDto> filterCourse(String course_name){
        List<Course> daoGetAll = ((CourseDAO)courseDAO).filterCourse(course_name);
        List<CourseDto> courseDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException(CANNOT_FIND_COURSE_EXCEPTION);
        }
        daoGetAll.forEach(course -> courseDtoList.add(courseConverter.convert(course)));
        return courseDtoList;
    }

}
