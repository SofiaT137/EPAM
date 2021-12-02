package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class UserDto extends AbstractDto<Integer> {

    private int account_id;
    private String group_name;
    private String first_name;
    private String last_name;

    public UserDto(int account_id, String group_name, String first_name, String last_name) {
        this.account_id = account_id;
        this.group_name = group_name;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public UserDto(Integer id, int account_id, String group_name, int group_id, String first_name, String last_name) {
        this.id = id;
        this.account_id = account_id;
        this.group_name = group_name;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public UserDto() {
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(account_id, userDto.getAccount_id())
                && Objects.equals(group_name, userDto.getGroup_name())
                && Objects.equals(first_name,userDto.getFirst_name())
                && Objects.equals(last_name, userDto.getLast_name())
                && Objects.equals(id,userDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, group_name, first_name,last_name);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", account_id=" + account_id +
                ", group_name=" + group_name +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
