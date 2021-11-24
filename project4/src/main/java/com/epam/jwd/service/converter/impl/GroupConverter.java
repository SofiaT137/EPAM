package com.epam.jwd.service.converter.impl;

import com.epam.jwd.DAO.model.group.Group;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.groupdto.GroupDto;


public class GroupConverter implements Converter<Group, GroupDto,Integer> {

    @Override
    public Group convert(GroupDto value) {
        return new Group(value.getId(),value.getName());
    }

    @Override
    public GroupDto convert(Group value) {
        return new GroupDto(value.getId(),value.getName());
    }
}
