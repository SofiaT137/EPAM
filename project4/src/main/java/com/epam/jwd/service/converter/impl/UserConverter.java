package com.epam.jwd.service.converter.impl;

import com.epam.jwd.DAO.impl.GroupDAO;
import com.epam.jwd.DAO.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.UserDto;

public class UserConverter implements Converter<User, UserDto,Integer> {

    GroupDAO groupDAO = new GroupDAO();

    @Override
    public User convert(UserDto userDto) {
        int groupId = groupDAO.filterGroup(userDto.getGroupName()).getId();
        return new User(userDto.getId(),userDto.getAccountId(),groupId,userDto.getFirstName(), userDto.getLastName());
    }

    @Override
    public UserDto convert(User user) {
        String groupName = groupDAO.findById(user.getGroup_id()).getName();
        return new UserDto(user.getId(),user.getAccount_id(),groupName,user.getGroup_id(),user.getFirst_name(),user.getLast_name());
    }
}
