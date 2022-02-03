package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
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
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_CREATE_TEACHER_PAGE_COMMAND";

    private static final String ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE = "allTeachers";

    private static final String NOT_ORIGINAL_LOGIN_FOR_REGISTRATION = "notOriginalLogin";
    private static final String ORIGINAL_ACCOUNT_FOR_REGISTRATION = "This account is original.";
    private static final String TEACHER = "Teacher";

    private final Service<AccountDto, Integer> accountService = new AccountServiceImpl();
    private final Service<UserDto, Integer> userService = new UserServiceImpl();

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

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnAddTeacher = requestContext.getParameterFromJSP("btnAddTeacher");

        String login = requestContext.getParameterFromJSP("lblLogin");
        String password = requestContext.getParameterFromJSP("lblPassword");
        String firstName = requestContext.getParameterFromJSP("lblFirstName");
        String lastName = requestContext.getParameterFromJSP("lblLastName");

        if (btnAddTeacher != null) {

            AccountDto newAccount = new AccountDto();
            if (Boolean.TRUE.equals(checkOriginalAccount(login,requestContext))){
                newAccount = createAccount(login,password,requestContext);
            }
            if (newAccount.getLogin() != null){
                createUser(newAccount,firstName,lastName,requestContext);
            }

            List<UserDto> allUser = userService.findAll();
            List<UserDto> allTeachers = findAlLUserTeachers(allUser);

            requestContext.addAttributeToSession(ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE, allTeachers);
            return REFRESH_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }


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

    private Boolean checkOriginalAccount(String login, RequestContext requestContext){
        try {
            ((AccountServiceImpl) accountService).getAccount(login);
            LOGGER.error(NOT_ORIGINAL_LOGIN_FOR_REGISTRATION);
            ERROR_HANDLER.setError(NOT_ORIGINAL_LOGIN_FOR_REGISTRATION,requestContext);
            return false;
        } catch (DAOException exception) {
            LOGGER.error(ORIGINAL_ACCOUNT_FOR_REGISTRATION);
            return true;
        }
    }

    private AccountDto createAccount(String login,String password,RequestContext requestContext) {
        AccountDto newAccount = new AccountDto();
            password = ((AccountServiceImpl) accountService).encryptPassword(password);
            try {
                newAccount.setRole(TEACHER);
                newAccount.setLogin(login);
                newAccount.setPassword(password);
                newAccount.setIsActive(1);
                newAccount = accountService.create(newAccount);

            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
            }
            return newAccount;
    }


    private void createUser(AccountDto accountDto, String firstName,String lastName,RequestContext requestContext) {
        UserDto newUserDto = new UserDto();
        try {
            newUserDto.setAccountId(accountDto.getId());
            newUserDto.setGroupName(TEACHER);
            newUserDto.setFirstName(firstName);
            newUserDto.setLastName(lastName);

            userService.create(newUserDto);

        } catch (ServiceException exception) {
            try {
                accountService.delete(accountDto);
            } catch (Exception exception1) {
                LOGGER.error(exception1.getMessage());
                ERROR_HANDLER.setError(exception1.getMessage(),requestContext);
            }
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
        }
    }
}
