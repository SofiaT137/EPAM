package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.GroupServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * The command of selection: registration or log in
 */

public class SelectRegistrationOrLogInCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SelectRegistrationOrLogInCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new SelectRegistrationOrLogInCommand();

    private static final String REGISTER_STUDENT_JSP = "/controller?command=SHOW_REGISTER_PAGE_COMMAND";
    private static final String STUDENT_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String TEACHER_PAGE_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
    private static final String ADMIN_PAGE_COMMAND = "/controller?command=SHOW_ADMIN_PAGE_COMMAND";
    private static final String DEFAULT_COMMAND = "/controller?command=DEFAULT";

    private static final String REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE = "registerAccount";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";
    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "allGroups";

    private static final String EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION = "notOriginalAccount";
    private static final String ACCESS_DENIED ="accessDenied";
    private static final String ACCOUNT_IS_ORIGINAL = "This account name is original :) !";
    private static final String CANNOT_FIND_THIS_ACCOUNT = "cannotFindThisAccount";


    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_TEACHER = "Teacher";
    private static final String ROLE_STUDENT = "Student";
    private static final String LABEL_LOGIN = "lblLogin";
    private static final String LABEL_PASSWORD = "lblPassword";
    private static final String LOG_IN_BUTTON = "btnLogIn";
    private static final String REGISTER_BUTTON = "btnRegister";
    private static final String ENGLISH = "en";

    private final Service<AccountDto, Integer> serviceAccount = new AccountServiceImpl();
    private final Service<UserDto, Integer> serviceUser = new UserServiceImpl();
    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();
    private final Service<GroupDto, Integer> groupService = new GroupServiceImpl();

    private static final String CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE = "language";


    private static final ResponseContext REGISTER_USER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REGISTER_STUDENT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext STUDENT_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return STUDENT_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext TEACHER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext ADMIN_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ADMIN_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DEFAULT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private SelectRegistrationOrLogInCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String login = requestContext.getParameterFromJSP(LABEL_LOGIN);
        String password = requestContext.getParameterFromJSP(LABEL_PASSWORD);
        String btnRegister = requestContext.getParameterFromJSP(REGISTER_BUTTON);
        String btnLogIn = requestContext.getParameterFromJSP(LOG_IN_BUTTON);

        List<GroupDto> listOfGroups;
        listOfGroups = groupService.findAll();
        GroupDto adminGroup = ((GroupServiceImpl)groupService).filterGroup(ROLE_ADMIN);
        GroupDto teacherGroup = ((GroupServiceImpl)groupService).filterGroup(ROLE_TEACHER);
        listOfGroups.remove(adminGroup);
        listOfGroups.remove(teacherGroup);
        requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,listOfGroups);

        String language = (String) requestContext.getAttributeFromSession(CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE);

        if (language == null){
            requestContext.addAttributeToSession(CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE,ENGLISH);
        }

        if (btnRegister != null) {

            try{
                validateAccount(login,password);
            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            try{
                findAccountByLogin(login);
                LOGGER.error(EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                ERROR_HANDLER.setError(EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }catch (DAOException exception) {
                LOGGER.info(ACCOUNT_IS_ORIGINAL);
            }
            password = getEncryptedPassword(password) ;

            AccountDto accountDto;

            try{
                accountDto = createAccount(login,password);
            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            requestContext.addAttributeToSession(REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE, accountDto);
            return REGISTER_USER_CONTEXT;

        }
        if (btnLogIn != null) {

            AccountDto accountDto;

            try{
                accountDto = filterAccount(login,password);
                requestContext.addAttributeToSession(REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE, accountDto);
            }catch (DAOException exception) {
                LOGGER.error(CANNOT_FIND_THIS_ACCOUNT);
                ERROR_HANDLER.setError(CANNOT_FIND_THIS_ACCOUNT,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            if (Boolean.FALSE.equals(isAccessAllowed(accountDto))){
                LOGGER.error(ACCESS_DENIED);
                ERROR_HANDLER.setError(ACCESS_DENIED,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            UserDto userDto = findUserByAccountId(accountDto);
            requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, userDto);

            String userRole = accountDto.getRole();
            if (Objects.equals(userRole, ROLE_TEACHER) || Objects.equals(userRole, ROLE_STUDENT)) {
                List<CourseDto> userCourses;

             userCourses = getUserAvailableCourses(userDto);

             requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourses);
            }
            if (userRole.equals(ROLE_ADMIN)){
                return ADMIN_PAGE_CONTEXT;
            }else if (userRole.equals(ROLE_TEACHER)){
                return TEACHER_PAGE_CONTEXT;
            }
            return STUDENT_PAGE_CONTEXT;
        }
        return REFRESH_PAGE_CONTEXT;
    }

    private void validateAccount(String login,String password){
       ((AccountServiceImpl) serviceAccount).validate(login,password);
    }

    private AccountDto filterAccount(String login,String password){
        return ((AccountServiceImpl) serviceAccount).filterAccount(login,password);
    }

    private String getEncryptedPassword(String password){
        return ((AccountServiceImpl) serviceAccount).encryptPassword(password);
    }

    private AccountDto createAccount(String login,String password){
        AccountDto accountDto = new AccountDto();
        accountDto.setRole(ROLE_STUDENT);
        accountDto.setLogin(login);
        accountDto.setPassword(password);
        accountDto.setIsActive(1);
        accountDto = serviceAccount.create(accountDto);
        return accountDto;
    }

    private Boolean isAccessAllowed(AccountDto accountDto){
       return accountDto.getIsActive() == 1;
    }

    private UserDto findUserByAccountId(AccountDto accountDto){
        return ((UserServiceImpl) serviceUser).findUserByAccountId(accountDto.getId());
    }

    private List<CourseDto> getUserAvailableCourses(UserDto userDto){
       return  ((CourseServiceImpl) courseService).getUserAvailableCourses(userDto.getFirstName(), userDto.getLastName());
    }

    private void findAccountByLogin(String login){
        ((AccountServiceImpl) serviceAccount).getAccount(login);
    }

}

