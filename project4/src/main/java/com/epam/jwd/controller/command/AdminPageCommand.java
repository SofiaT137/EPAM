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
import com.epam.jwd.service.impl.ReviewService;
import com.epam.jwd.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AdminPageCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AdminPageCommand.class);

    private static final Command INSTANCE = new AdminPageCommand();

    private static final String GET_ALL_USER_COMMAND = "/controller?command=GET_ALL_USER&page=1";
    private static final String GET_ALL_REVIEW_COMMAND = "/controller?command=GET_ALL_REVIEW";
    private static final String GET_ALL_COURSE_COMMAND = "/controller?command=GET_ALL_COURSE";
    private static final String CREATE_NEW_TEACHER_COMMAND = "/controller?command=SHOW_CREATE_TEACHER_PAGE_COMMAND";
    private static final String BLOCK_USER_COMMAND = "/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND";
    private static final String CREATE_GROUP_COMMAND = "/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private static final String ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE = "allTeachers";
    private static final String ALL_USERS_SESSION_COLLECTION_ATTRIBUTE = "allUsers";
    private static final String ALL_COURSES_SESSION_COLLECTION_ATTRIBUTE = "allCourses";
    private static final String ALL_REVIEWS_SESSION_COLLECTION_ATTRIBUTE = "allReviews";
    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "universityGroups";
    private static final String BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE = "blockedUsers";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final String CANNOT_FIND_ANY_COURSES_LOGGER = "I cannot find any courses at this university";
    private static final String CANNOT_FIND_ANY_REVIEWS_LOGGER = "I cannot find any reviews at this university";
    private static final String FIND_PEOPLE_WITH_ROLE_TEACHER = "Teacher";

    private final Service<UserDto, Integer> userService = new UserService();
    private final Service<CourseDto, Integer> course_Service = new CourseService();
    private final Service<AccountDto, Integer> accountService = new AccountService();
    private final Service<ReviewDto, Integer> reviewService = new ReviewService();
    private final Service<GroupDto, Integer> groupService = new GroupService();


    public static Command getInstance() {
        return INSTANCE;
    }

    private AdminPageCommand() {

    }

    private static final ResponseContext GET_ALL_USER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_ALL_USER_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext GET_ALL_REVIEW_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_ALL_REVIEW_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext GET_ALL_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_ALL_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext CREATE_NEW_TEACHER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_NEW_TEACHER_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext  BLOCK_USER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return  BLOCK_USER_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext CREATE_GROUP_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_GROUP_COMMAND;
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

        String btnShowAllCourses = requestContext.getParameterFromJSP("btnShowAllCourses");
        String btnShowAllUsers = requestContext.getParameterFromJSP("btnShowAllUsers");
        String btnShowAllReviews = requestContext.getParameterFromJSP("btnShowAllReviews");
        String btnCreateNewTeacher = requestContext.getParameterFromJSP("btnCreateNewTeacher");
        String btnBlockUser = requestContext.getParameterFromJSP("btnBlockUser");
        String btnCreateNewGroup = requestContext.getParameterFromJSP("btnCreateNewGroup");

        List<UserDto> allUser = userService.getAll();
        List<GroupDto> all_groups;

        try {
            all_groups = groupService.getAll();
        }catch (ServiceException exception){
            LOGGER.error(exception.getMessage());
            requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
            return ERROR_PAGE_CONTEXT;
        }
        List<CourseDto> courseDtoList = new ArrayList<>();

        if (btnShowAllCourses != null){
            try{
                courseDtoList = course_Service.getAll();
            }catch (ServiceException exception){
                LOGGER.info(CANNOT_FIND_ANY_COURSES_LOGGER);
            }
            requestContext.addAttributeToSession(ALL_COURSES_SESSION_COLLECTION_ATTRIBUTE,courseDtoList);
            return GET_ALL_COURSE_CONTEXT;

        }else if (btnShowAllUsers != null){
            requestContext.addAttributeToSession(ALL_USERS_SESSION_COLLECTION_ATTRIBUTE,allUser);
            return GET_ALL_USER_CONTEXT;

        }else if (btnShowAllReviews != null){
            List<ReviewDto> allReviews = new ArrayList<>();

            try {
                allReviews = reviewService.getAll();
            }catch (ServiceException exception){
                LOGGER.info(CANNOT_FIND_ANY_REVIEWS_LOGGER);
            }

            requestContext.addAttributeToSession(ALL_REVIEWS_SESSION_COLLECTION_ATTRIBUTE,allReviews);
            return GET_ALL_REVIEW_CONTEXT;
        }
        else if (btnCreateNewGroup != null){
            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,all_groups);
            return CREATE_GROUP_CONTEXT;
        }else if (btnCreateNewTeacher != null){
            List<UserDto> allTeachers = findAlLUserTeachers(allUser);
            requestContext.addAttributeToSession(ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE,allTeachers);
           return CREATE_NEW_TEACHER_CONTEXT;
        }else if (btnBlockUser != null){
            List<UserDto> blockedUser = findBlockedUser(allUser);
            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,all_groups);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUser);
            return BLOCK_USER_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<UserDto> findAlLUserTeachers(List<UserDto> list){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto
                : list) {
            int account_id = userDto.getAccount_id();
            AccountDto accountDto;
            accountDto = accountService.getById(account_id);
            if (accountDto.getRole().equals(FIND_PEOPLE_WITH_ROLE_TEACHER)){
                result.add(userDto);
            }
        }
        return result;
    }

    private List<UserDto> findBlockedUser(List<UserDto> list){
        List<UserDto> blockedUser = new ArrayList<>();
        for (UserDto userDto
                : list) {
            if (accountService.getById(userDto.getAccount_id()).getIsActive() == 0){
                blockedUser.add(userDto);
            }
        }
        return blockedUser;
    }
}
