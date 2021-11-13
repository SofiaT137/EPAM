package com.epam.jwd.DAO.model.user;

import com.epam.jwd.DAO.model.AbstractEntity;

import java.util.Objects;

public class Account extends AbstractEntity<Integer> {

    private Role role;
    private String login;
    private String password;

    public Account() {
    }

    public Account(Integer id, int role_id, String login, String password) {
        super(id);
        this.role_id = role_id;
        this.login = login;
        this.password = password;
    }

    public Account(int role_id, String login, String password) {
        this.role_id = role_id;
        this.login = login;
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
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
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return role_id == account.getRole_id()
                && Objects.equals(login, account.getLogin())
                && Objects.equals(password, account.getPassword())
                && Objects.equals(id,account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_id, login, password,id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
