package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
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


public class RegisterUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegisterUserCommand.class);

    private static final Command INSTANCE = new RegisterUserCommand();

    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private final Service<AccountDto, Integer> accountService = new AccountService();
    private final Service<GroupDto, Integer> groupService = new GroupService();

    private static final String USER_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE = "userReview";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final String LOGGER_INFO_THE_COURSE_LIST_EMPTY = "The course list is empty";

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
        String groupName = requestContext.getParameterFromJSP("group_name");
        String btnRegister = requestContext.getParameterFromJSP("btnRegister");

        AccountDto registerAccount = null;
        UserDto currentUser = null;

        GroupDto groupDto = ((GroupService) groupService).filterGroup(groupName);

        boolean savePoint = false;

        try {
            if (btnRegister != null) {
                registerAccount = (AccountDto) requestContext.getAttributeFromSession("registerAccount");

                UserDto userDto = new UserDto();
                userDto.setAccountId(registerAccount.getId());
                userDto.setGroupName(groupDto.getName());
                userDto.setFirstName(firstName);
                userDto.setLastName(lastName);

                currentUser = serviceUser.create(userDto);
                savePoint = true;
                requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, currentUser);
            }
        } catch (Exception exception) {
            if (!savePoint) {
                try {
                    accountService.delete(registerAccount);
                } catch (ServiceException exception1) {
                    LOGGER.error(exception1.getMessage());
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception1.getMessage());
                    return ERROR_PAGE_CONTEXT;
                }
            }
            LOGGER.error(exception.getMessage());
            requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
            return ERROR_PAGE_CONTEXT;
        }

        List<CourseDto> userCourses = new ArrayList<>();
        try {
            userCourses = ((CourseService) courseService).getUserAvailableCourses(currentUser.getFirstName(), currentUser.getLastName());
        } catch (ServiceException exception) {
            LOGGER.info(LOGGER_INFO_THE_COURSE_LIST_EMPTY);
        }
        requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourses);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        requestContext.addAttributeToSession(USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE, reviewDtoList);
        return USER_PAGE_CONTEXT;
    }
}
