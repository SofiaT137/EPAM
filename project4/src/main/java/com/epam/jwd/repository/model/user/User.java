package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.AbstractEntity;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {

    private int role_id;
    private int group_id;
    private String first_name;
    private String last_name;
    private String login;
    private String password;

    public User(Integer id, int role_id, int group_id, String first_name, String last_name, String login, String password) {
        super(id);
        this.role_id = role_id;
        this.group_id = group_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.login = login;
        this.password = password;
    }

    public User(int role_id, int group_id, String first_name, String last_name, String login, String password) {
        this.role_id = role_id;
        this.group_id = group_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return role_id == user.getRole_id()
                && group_id == user.getGroup_id()
                && Objects.equals(first_name, user.getFirst_name())
                && Objects.equals(last_name, user.getLast_name())
                && Objects.equals(login, user.getLogin())
                && Objects.equals(password, user.getPassword())
                && Objects.equals(id,user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,role_id, group_id, first_name, last_name, login, password);
    }
}
