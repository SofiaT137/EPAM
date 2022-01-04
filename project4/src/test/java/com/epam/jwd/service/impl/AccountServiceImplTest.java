package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.impl.AccountDaoImpl;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.service.dto.userdto.AccountDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    private final String login1 = "TestTest";
    private final String password1 = "TestTestTest93";
    private final Role role = Role.STUDENT;
    private final AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
    private final AccountDaoImpl accountDAOImpl = new AccountDaoImpl();
    private AccountDto accountDto1;


    @BeforeEach
    void setUp() {
        accountDto1 = new AccountDto();
        accountDto1.setRole(role.getName());
        accountDto1.setLogin(login1);
        accountDto1.setPassword(accountServiceImpl.encryptPassword(password1));
        accountDto1.setIsActive(1);
    }

    @AfterEach
    void tearDown(){
        accountServiceImpl.delete(accountDto1);
    }

    @Test
    void create() {
        accountDto1 = accountServiceImpl.create(accountDto1);
        AccountDto accountDto2 = accountServiceImpl.filterAccount(login1,password1);
        assertEquals(accountDto2.getLogin(),accountDto1.getLogin());
        assertEquals(accountDto2.getPassword(),accountDto1.getPassword());
    }

    @Test
    void update() {
        create();
        accountDto1.setIsActive(0);
        accountServiceImpl.update(accountDto1);
        assertEquals(accountDAOImpl.findAccountByLoginAndPassword(login1,password1).getIsActive(),accountDto1.getIsActive());
    }

    @Test
    void delete() {
        create();
        accountServiceImpl.delete(accountDto1);
        String msg_result;
        try{
            accountServiceImpl.filterAccount(login1,password1);
            msg_result = "I've found this account!";
        } catch (DAOException exception){
            msg_result = exception.getMessage();
        }
        assertEquals("I cannot find this account!", msg_result);
    }

    @Test
    void getById() {
        create();
        AccountDto accountDto2 = accountServiceImpl.getById(accountDto1.getId());
        assertEquals(accountDto2,accountDto1);
    }

    @Test
    void getAll() {
        List<AccountDto> accountDtoList = accountServiceImpl.findAll();
        int sizeBeforeAdd = accountDtoList.size();
        create();
        assertEquals(accountServiceImpl.findAll().size(),sizeBeforeAdd+1);
    }

    @Test
    void filterAccount() {
        create();
        AccountDto accountDto2 = accountServiceImpl.filterAccount(login1,password1);
        assertEquals(accountDto2,accountDto1);
    }

    @Test
    void getAccount() {
        create();
        AccountDto accountDto2 = accountServiceImpl.getAccount(login1);
        assertEquals(accountDto2,accountDto1);
    }
}