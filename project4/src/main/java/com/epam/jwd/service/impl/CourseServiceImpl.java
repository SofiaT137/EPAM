package com.epam.jwd.service.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.impl.CourseDaoImpl;
import com.epam.jwd.dao.model.course.Course;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.converter.impl.CourseConverter;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.impl.CourseValidator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * The course service
 */

public class CourseServiceImpl implements Service<CourseDto,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(CourseServiceImpl.class);

    private final Dao<Course,Integer> courseDao = new CourseDaoImpl();
    private final Validator<CourseDto> courseValidator = new CourseValidator();
    private final Validator<UserDto> userDtoValidator = new UserValidator();
    private final Converter<Course, CourseDto, Integer> courseConverter = new CourseConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String COURSE_NOT_FOUND_EXCEPTION = "This course is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any course.";
//    private static final String CANNOT_FIND_COURSE_EXCEPTION = "I can't find this course by its name";
    private static final String CANNOT_FIND_USER_EXCEPTION = "I can't find any course for this user";


    @Override
    public CourseDto create(CourseDto value) throws ServiceException {
        courseValidator.validate(value);
        Course course = courseConverter.convert(value);
        courseDao.save(course);
        return courseConverter.convert(course);
    }

    /**
     * Get user available courses
     * @param firstName user's first name
     * @param lastName user's last name
     * @return list of CourseDto
     */
    public List<CourseDto> getUserAvailableCourses(String firstName,String lastName){
        List<Course> courseList = ((CourseDaoImpl) courseDao).getUserAvailableCourses(firstName,lastName);
        List<CourseDto> courseDtoList = new ArrayList<>();
        if (courseList.isEmpty()){
            LOGGER.error(CANNOT_FIND_USER_EXCEPTION);
            throw new ServiceException(CANNOT_FIND_USER_EXCEPTION);
        }
        courseList.forEach(course -> courseDtoList.add(courseConverter.convert(course)));
        return courseDtoList;
    }

    /**
     * Delete user from course
     * @param courseDto Course Dto
     * @param userDto User Dto
     * @return Boolean result
     */
    public Boolean deleteUserFromCourse(CourseDto courseDto, UserDto userDto){
        courseValidator.validate(courseDto);
        userDtoValidator.validate(userDto);
       return ((CourseDaoImpl) courseDao).deleteUserFromCourse(courseDto.getName(),userDto.getFirstName(),userDto.getLastName());
    }

    /**  Add user into course
     * @param courseDto Course Dto
     * @param userDto User Dto
     * @return Boolean result
     */
    public Boolean addUserIntoCourse(CourseDto courseDto, UserDto userDto) {
        courseValidator.validate(courseDto);
        userDtoValidator.validate(userDto);
        return ((CourseDaoImpl) courseDao).addUserIntoCourse(courseDto.getName(),userDto.getFirstName(),userDto.getLastName());
    }

    @Override
    public Boolean update(CourseDto value) throws ServiceException {
        courseValidator.validate(value);
        return courseDao.update(courseConverter.convert(value));
    }

    @Override
    public Boolean delete(CourseDto value) throws ServiceException{
        courseValidator.validate(value);
        return courseDao.delete(courseConverter.convert(value));
    }

    @Override
    public CourseDto getById(Integer id) throws ServiceException {
        if (id == null) {
            LOGGER.error(ID_IS_NULL_EXCEPTION);
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Course course = courseDao.findById(id);
        if (course == null){
            LOGGER.error(COURSE_NOT_FOUND_EXCEPTION);
            throw new ServiceException(COURSE_NOT_FOUND_EXCEPTION);
        }
        return courseConverter.convert(course);
    }

    @Override
    public List<CourseDto> findAll() throws ServiceException {
        List<Course> daoGetAll = courseDao.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            LOGGER.error(REPOSITORY_IS_EMPTY_EXCEPTION);
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoGetAll.forEach(course -> courseDtoList.add(courseConverter.convert(course)));
        return courseDtoList;
    }


    /**
     * Find course by name
     * @param courseName
     * @return list of CourseDto
     */
    public List<CourseDto> filterCourse(String courseName){
        List<Course> daoGetAll = ((CourseDaoImpl) courseDao).findCourseByName(courseName);
        List<CourseDto> courseDtoList = new ArrayList<>();
        daoGetAll.forEach(course -> courseDtoList.add(courseConverter.convert(course)));
        return courseDtoList;
    }

    /**
     * Delete all courses in user has course by course id
     * @param courseId Course ID
     * @return Boolean result
     */
    public Boolean deleteAllCourseInUSERHAsCourse(int courseId){
        return ((CourseDaoImpl) courseDao).deleteAllFieldsUserHasCourseByCourseId(courseId);
    }

}
