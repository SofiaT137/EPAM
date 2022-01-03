package com.epam.jwd.controller.command;

import com.epam.jwd.Dao.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.GroupService;
import com.epam.jwd.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The command of selection: registration or log in
 */

public class SelectRegistrationOrLogInCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SelectRegistrationOrLogInCommand.class);

    private static final Command INSTANCE = new SelectRegistrationOrLogInCommand();

    private static final String REGISTER_STUDENT_JSP = "/controller?command=SHOW_REGISTER_PAGE_COMMAND";
    private static final String STUDENT_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String TEACHER_PAGE_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
    private static final String ADMIN_PAGE_COMMAND = "/controller?command=SHOW_ADMIN_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private static final String REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE = "registerAccount";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";
    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "allGroups";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final String EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION = "This account is not original for registration. Try again.";
    private static final String ACCESS_DENIED ="Access denied. Please,contact administrator!";
    private static final String ACCOUNT_IS_ORIGINAL = "This account name is original :) !";

    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_TEACHER = "Teacher";
    private static final String ROLE_STUDENT = "Student";

    private final Service<AccountDto, Integer> service = new AccountService();
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private final Service<GroupDto, Integer> groupService = new GroupService();

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


    public static Command getInstance() {
        return INSTANCE;
    }

    private SelectRegistrationOrLogInCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String login = requestContext.getParameterFromJSP("lblLogin");
        String password = requestContext.getParameterFromJSP("lblPassword");
        try{
            ((AccountService)service).validate(login,password);
        }catch (Exception exception){
            LOGGER.error(exception.getMessage());
            requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,exception.getMessage());
            return ERROR_PAGE_CONTEXT;
        }
        String btnRegister = requestContext.getParameterFromJSP("btnRegister");
        String btnLogIn = requestContext.getParameterFromJSP("btnLogIn");

        List<GroupDto> listOfGroups;
        listOfGroups = groupService.getAll();
        GroupDto adminGroup = ((GroupService)groupService).filterGroup(ROLE_ADMIN);
        GroupDto teacherGroup = ((GroupService)groupService).filterGroup(ROLE_TEACHER);
        listOfGroups.remove(adminGroup);
        listOfGroups.remove(teacherGroup);
        requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,listOfGroups);

        String language = (String) requestContext.getAttributeFromSession(CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE);

        if (language == null){
            requestContext.addAttributeToSession(CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE, "en");
        }

        if (btnRegister != null) {
            try{
                ((AccountService) service).getAccount(login);
                LOGGER.error(EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,"( " + login + " ) " + EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                return ERROR_PAGE_CONTEXT;
            }catch (DAOException exception) {
                LOGGER.error(ACCOUNT_IS_ORIGINAL);
            }

            password = ((AccountService) service).encryptPassword(password);

            AccountDto accountDto = new AccountDto();
            accountDto.setRole(ROLE_STUDENT);
            accountDto.setLogin(login);
            accountDto.setPassword(password);
            accountDto.setIsActive(1);

            try{
                accountDto = service.create(accountDto);
            }catch (ServiceException exception){
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,exception.getMessage());
            }
            requestContext.addAttributeToSession(REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE, accountDto);
            return REGISTER_USER_CONTEXT;

        } else if (btnLogIn != null) {
            AccountDto accountDto;
            try{
                accountDto = ((AccountService) service).filterAccount(login,password);
                requestContext.addAttributeToSession(REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE, accountDto);
            }catch (DAOException exception){
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }
            if (accountDto.getIsActive() == 0){
                LOGGER.error(ACCESS_DENIED);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,ACCESS_DENIED);
                return ERROR_PAGE_CONTEXT;
            }
            UserDto userDto = ((UserService) serviceUser).findUserByAccountId(accountDto.getId());
            requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, userDto);
            String userRole = accountDto.getRole();
            if (Objects.equals(userRole, ROLE_TEACHER) || Objects.equals(userRole, ROLE_STUDENT)) {
                List<CourseDto> userCourses = new ArrayList<>();
                try{
                    userCourses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirstName(), userDto.getLastName());
                }catch (ServiceException exception){
                    LOGGER.info(exception.getMessage());
                }
                requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourses);
            }
            if (userRole.equals(ROLE_ADMIN)){
                return ADMIN_PAGE_CONTEXT;
            }else if (userRole.equals(ROLE_TEACHER)){
                return TEACHER_PAGE_CONTEXT;
            }
            return STUDENT_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

