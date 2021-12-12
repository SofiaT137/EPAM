package com.epam.jwd.service.impl;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.DAO.impl.AccountDAO;
import com.epam.jwd.DAO.model.user.Role;
import com.epam.jwd.service.dto.userdto.AccountDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final String login1 = "TestTest";
    private final String password1 = "TestTestTest93";
    private final Role role = Role.STUDENT;
    private final AccountService accountService = new AccountService();
    private final AccountDAO accountDAO = new AccountDAO();
    private AccountDto accountDto1;


    @BeforeEach
    void setUp() {
        accountDto1 = new AccountDto();
        accountDto1.setRole(role.getName());
        accountDto1.setLogin(login1);
        accountDto1.setPassword(accountService.encryptPassword(password1));
        accountDto1.setIsActive(1);
    }

    @AfterEach
    void tearDown(){
        accountService.delete(accountDto1);
    }

    @Test
    void create() {
        accountDto1 = accountService.create(accountDto1);
        AccountDto accountDto2 = accountService.filterAccount(login1,password1);
        assertEquals(accountDto2.getLogin(),accountDto1.getLogin());
        assertEquals(accountDto2.getPassword(),accountDto1.getPassword());
    }

    @Test
    void update() {
        create();
        accountDto1.setIsActive(0);
        accountService.update(accountDto1);
        assertEquals(accountDAO.filterAccount(login1,password1).getIsActive(),accountDto1.getIsActive());
    }

    @Test
    void delete() {
        create();
        accountService.delete(accountDto1);
        String msg_result;
        try{
            accountService.filterAccount(login1,password1);
            msg_result = "I've found this account!";
        } catch (DAOException exception){
            msg_result = exception.getMessage();
        }
        assertEquals("I cannot find this account!", msg_result);
    }

    @Test
    void getById() {
        create();
        AccountDto accountDto2 = accountService.getById(accountDto1.getId());
        assertEquals(accountDto2,accountDto1);
    }

    @Test
    void getAll() {
        List<AccountDto> accountDtoList = accountService.getAll();
        int sizeBeforeAdd = accountDtoList.size();
        create();
        assertEquals(accountService.getAll().size(),sizeBeforeAdd+1);
    }

    @Test
    void filterAccount() {
        create();
        AccountDto accountDto2 = accountService.filterAccount(login1,password1);
        assertEquals(accountDto2,accountDto1);
    }

    @Test
    void getAccount() {
        create();
        AccountDto accountDto2 = accountService.getAccount(login1);
        assertEquals(accountDto2,accountDto1);
    }
}