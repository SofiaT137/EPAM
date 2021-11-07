package com.epam.jwd.DAO.model.user;

import com.epam.jwd.DAO.model.AbstractEntity;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {

    private int account_id;
    private int group_id;
    private String first_name;
    private String last_name;

    public User() {
    }

    public User(int account_id, int group_id, String first_name, String last_name) {
        this.account_id = account_id;
        this.group_id = group_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(Integer id, int account_id, int group_id, String first_name, String last_name) {
        super(id);
        this.account_id = account_id;
        this.group_id = group_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return account_id == user.getAccount_id()
                && group_id == user.getGroup_id()
                && Objects.equals(first_name, user.getFirst_name())
                && Objects.equals(last_name, user.getLast_name())
                && Objects.equals(id,user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, group_id, first_name, last_name);
    }
}
