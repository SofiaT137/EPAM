package com.epam.jwd.service.converter.impl;

import com.epam.jwd.DAO.impl.GroupDAO;
import com.epam.jwd.DAO.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.UserDto;

public class UserConverter implements Converter<User, UserDto,Integer> {

    GroupDAO groupDAO = new GroupDAO();

    @Override
    public User convert(UserDto userDto) {
        int group_id = groupDAO.filterGroup(userDto.getGroup_name()).getId();
        return new User(userDto.getId(),userDto.getAccount_id(),group_id,userDto.getFirst_name(), userDto.getLast_name());
    }

    @Override
    public UserDto convert(User user) {
        String group_name = groupDAO.findById(user.getGroup_id()).getName();
        return new UserDto(user.getId(),user.getAccount_id(),group_name,user.getGroup_id(),user.getFirst_name(),user.getLast_name());
    }
}
