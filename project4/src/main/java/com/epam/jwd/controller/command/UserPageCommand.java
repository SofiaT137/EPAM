package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The main command of student logic
 */
public class UserPageCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UserPageCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new UserPageCommand();
    private static final String GET_REVIEW_COMMAND = "/controller?command=SHOW_REVIEW_PAGE_COMMAND";
    private static final String GET_COURSE_COMMAND = "/controller?command=SHOW_POSSIBLE_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_PAGE_COMMAND";

    private final Service<ReviewDto, Integer> reviewService = new ReviewServiceImpl();
    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

    private static final String USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE = "userReview";
    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "possibleCourses";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String SEE_RESULT_BUTTON = "btnSeeResults";
    private static final String CURRENT_USER = "currentUser";
    private static final String GET_COURSE_BUTTON = "btnGetCourse";
    private static final String DELETE_COURSE_BUTTON = "btnDeleteCourse";


    public static Command getInstance() {
        return INSTANCE;
    }

    private UserPageCommand() {

    }

    private static final ResponseContext SEE_USER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_REVIEW_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext GET_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext DELETE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE);

        String btnSeeResults = requestContext.getParameterFromJSP(SEE_RESULT_BUTTON);
        String btnGetCourse = requestContext.getParameterFromJSP(GET_COURSE_BUTTON);
        String btnDeleteCourse = requestContext.getParameterFromJSP(DELETE_COURSE_BUTTON);

        List<CourseDto> courseList = new ArrayList<>();
        try {
            courseList = courseService.findAll();
        }catch (ServiceException exception){
            LOGGER.info(exception.getMessage());
        }
        if (btnSeeResults != null){
            List<ReviewDto> userReview = new ArrayList<>();
            try {
                userReview = getAllUserReview(userDto.getId(),userCourse);
            }catch (DAOException exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
            }
            requestContext.addAttributeToSession(USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE,userReview);
            return SEE_USER_RESULT_CONTEXT;
        }else if(btnGetCourse != null){
            requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, findUserPossibleToSignInCourses(courseList,userCourse));
            return GET_COURSE_CONTEXT;
        }else if(btnDeleteCourse != null){
            List<CourseDto> finishedUserCourses = findAllFinishedUserCourses(userCourse);
            userCourse.removeAll(finishedUserCourses);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE,userCourse);
            return DELETE_COURSE_CONTEXT;
        }
           return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<ReviewDto> getAllUserReview(int userId, List<CourseDto> listOfCourses) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (CourseDto courseDto :
                listOfCourses) {
            List<ReviewDto> reviewDto = ((ReviewServiceImpl) reviewService).findReviewByCourseIdAndUserId(courseDto.getId(), userId);
            reviewDtoList.add(reviewDto.get(0));
        }
        return reviewDtoList;
    }

    private List<CourseDto> findUserPossibleToSignInCourses(List<CourseDto> allCourses, List<CourseDto> userCourses){
        List<CourseDto> result = new ArrayList<>();
        long millis=System.currentTimeMillis();
        Date dateForCheck = new Date(millis);
        allCourses.removeAll(userCourses);

        if (!(allCourses.isEmpty())){
            for (CourseDto course:
                 allCourses) {
                if (course.getEndCourse().after(dateForCheck)){
                    result.add(course);
                }
            }
        }

        return result;
    }

    private List<CourseDto> findAllFinishedUserCourses(List<CourseDto> userCourses){
        List<CourseDto> result = new ArrayList<>();
        long millis=System.currentTimeMillis();
        Date dateForCheck = new Date(millis);

        if (!(userCourses.isEmpty())){
            for (CourseDto course:
                    userCourses) {
                if (course.getEndCourse().before(dateForCheck)){
                    result.add(course);
                }
            }
        }
        return result;
    }
}
