package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.model.group.Group;
import com.epam.jwd.service.converter.Converter;
import com.epam.jwd.service.dto.groupdto.GroupDto;

/**
 * Class for convert of group
 */
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
