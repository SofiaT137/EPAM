package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.UserService;


import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;


public class RegisterUserCommand implements Command {

    private static final Command INSTANCE = new RegisterUserCommand();
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private final Service<AccountDto, Integer> accountService = new AccountService();
    private static final String USER_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE = "userReview";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_PAGE_COMMAND;
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

    private RegisterUserCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String firstName = requestContext.getParameterFromJSP("lblFirstName");
        String lastName = requestContext.getParameterFromJSP("lblLastName");
        String group = requestContext.getParameterFromJSP("lblGroup");
        String btnRegister = requestContext.getParameterFromJSP("btnRegister");

        AccountDto registerAccount =null;

        boolean savePoint = false;
        try {
           registerAccount = (AccountDto) requestContext.getAttributeFromSession("registerAccount");

            UserDto userDto = new UserDto();
            userDto.setAccount_id(registerAccount.getId());
            userDto.setGroup_id(Integer.parseInt(group));
            userDto.setFirst_name(firstName);
            userDto.setLast_name(lastName);

            if (btnRegister != null) {
                UserDto current_user = serviceUser.create(userDto);
                savePoint = true;
                requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, current_user);
                List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(current_user.getFirst_name(), current_user.getLast_name());
                requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
                List<ReviewDto> reviewDtoList = new ArrayList<>();
                requestContext.addAttributeToSession(USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE, reviewDtoList);
                return USER_PAGE_CONTEXT;
            }
        }catch (Exception exception){
            if (!savePoint){
                try {
                    accountService.delete(registerAccount);
                } catch (ServerException exception1) {
                    //log "I cannot delete this account
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception1.getMessage());
                    return ERROR_PAGE_CONTEXT;
                }
            }
            //log
            requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
            return ERROR_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
