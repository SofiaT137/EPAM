package com.epam.jwd.service.impl;

import com.epam.jwd.dao.Dao;
import com.epam.jwd.dao.impl.GroupDaoImpl;
import com.epam.jwd.dao.model.group.Group;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.converter.impl.GroupConverter;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import com.epam.jwd.service.validator.impl.GroupValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The group service
 */
public class GroupServiceImpl implements Service<GroupDto,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(GroupServiceImpl.class);

    private final Dao<Group,Integer> groupDao = new GroupDaoImpl();
    private final Validator<GroupDto> groupValidator = new GroupValidator();
    private final Converter<Group, GroupDto, Integer> groupConverter = new GroupConverter();

    private static final String ID_IS_NULL_EXCEPTION = "This id is null";
    private static final String GROUP_NOT_FOUND_EXCEPTION = "This group is not found!";
    private static final String REPOSITORY_IS_EMPTY_EXCEPTION = "Repository is empty. I can't find any group.";

    @Override
    public GroupDto create(GroupDto value) throws ServiceException {
        groupValidator.validate(value);
        Group group = groupConverter.convert(value);
        groupDao.save(group);
        return groupConverter.convert(group);
    }

    @Override
    public Boolean update(GroupDto value) throws ServiceException {
        groupValidator.validate(value);
        return groupDao.update(groupConverter.convert(value));
    }

    @Override
    public Boolean delete(GroupDto value) throws ServiceException{
        groupValidator.validate(value);
        return groupDao.delete(groupConverter.convert(value));
    }

    @Override
    public GroupDto getById(Integer id) throws ServiceException {
        if (id == null) {
            LOGGER.error(ID_IS_NULL_EXCEPTION);
            throw new ServiceException(ID_IS_NULL_EXCEPTION);
        }
        Group group =  groupDao.findById(id);
        if (group == null){
            LOGGER.error(GROUP_NOT_FOUND_EXCEPTION);
            throw new ServiceException(GROUP_NOT_FOUND_EXCEPTION);
        }
        return groupConverter.convert(group);
    }

    @Override
    public List<GroupDto> findAll() throws ServiceException {
        List<Group> groupGetAll = groupDao.findAll();
        List<GroupDto> groupDtoList = new ArrayList<>();
        if (!(groupGetAll.isEmpty())){
            groupGetAll.forEach(group -> groupDtoList.add(groupConverter.convert(group)));
        }
        return groupDtoList;
    }

    /**
     * Find group by name
     * @param name group name
     * @return GroupDto object
     */
    public GroupDto filterGroup(String name){
        Group group = ((GroupDaoImpl) groupDao).findGroupByName(name);
        return groupConverter.convert(group);
    }
}
