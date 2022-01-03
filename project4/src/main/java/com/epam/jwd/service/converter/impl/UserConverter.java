package com.epam.jwd.service.converter.impl;

import com.epam.jwd.Dao.impl.GroupDao;
import com.epam.jwd.Dao.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.UserDto;

/**
 * Class for convert of user
 */
public class UserConverter implements Converter<User, UserDto,Integer> {

    GroupDao groupDAO = new GroupDao();

    @Override
    public User convert(UserDto userDto) {
        int groupId = groupDAO.filterGroup(userDto.getGroupName()).getId();
        return new User(userDto.getId(),userDto.getAccountId(),groupId,userDto.getFirstName(),userDto.getLastName());
    }

    @Override
    public UserDto convert(User user) {
        String groupName = groupDAO.findById(user.getGroupId()).getName();
        return new UserDto(user.getId(),user.getAccountId(),groupName,user.getGroupId(),user.getFirstName(),user.getLastName());
    }
}
