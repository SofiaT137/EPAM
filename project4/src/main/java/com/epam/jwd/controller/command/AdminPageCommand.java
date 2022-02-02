package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.GroupServiceImpl;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The main command of admin logic
 */
public class AdminPageCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AdminPageCommand.class);

    private static final Command INSTANCE = new AdminPageCommand();

    private static final String SHOW_ALL_USER_COMMAND = "/controller?command=SHOW_ALL_USER&page=1";
    private static final String SHOW_ALL_REVIEW_COMMAND = "/controller?command=SHOW_ALL_REVIEW";
    private static final String SHOW_ALL_COURSE_COMMAND = "/controller?command=SHOW_ALL_COURSE";
    private static final String CREATE_NEW_TEACHER_COMMAND = "/controller?command=SHOW_CREATE_TEACHER_PAGE_COMMAND";
    private static final String BLOCK_USER_COMMAND = "/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND";
    private static final String CREATE_GROUP_COMMAND = "/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND";

    private static final String ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE = "allTeachers";
    private static final String ALL_USERS_SESSION_COLLECTION_ATTRIBUTE = "allUsers";
    private static final String ALL_COURSES_SESSION_COLLECTION_ATTRIBUTE = "allCourses";
    private static final String ALL_REVIEWS_SESSION_COLLECTION_ATTRIBUTE = "allReviews";
    private static final String ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE = "universityGroups";
    private static final String BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE = "blockedUsers";

    private static final String CANNOT_FIND_ANY_COURSE = "I cannot find any course at this university";
    private static final String CANNOT_FIND_ANY_REVIEW = "I cannot find any review at this university";
    private static final String CANNOT_FIND_ANY_GROUP = "I cannot find any group at this university";
    private static final String CANNOT_FIND_ANY_USER = "I cannot find any user at this university";

    private static final String ROLE_TEACHER = "Teacher";

    private final Service<UserDto, Integer> userService = new UserServiceImpl();
    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();
    private final Service<AccountDto, Integer> accountService = new AccountServiceImpl();
    private final Service<ReviewDto, Integer> reviewService = new ReviewServiceImpl();
    private final Service<GroupDto, Integer> groupService = new GroupServiceImpl();


    public static Command getInstance() {
        return INSTANCE;
    }

    private AdminPageCommand() {

    }

    private static final ResponseContext SHOW_ALL_USER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return SHOW_ALL_USER_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext SHOW_ALL_REVIEW_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return SHOW_ALL_REVIEW_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext SHOW_ALL_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return SHOW_ALL_COURSE_COMMAND;
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

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnShowAllCourses = requestContext.getParameterFromJSP("btnShowAllCourses");
        String btnShowAllUsers = requestContext.getParameterFromJSP("btnShowAllUsers");
        String btnShowAllReviews = requestContext.getParameterFromJSP("btnShowAllReviews");
        String btnCreateNewTeacher = requestContext.getParameterFromJSP("btnCreateNewTeacher");
        String btnBlockUser = requestContext.getParameterFromJSP("btnBlockUser");
        String btnCreateNewGroup = requestContext.getParameterFromJSP("btnCreateNewGroup");

        List<UserDto> allUser = findAllUniversityUsers();
        List<GroupDto> allUniversityGroups = findAllUniversityGroups();

        if (btnShowAllCourses != null){
            requestContext.addAttributeToSession(ALL_COURSES_SESSION_COLLECTION_ATTRIBUTE,findAllUniversityCourses());
            return SHOW_ALL_COURSE_CONTEXT;
        }else if (btnShowAllUsers != null){
            requestContext.addAttributeToSession(ALL_USERS_SESSION_COLLECTION_ATTRIBUTE,allUser);
            return SHOW_ALL_USER_CONTEXT;
        }else if (btnShowAllReviews != null){
            requestContext.addAttributeToSession(ALL_REVIEWS_SESSION_COLLECTION_ATTRIBUTE,findAllUniversityUserReviews());
            return SHOW_ALL_REVIEW_CONTEXT;
        }else if (btnCreateNewGroup != null){
            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,allUniversityGroups);
            return CREATE_GROUP_CONTEXT;
        }else if (btnCreateNewTeacher != null){
            requestContext.addAttributeToSession(ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE,findAlLUserTeachers(allUser));
           return CREATE_NEW_TEACHER_CONTEXT;
        }else if (btnBlockUser != null){
            requestContext.addAttributeToSession(ALL_GROUPS_SESSION_COLLECTION_ATTRIBUTE,allUniversityGroups);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,findBlockedUser(allUser));
            return BLOCK_USER_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<ReviewDto> findAllUniversityUserReviews(){
        List<ReviewDto> allUniversityUserReviews = ((ReviewServiceImpl) reviewService).findAll();
        if (allUniversityUserReviews.isEmpty()){
            LOGGER.info(CANNOT_FIND_ANY_REVIEW);
        }
        return allUniversityUserReviews;
    }

    private List<CourseDto> findAllUniversityCourses(){
        List<CourseDto> allUniversityCourses = courseService.findAll();
        if (allUniversityCourses.isEmpty()){
            LOGGER.info(CANNOT_FIND_ANY_COURSE);
        }
        return allUniversityCourses;
    }

    private List<UserDto> findAllUniversityUsers(){
        List<UserDto> allUniversityUsers = userService.findAll();
        if (allUniversityUsers.isEmpty()){
            LOGGER.info(CANNOT_FIND_ANY_USER);
        }
        return allUniversityUsers;
    }

    private List<GroupDto> findAllUniversityGroups(){
        List<GroupDto> allUniversityGroups = groupService.findAll();
        if (allUniversityGroups.isEmpty()){
            LOGGER.info(CANNOT_FIND_ANY_GROUP);
        }
        return allUniversityGroups;
    }

    private List<UserDto> findAlLUserTeachers(List<UserDto> list){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto
                : list) {
            int accountId = userDto.getAccountId();
            AccountDto accountDto;
            accountDto = accountService.getById(accountId);
            if (accountDto.getRole().equals(ROLE_TEACHER)){
                result.add(userDto);
            }
        }
        return result;
    }

    private List<UserDto> findBlockedUser(List<UserDto> list){
        List<UserDto> blockedUser = new ArrayList<>();
        for (UserDto userDto
                : list) {
            if (accountService.getById(userDto.getAccountId()).getIsActive() == 0){
                blockedUser.add(userDto);
            }
        }
        return blockedUser;
    }
}
