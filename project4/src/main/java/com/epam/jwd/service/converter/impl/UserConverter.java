package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.impl.GroupDaoImpl;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.dto.userdto.UserDto;

/**
 * Class for convert of user
 */
public class UserConverter implements Converter<User, UserDto,Integer> {

    GroupDaoImpl groupDAOImpl = new GroupDaoImpl();

    @Override
    public User convert(UserDto userDto) {
        int groupId = groupDAOImpl.findGroupByName(userDto.getGroupName()).getId();
        return new User(userDto.getId(),userDto.getAccountId(),groupId,userDto.getFirstName(),userDto.getLastName());
    }

    @Override
    public UserDto convert(User user) {
        String groupName = groupDAOImpl.findById(user.getGroupId()).getName();
        return new UserDto(user.getId(),user.getAccountId(),groupName,user.getGroupId(),user.getFirstName(),user.getLastName());
    }
}
