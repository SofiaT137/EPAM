package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.UserService;

import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

public class CreateTeacherCommand implements Command {

    private static final Command INSTANCE = new CreateTeacherCommand();
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_CREATE_TEACHER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String ADMIN_PAGE_JSP = "/controller?command=SHOW_ADMIN_PAGE_COMMAND";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE = "allTeachers";
    private static final String EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION = "This account is not original for registration. Try again.";

    private final Service<AccountDto, Integer> accountService = new AccountService();
    private final Service<UserDto, Integer> userService = new UserService();

    public static Command getInstance() {
        return INSTANCE;
    }

    private CreateTeacherCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REFRESH_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext ERROR_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ERROR_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext ADMIN_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ADMIN_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };



    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnAddTeacher = requestContext.getParameterFromJSP("btnAddTeacher");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");
        final String role = "Teacher";

        if (btnAddTeacher != null) {
            String login = requestContext.getParameterFromJSP("lblLogin");
            String password = requestContext.getParameterFromJSP("lblPassword");

            AccountDto accountDto = new AccountDto();
            try {
                try {
                    accountDto = ((AccountService) accountService).checkOriginalAccount(login);
                    //log account is not original
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, "( " + login + " ) " + EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                    return ERROR_PAGE_CONTEXT;
                } catch (DAOException exception) {
                    //log Account is original
                }
                accountDto.setRole(role);
                accountDto.setLogin(login);
                accountDto.setPassword(password);

                accountDto = accountService.create(accountDto);
            } catch (Exception exception) {
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }
            try {
                String lblFirstName = new String(((requestContext.getParameterFromJSP("lblFirstName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
                String lblLastName = new String(((requestContext.getParameterFromJSP("lblLastName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
                final int group_id = 2;

                UserDto userDto = new UserDto();
                userDto.setAccount_id(accountDto.getId());
                userDto.setGroup_id(group_id);
                userDto.setFirst_name(lblFirstName);
                userDto.setLast_name(lblLastName);

                userDto = userService.create(userDto);

                List<UserDto> allUser = userService.getAll();
                List<UserDto> allTeachers = findAlLUserTeachers(allUser);

                requestContext.addAttributeToSession(ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE, allTeachers);
                return REFRESH_PAGE_CONTEXT;
            } catch (Exception exception) {
                try {
                    accountService.delete(accountDto);
                } catch (ServerException exception1) {
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception1.getMessage());
                    return ERROR_PAGE_CONTEXT;
                }
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }
        }else if (btnGetBack != null){
            return ADMIN_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<UserDto> findAlLUserTeachers(List<UserDto> list){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto : list) {
            int account_id = userDto.getAccount_id();
            AccountDto accountDto;
            accountDto = accountService.getById(account_id);
            if (accountDto.getRole().equals("Teacher")){
                result.add(userDto);
            }
        }
        return result;
    }
}
