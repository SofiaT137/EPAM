package com.epam.jwd.controller.command;

import com.epam.jwd.Dao.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The command of creating teachers
 */
public class CreateTeacherCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(CreateTeacherCommand.class);

    private static final Command INSTANCE = new CreateTeacherCommand();

    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_CREATE_TEACHER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE = "allTeachers";

    private static final String NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION = "This account is not original for registration. Try again.";
    private static final String ORIGINAL_ACCOUNT_FOR_REGISTRATION = "This account is original.";
    private static final String TEACHER = "Teacher";

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


    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnAddTeacher = requestContext.getParameterFromJSP("btnAddTeacher");

        if (btnAddTeacher != null) {

            String login = requestContext.getParameterFromJSP("lblLogin");
            String password = requestContext.getParameterFromJSP("lblPassword");

            try{
                ((AccountService)accountService).validate(login,password);
            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            AccountDto accountDto = new AccountDto();

                try {
                    accountDto = ((AccountService) accountService).getAccount(login);
                    LOGGER.error(NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, "( " + login + " ) " + NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                    return ERROR_PAGE_CONTEXT;
                } catch (DAOException exception) {
                    LOGGER.error(ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                }

            password = ((AccountService) accountService).encryptPassword(password);

            try {
                accountDto.setRole(TEACHER);
                accountDto.setLogin(login);
                accountDto.setPassword(password);
                accountDto.setIsActive(1);

                accountDto = accountService.create(accountDto);

            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            try {

                String lblFirstName = requestContext.getParameterFromJSP("lblFirstName");
                String lblLastName = requestContext.getParameterFromJSP("lblLastName");

                UserDto userDto = new UserDto();
                userDto.setAccountId(accountDto.getId());
                userDto.setGroupName(TEACHER);
                userDto.setFirstName(lblFirstName);
                userDto.setLastName(lblLastName);

                userService.create(userDto);

                List<UserDto> allUser = userService.findAll();
                List<UserDto> allTeachers = findAlLUserTeachers(allUser);

                requestContext.addAttributeToSession(ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE, allTeachers);
                return REFRESH_PAGE_CONTEXT;

            } catch (Exception exception) {
                try {
                    accountService.delete(accountDto);
                } catch (Exception exception1) {
                    LOGGER.error(exception1.getMessage());
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception1.getMessage());
                    return ERROR_PAGE_CONTEXT;
                }
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    /**
     * Find all users with role "Teacher"
     * @param list list of UserDto
     * @return list of all teachers
     */
    private List<UserDto> findAlLUserTeachers(List<UserDto> list){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto
                : list) {
            int accountId= userDto.getAccountId();
            AccountDto accountDto;
            accountDto = accountService.getById(accountId);
            if (accountDto.getRole().equals(TEACHER)){
                result.add(userDto);
            }
        }
        return result;
    }
}
