package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.model.user.User;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.GroupServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The command of register student
 */
public class RegisterUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegisterUserCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new RegisterUserCommand();

    private final Service<UserDto, Integer> serviceUser = new UserServiceImpl();
    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();
    private final Service<AccountDto, Integer> accountService = new AccountServiceImpl();
    private final Service<GroupDto, Integer> groupService = new GroupServiceImpl();

    private static final String USER_PAGE_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_REGISTER_PAGE_COMMAND";
    private static final String DEFAULT_COMMAND = "/controller?command=DEFAULT";

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE = "userReview";
    private static final String CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE = "currentUser";
    private static final String FIRST_NAME_LABEL = "lblFirstName";
    private static final String LAST_NAME_LABEL = "lblLastName";
    private static final String GROUP_NAME = "groupName";
    private static final String BUTTON_REGISTER = "btnRegister";
    private static final String REGISTER_ACCOUNT = "registerAccount";

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

    private static final ResponseContext DEFAULT_PAGE_CONTEXT = new ResponseContext() {

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

    private RegisterUserCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String firstName = requestContext.getParameterFromJSP(FIRST_NAME_LABEL);
        String lastName = requestContext.getParameterFromJSP(LAST_NAME_LABEL);
        String groupName = requestContext.getParameterFromJSP(GROUP_NAME);
        String btnRegister = requestContext.getParameterFromJSP(BUTTON_REGISTER);

        AccountDto registerAccount = (AccountDto) requestContext.getAttributeFromSession(REGISTER_ACCOUNT);

        boolean savePoint = false;

            if (btnRegister != null) {

                GroupDto groupDto;

                try{
                    groupDto = findGroupByName(groupName);
                }catch (DAOException exception){
                    ERROR_HANDLER.setError(exception.getMessage(), requestContext);
                    return REFRESH_PAGE_CONTEXT;
                }

                UserDto currentUser;
                try{
                    currentUser = createUser(registerAccount,groupDto,firstName,lastName);
                    savePoint = true;
                }catch (Exception exception){
                    LOGGER.error(exception.getMessage());
                    ERROR_HANDLER.setError(exception.getMessage(), requestContext);
                    return REFRESH_PAGE_CONTEXT;
                }

                requestContext.addAttributeToSession(CURRENT_USER_SESSION_COLLECTION_ATTRIBUTE, currentUser);
            }
            if (!savePoint) {
                try {
                    accountService.delete(registerAccount);
                } catch (Exception exception) {
                    LOGGER.error(exception.getMessage());
                    ERROR_HANDLER.setError(exception.getMessage(), requestContext);
                }
                return DEFAULT_PAGE_CONTEXT;
            }

        List<CourseDto> userCourses = new ArrayList<>();

        requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourses);

        List<ReviewDto> reviewDtoList = new ArrayList<>();

        requestContext.addAttributeToSession(USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE, reviewDtoList);
        return USER_PAGE_CONTEXT;
    }

    private UserDto createUser(AccountDto registerAccount, GroupDto groupDto, String firstName, String lastName){
        UserDto currentUser = new UserDto();
        currentUser.setAccountId(registerAccount.getId());
        currentUser.setGroupName(groupDto.getName());
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser = serviceUser.create(currentUser);
        return currentUser;
    }

    private GroupDto findGroupByName (String groupName){
        return ((GroupServiceImpl) groupService).filterGroup(groupName);
    }


}
