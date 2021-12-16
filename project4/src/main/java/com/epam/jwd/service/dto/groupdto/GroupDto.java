package com.epam.jwd.service.dto.groupdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;


/**
 * The GroupDto class
 */
public class GroupDto extends AbstractDto<Integer> {

    private String name;

    public GroupDto(String name) {
        this.name = name;
    }

    public GroupDto(Integer id,String name) {
        this.id = id;
        this.name = name;
    }

    public GroupDto(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDto)) return false;
        GroupDto groupDto = (GroupDto) o;
        return Objects.equals(name, groupDto.getName())
                && Objects.equals(id,groupDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id);
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
