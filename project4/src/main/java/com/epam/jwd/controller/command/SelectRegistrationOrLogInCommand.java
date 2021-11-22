package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.UserService;

import java.util.List;
import java.util.Objects;

public class SelectRegistrationOrLogInCommand implements Command {

    private static final Command INSTANCE = new SelectRegistrationOrLogInCommand();
    private static final String REGISTER_STUDENT_JSP = "/controller?command=SHOW_REGISTER_PAGE_COMMAND";
    private static final String STUDENT_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String TEACHER_PAGE_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
    private static final String ADMIN_PAGE_COMMAND = "/controller?command=SHOW_ADMIN_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE = "registerAccount";
    private static final String USER_PAGE_SESSION_COLLECTION_ATTRIBUTE = "getUserPage";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION = "This account is not original for registration. Try again.";
    private final Service<AccountDto, Integer> service = new AccountService();
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> courseService = new CourseService();


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
        String btnRegister = requestContext.getParameterFromJSP("btnRegister");
        String btnLogIn = requestContext.getParameterFromJSP("btnLogIn");
        String role = "Student";

        List<AccountDto> accountDtoList = ((AccountService) service).filterAccount(login,password);

        AccountDto checkAccount;

        AccountDto accountDto;

        if (btnRegister != null) {
            try{
                checkAccount = ((AccountService) service).checkOriginalAccount(login);
                //log account is not original
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,"( " + login + " ) " + EXCEPTION_NOT_ORIGINAL_ACCOUNT_FOR_REGISTRATION);
                return ERROR_PAGE_CONTEXT;

            }catch (DAOException exception) {
                //log Account is original
            }

            accountDto = new AccountDto();
            accountDto.setRole(role);
            accountDto.setLogin(login);
            accountDto.setPassword(password);

            accountDto = service.create(accountDto);
            requestContext.addAttributeToSession(REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE, accountDto);
            return REGISTER_USER_CONTEXT;

        } else if (btnLogIn != null) {
            if (accountDtoList.size() != 0) {
                accountDto = accountDtoList.get(0);
                requestContext.addAttributeToSession(USER_PAGE_SESSION_COLLECTION_ATTRIBUTE, accountDto);
            } else {
                return DefaultCommand.getInstance().execute(requestContext);
            }
            UserDto userDto = ((UserService) serviceUser).findUserByAccountId(accountDto.getId());
            requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, userDto);
            String userRole = accountDto.getRole();
            if (Objects.equals(userRole, "Teacher") || Objects.equals(userRole, "Student")) {
                List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(), userDto.getLast_name());
                requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            }
            if (userRole.equals("Admin")){
                return ADMIN_PAGE_CONTEXT;
            }else if (userRole.equals("Teacher")){
                return TEACHER_PAGE_CONTEXT;
            }
            return STUDENT_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

