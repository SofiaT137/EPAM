package com.epam.jwd.controller.command;


import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.UserService;


import java.util.ArrayList;
import java.util.List;

public class AdminPageCommand implements Command {

    private static final Command INSTANCE = new AdminPageCommand();
    private static final String GET_ALL_USER_COMMAND = "/controller?command=GET_ALL_USER";
    private static final String GET_ALL_REVIEW_COMMAND = "/controller?command=GET_ALL_REVIEW";
    private static final String GET_ALL_COURSE_COMMAND = "/controller?command=GET_ALL_COURSE";
    private static final String CREATE_NEW_TEACHER_COMMAND = "/controller?command=SHOW_CREATE_TEACHER_PAGE_COMMAND";
    private static final String BLOCK_USER_COMMAND = "/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String CREATE_GROUP_COMMAND = "/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND";
    private static final String ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE = "allTeachers";
    private static final String ALL_USERS_SESSION_COLLECTION_ATTRIBUTE = "allUsers";
    private static final String ALL_COURSES_SESSION_COLLECTION_ATTRIBUTE = "allCourses";
    private static final String BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE = "blockedUsers";
    private final Service<UserDto, Integer> serviceUser = new UserService();
    private final Service<CourseDto, Integer> serviceCourse = new CourseService();
    private final Service<AccountDto, Integer> serviceAccount = new AccountService();

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

        List<UserDto> allUser= serviceUser.getAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        if (btnShowAllCourses != null){
            try{
                courseDtoList = serviceCourse.getAll();
            }catch (ServiceException exception){
                //log exception.getMessage();
            }
            requestContext.addAttributeToSession(ALL_COURSES_SESSION_COLLECTION_ATTRIBUTE,courseDtoList);
            return GET_ALL_COURSE_CONTEXT;

        }else if (btnShowAllUsers != null){
            requestContext.addAttributeToSession(ALL_USERS_SESSION_COLLECTION_ATTRIBUTE,allUser);
            return GET_ALL_USER_CONTEXT;

        }else if (btnShowAllReviews != null){
            return GET_ALL_REVIEW_CONTEXT;
        }
        else if (btnCreateNewGroup != null){
            return CREATE_GROUP_CONTEXT;
        }else if (btnCreateNewTeacher != null){
            List<UserDto> allTeachers = findAlLUserTeachers(allUser);
            requestContext.addAttributeToSession(ALL_TEACHERS_SESSION_COLLECTION_ATTRIBUTE,allTeachers);
           return CREATE_NEW_TEACHER_CONTEXT;
        }else if (btnBlockUser != null){
            List<UserDto> blockedUser = findBlockedUser(allUser);
            requestContext.addAttributeToSession(BLOCKED_USERS_SESSION_COLLECTION_ATTRIBUTE,blockedUser);
            return BLOCK_USER_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<UserDto> findAlLUserTeachers(List<UserDto> list){
        List<UserDto> result = new ArrayList<>();
        for (UserDto userDto : list) {
            int account_id = userDto.getAccount_id();
            AccountDto accountDto;
            accountDto = serviceAccount.getById(account_id);
            if (accountDto.getRole().equals("Teacher")){
                result.add(userDto);
            }
        }
        return result;
    }

    private List<UserDto> findBlockedUser(List<UserDto> list){
        List<UserDto> blockedUser = new ArrayList<>();
        for (UserDto userDto : list) {
            if (serviceAccount.getById(userDto.getAccount_id()).getIsActive() == 0){
                blockedUser.add(userDto);
            }
        }
        return blockedUser;
    }
}
