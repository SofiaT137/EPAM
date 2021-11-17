package com.epam.jwd.controller.command;

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

public class SelectRegistrationOrLogInCommand implements Command {

    private static final Command INSTANCE = new SelectRegistrationOrLogInCommand();
    private static final String REGISTER_USER_JSP = "/WEB-INF/jsp/register_user.jsp";
    private static final String USER_PAGE_JSP = "/WEB-INF/jsp/user_page.jsp";
    private final Service<AccountDto, Integer> service = new AccountService();
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private static final String REGISTER_ACCOUNT_SESSION_COLLECTION_ATTRIBUTE = "registerAccount";
    private static final String USER_PAGE_SESSION_COLLECTION_ATTRIBUTE = "getUserPage";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";


    private static final ResponseContext REGISTER_USER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REGISTER_USER_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return "/controller?command=SHOW_USER_PAGE_COMMAND";
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

        List<AccountDto> accountDtoList = ((AccountService) service).filterAccount(login);

        AccountDto accountDto = new AccountDto();
        accountDto.setRole(role);
        accountDto.setLogin(login);
        accountDto.setPassword(password);

        if (btnRegister != null) {

            if (accountDtoList.size() != 0) {
                return DefaultCommand.getInstance().execute(requestContext);
            }
            service.create(accountDto);
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
            requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
            requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, userDto);
            List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, user_courses);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            return USER_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}

