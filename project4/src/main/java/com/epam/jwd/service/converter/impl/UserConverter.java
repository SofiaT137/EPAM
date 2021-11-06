package com.epam.jwd.service.converter.impl;

import com.epam.jwd.repository.model.user.User;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.userdto.UserDto;

public class UserConverter implements Converter<User, UserDto,Integer> {


    @Override
    public User convert(UserDto userDto) {
        return new User(userDto.getId(),userDto.getAccount_id(),userDto.getGroup_id(),userDto.getFirst_name(), userDto.getLast_name());
    }

    @Override
    public UserDto convert(User user) {
        return new UserDto(user.getId(),user.getAccount_id(),user.getGroup_id(),user.getFirst_name(),user.getLast_name());
    }
}
