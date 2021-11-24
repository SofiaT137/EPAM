package com.epam.jwd.service.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.impl.GroupDAO;
import com.epam.jwd.DAO.model.group.Group;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.GroupConverter;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.GroupValidator;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;


public class GroupService implements Service<GroupDto,Integer> {

    private final DAO<Group,Integer> groupDAO = new GroupDAO();
    private final Validator<GroupDto> groupValidator = new GroupValidator();
    private final Converter<Group, GroupDto, Integer> groupConverter = new GroupConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String GROUP_NOT_FOUND_EXCEPTION = "This group is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any group.";

    @Override
    public GroupDto create(GroupDto value) throws ServiceException {
        groupValidator.validate(value);
        Group group = groupConverter.convert(value);
        groupDAO.save(group);
        return groupConverter.convert(group);
    }

    @Override
    public Boolean update(GroupDto value) throws ServiceException, ServerException {
        groupValidator.validate(value);
        return groupDAO.update(groupConverter.convert(value));
    }

    @Override
    public Boolean delete(GroupDto value) throws ServiceException, ServerException {
        groupValidator.validate(value);
        return groupDAO.delete(groupConverter.convert(value));
    }

    @Override
    public GroupDto getById(Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Group group =  groupDAO.findById(id);
        if (group == null){
            throw new ServiceException(GROUP_NOT_FOUND_EXCEPTION);
        }
        return groupConverter.convert(group);
    }

    @Override
    public List<GroupDto> getAll() throws ServiceException {
        List<Group> groupGetAll = groupDAO.findAll();
        List<GroupDto> groupDtoList = new ArrayList<>();
        if (groupGetAll.isEmpty()){
            throw new ServiceException(REPOSITORY_IS_EMPTY_EXCEPTION);
        }
        groupGetAll.forEach(group -> groupDtoList.add(groupConverter.convert(group)));
        return groupDtoList;
    }

    public GroupDto filterGroup(String name){
        Group group = ((GroupDAO)groupDAO).filterGroup(name);
        return groupConverter.convert(group);
    }
}
