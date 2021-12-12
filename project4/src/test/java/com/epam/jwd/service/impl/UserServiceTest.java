package com.epam.jwd.service.impl;

import com.epam.jwd.DAO.impl.AccountDAO;
import com.epam.jwd.DAO.model.group.Group;
import com.epam.jwd.DAO.model.user.Role;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final String firstName = "Тест";
    private final String lastName = "Тестович";
    private static String login1 = "TestTest";
    private static String password1 = "TestTestTest93";
    private final Role role = Role.STUDENT;
    private static final AccountService accountService = new AccountService();
    private final GroupService groupService = new GroupService();
    private final UserService userService= new UserService();
    private static AccountDto accountDto1 = new AccountDto();
    private UserDto userDto1 = new UserDto();

    void createAccountAndUser(){
        accountDto1.setRole(role.getName());
        accountDto1.setLogin(login1);
        accountDto1.setPassword(password1);
        accountDto1.setIsActive(1);
        accountDto1 = accountService.create(accountDto1);

        userDto1 = new UserDto();
        userDto1.setAccountId(accountDto1.getId());
        String groupName = "01033";
        userDto1.setGroupName(groupService.filterGroup(groupName).getName());
        userDto1.setFirstName(firstName);
        userDto1.setLastName(lastName);
    }

    @AfterAll
    static void deleteAccount(){
        AccountDto accountDto = accountService.getAccount(login1);
        accountDto.setIsActive(0);
        accountService.update(accountDto);
    }

    @Test
    void create() {
        createAccountAndUser();
        userDto1 = userService.create(userDto1);
        UserDto userDto2 = userService.filterUser(firstName,lastName);
        assertEquals(userDto2.getFirstName(),userDto1.getFirstName());
        assertEquals(userDto2.getLastName(),userDto1.getLastName());
    }

    @Test
    void update() {
        UserDto userDto = userService.filterUser(firstName,lastName);
        String newFirstName = "Test";
        userDto.setFirstName(newFirstName);
        userService.update(userDto);
        assertEquals(userDto.getFirstName(),newFirstName);
    }

//    @Test
//    void getById() {
//    }
//
//    @Test
//    void findUserByAccountId() {
//    }
//
//    @Test
//    void findALLStudentOnThisCourse() {
//    }
}