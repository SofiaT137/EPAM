package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.AbstractEntity;

import java.util.Objects;

public class Account extends AbstractEntity<Integer> {

    private Role role;
    private String login;
    private String password;
    private int isActive;

    public Account() {
    }

    public Account(Integer id, Role role, String login, String password, int isActive) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.password = password;
        this.isActive = isActive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getRole() == account.getRole()
                && Objects.equals(login, account.login)
                && Objects.equals(password, account.password)
                && Objects.equals(id,account.id)
                && Objects.equals(isActive,account.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,role,login,password,isActive);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
