package com.epam.jwd.service.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.UserDao;
import com.epam.jwd.dao.impl.CourseDaoImpl;
import com.epam.jwd.dao.impl.UserDaoImpl;
import com.epam.jwd.dao.model.course.Course;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The user service
 */
public class UserServiceImpl implements Service<UserDto,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final Dao<Course,Integer> courseDao = new CourseDaoImpl();
    private final UserDao<User,Integer> userDaoImpl = new UserDaoImpl();
    private final Validator<UserDto> userValidator = new UserValidator();
    private final Converter<User, UserDto, Integer> userConverter = new UserConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String USER_NOT_FOUND_EXCEPTION = "This user is not found!";


    @Override
    public UserDto create(UserDto value) throws ServiceException {
        userValidator.validate(value);
        User user = userConverter.convert(value);
        userDaoImpl.save(user);
        return userConverter.convert(user);
    }

    @Override
    public Boolean update(UserDto value) throws ServiceException {
        userValidator.validate(value);
        return userDaoImpl.update(userConverter.convert(value));
    }

    @Override
    public Boolean delete(UserDto value) throws ServiceException {
        return false;
    }

    @Override
    public UserDto getById(Integer id) throws ServiceException {
        if (id == null) {
            LOGGER.error(ID_IS_NULL_EXCEPTION);
           throw new ServiceException(ID_IS_NULL_EXCEPTION);
       }
       User user = userDaoImpl.findById(id);
       if (user == null){
           LOGGER.error(USER_NOT_FOUND_EXCEPTION);
           throw new ServiceException(USER_NOT_FOUND_EXCEPTION);
       }
       return userConverter.convert(user);
   }


    @Override
    public List<UserDto> findAll(){
        List<User> daoGetAll = userDaoImpl.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        if (!(daoGetAll.isEmpty())){
            daoGetAll.forEach(user -> userDtoList.add(userConverter.convert(user)));
        }
        return userDtoList;
    }

    /**
     * Find user by firstName and lastName
     * @param firstName user's firstName
     * @param lastName user's lastName
     * @return UserDto object
     */
    public List<UserDto> filterUser(String firstName,String lastName){
        List<User> daoGetAll = ((UserDaoImpl) userDaoImpl).findUserByFirstNameAndLastName(firstName,lastName);
        List<UserDto> userDtoList = new ArrayList<>();
        if (!(daoGetAll.isEmpty())){
            daoGetAll.forEach(user -> userDtoList.add(userConverter.convert(user)));
        }
        return userDtoList;
    }

    /**
     * Find user by accountId
     * @param accountId User account's ID
     * @return UserDto object
     */
    public UserDto findUserByAccountId(int accountId){
        User user = ((UserDaoImpl) userDaoImpl).findUserByAccountId(accountId);
        return userConverter.convert(user);
    }

    /**
     * Find all students on course
     * @param courseName name of the course
     * @return List of UserDto objects
     */
    public List<UserDto> findALLStudentOnThisCourse(String courseName){
        List<User> daoGetAllStudent = ((CourseDaoImpl) courseDao).getAllUserAtCourse(courseName);
        List<UserDto> dtoGetAllStudent = new ArrayList<>();
        daoGetAllStudent.forEach(student -> dtoGetAllStudent.add(userConverter.convert(student)));
        return dtoGetAllStudent;
    }
}
