package com.epam.jwd.service.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.impl.UserDAO;
import com.epam.jwd.repository.model.user.User;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.UserConverter;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.UserValidator;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;


public class UserService implements Service<UserDto,Integer> {

    private final DAO<User,Integer> userDAO = new UserDAO();
    private final Validator<UserDto> userValidator = new UserValidator();
    private final Converter<User, UserDto, Integer> userConverter = new UserConverter();


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
           throw new ServiceException("This id is null");
       }
       User user = userDAO.findById(id);
       if (user == null){
           throw new ServiceException("This user is not found!");
       }
       return userConverter.convert(user);
   }


    @Override
    public List<UserDto> getAll() throws ServiceException {
        List<User> daoGetAll = userDAO.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException("Repository is empty. I can't find any user.");
        }
        daoGetAll.forEach(user -> userDtoList.add(userConverter.convert(user)));
        return userDtoList;
    }

    public List<UserDto> filterUser(String first_name,String last_name){
        List<User> daoGetAll = ((UserDAO)userDAO).filterUser(first_name,last_name);
        List<UserDto> userDtoList = new ArrayList<>();
        if (daoGetAll.isEmpty()){
            throw new ServiceException("I can't find this user by his first name and last name");
        }
        daoGetAll.forEach(user -> userDtoList.add(userConverter.convert(user)));
        return userDtoList;
    }
}
