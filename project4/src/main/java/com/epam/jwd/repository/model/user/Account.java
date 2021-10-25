package com.epam.jwd.repository.model.user;

import com.epam.jwd.repository.model.AbstractEntity;

import java.util.Objects;

public class Account extends AbstractEntity<Integer> {

    private String login;
    private String password;
    private Integer role;

    public Account(){

    }

    public Account(String login, String password, Integer role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Account(int id,String login, String password, Integer role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(login, account.getLogin())
                && Objects.equals(password, account.getPassword())
                && Objects.equals(role, account.getRole())
                && Objects.equals(id,account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,login,password,role);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
