package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class UserDto extends AbstractDto<Integer> {

    private int accountId;
    private String groupName;
    private String firstName;
    private String lastName;

    public UserDto(int accountId, String groupName, String firstName, String lastName) {
        this.accountId = accountId;
        this.groupName = groupName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(Integer id, int accountId, String groupName, int groupId, String firstName, String lastName)  {
        this.id = id;
        this.accountId = accountId;
        this.groupName = groupName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(accountId, userDto.getAccountId())
                && Objects.equals(groupName, userDto.getGroupName())
                && Objects.equals(firstName,userDto.getFirstName())
                && Objects.equals(lastName, userDto.getLastName())
                && Objects.equals(id,userDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, groupName, firstName,lastName);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", account_id=" + accountId +
                ", group_name=" + groupName +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                '}';
    }
}
