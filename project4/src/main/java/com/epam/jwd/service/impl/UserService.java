package com.epam.jwd.service.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.impl.CourseDAO;
import com.epam.jwd.DAO.impl.UserDAO;
import com.epam.jwd.DAO.model.course.Course;
import com.epam.jwd.DAO.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;


public class UserService implements Service<UserDto,Integer> {

    private final DAO<Course,Integer> courseDAO = new CourseDAO();
    private final DAO<User,Integer> userDAO = new UserDAO();
    private final Validator<UserDto> userValidator = new UserValidator();
    private final Converter<User, UserDto, Integer> userConverter = new UserConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String USER_NOT_FOUND_EXCEPTION = "This user is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any user.";
    private static final String CANNOT_FIND_USER_EXCEPTION = "I can't find this user by its first name and last name";


    @Override
    public UserDto create(UserDto value) throws ServiceException {
        userValidator.validate(value);
        User user = userConverter.convert(value);
        userDAO.save(user);
        return userConverter.convert(user);
    }

    @Override
    public Boolean update(UserDto value) throws ServerException {
        userValidator.validate(value);
        return userDAO.update(userConverter.convert(value));
    }

    @Override
    public Boolean delete(UserDto value) throws ServerException {
        userValidator.validate(value);
        return userDAO.delete(userConverter.convert(value));
    }

    @Override
    public UserDto getById(Integer id) throws ServiceException {
        if (id == null) {
           throw new ServiceException(ID_IS_NULL_EXCEPTION);
       }
       User user = userDAO.findById(id);
       if (user == null){
           throw new ServiceException(USER_NOT_FOUND_EXCEPTION);
       }
       return userConverter.convert(user);
   }


    @Override
    public List<UserDto> getAll() throws ServiceException {
        List<User> daoGetAll = userDAO.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        daoGetAll.forEach(user -> userDtoList.add(userConverter.convert(user)));
        return userDtoList;
    }

    public List<UserDto> filterUser(String first_name,String last_name){
        List<User> daoGetAll = ((UserDAO)userDAO).filterUser(first_name,last_name);
        List<UserDto> userDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException(CANNOT_FIND_USER_EXCEPTION);
        }
        daoGetAll.forEach(user -> userDtoList.add(userConverter.convert(user)));
        return userDtoList;
    }

    public UserDto findUserByAccountId(int account_id){
        User user = ((UserDAO)userDAO).findUserByAccountId(account_id);
        return userConverter.convert(user);
    }

    public List<UserDto> findALLStudentOnThisCourse(String course_name){
        List<User> daoGetAllStudent = ((CourseDAO)courseDAO).getAllUserAtCourse(course_name);
        List<UserDto> dtoGetAllStudent = new ArrayList<>();
        daoGetAllStudent.forEach(student -> dtoGetAllStudent.add(userConverter.convert(student)));
        return dtoGetAllStudent;
    }
}
