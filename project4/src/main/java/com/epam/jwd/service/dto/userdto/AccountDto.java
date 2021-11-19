package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class AccountDto extends AbstractDto<Integer> {

    private String role;
    private String login;
    private String password;


    public AccountDto(Integer id, String roleName, String login, String password) {
        this.id = id;
        this.role = roleName;
        this.login = login;
        this.password = password;
    }

    public AccountDto(){

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDto)) return false;
        AccountDto accountDto = (AccountDto) o;
        return Objects.equals(role, accountDto.getRole())
                && Objects.equals(login, accountDto.getLogin())
                && Objects.equals(password, accountDto.getPassword())
                && Objects.equals(id,accountDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, password);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
