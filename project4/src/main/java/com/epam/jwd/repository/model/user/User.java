package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.AbstractEntity;
import com.epam.jwd.repository.model.group.Group;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {

    private Account account;
    private Group group;
    private String firstname;
    private String lastName;

    public User(Integer id, Account account, Group group, String firstname, String lastName) {
        super(id);
        this.account = account;
        this.group = group;
        this.firstname = firstname;
        this.lastName = lastName;
    }

    public User(Account account, Group group, String firstname, String lastName) {
        this.account = account;
        this.group = group;
        this.firstname = firstname;
        this.lastName = lastName;
    }

    public User() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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
        return Objects.equals(account, user.getAccount())
                && Objects.equals(group, user.getGroup())
                && Objects.equals(firstname, user.getFirstname())
                && Objects.equals(lastName, user.getLastName())
                && Objects.equals(id,user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, group, firstname, lastName, id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account=" + account +
                ", group=" + group +
                ", firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
