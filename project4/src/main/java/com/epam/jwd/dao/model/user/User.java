package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.AbstractEntity;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {

    private int accountId;
    private int groupId;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(int accountId, int groupId, String firstName, String lastName) {
        this.accountId = accountId;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Integer id,int accountId, int groupId, String firstName, String lastName) {
        super(id);
        this.accountId = accountId;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return accountId == user.getAccountId()
                && groupId == user.getGroupId()
                && Objects.equals(firstName, user.getFirstName())
                && Objects.equals(lastName, user.getLastName())
                && Objects.equals(id,user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, groupId, firstName, lastName);
    }
}
