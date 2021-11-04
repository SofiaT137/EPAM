package com.epam.jwd.service.dto.userdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Objects;

public class AccountDto extends AbstractDto<Integer> {

    private int role_id;
    private String login;
    private String password;


    public AccountDto(Integer id, int role_id, String login, String password) {
        this.id = id;
        this.role_id = role_id;
        this.login = login;
        this.password = password;
    }

    public AccountDto(int role_id, String login, String password) {
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
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto accountDTO = (AccountDto) o;
        return Objects.equals(role_id, accountDTO.getRole_id())
                && Objects.equals(login, accountDTO.getLogin())
                && Objects.equals(password, accountDTO.getPassword())
                && Objects.equals(id, accountDTO.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hash(role_id,login,password,id);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
