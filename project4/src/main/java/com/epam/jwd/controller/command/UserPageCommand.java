package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserPageCommand implements Command {

    private static final Command INSTANCE = new UserPageCommand();
    private static final String SEE_USER_RESULT_JSP = "/WEB-INF/jsp/user_result.jsp";
    private static final String GET_COURSE_COMMAND = "/controller?command=SHOW_POSSIBLE_PAGE_COMMAND";
    private static final String DELETE_COURSE_JSP = "/controller?command=SHOW_DELETE_PAGE_COMMAND";
    private final Service<ReviewDto, Integer> reviewService = new ReviewService();
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private static final String USER_REVIEW_JSP_COLLECTION_ATTRIBUTE = "user_review";
    private static final String CURRENT_USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "user_course";
    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "possibleCourses";


    public static Command getInstance() {
        return INSTANCE;
    }

    private UserPageCommand() {

    }
    private static final ResponseContext SEE_USER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return SEE_USER_RESULT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
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
            return DELETE_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnSeeResults = requestContext.getParameterFromJSP("btnSeeResults");
        String btnGetCourse = requestContext.getParameterFromJSP("btnGetCourse");
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");

        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        if (btnSeeResults != null){
            List<ReviewDto> reviewDtoList = getAllUserReview(userDto.getId(),userCourse);
            requestContext.addAttributeToJSP(USER_REVIEW_JSP_COLLECTION_ATTRIBUTE, reviewDtoList);
            return SEE_USER_RESULT_CONTEXT;
        }else if(btnGetCourse != null){
            List<CourseDto> courseList = courseService.getAll();
            courseList.removeAll(userCourse);
            requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, courseList);
            return GET_COURSE_CONTEXT;
        }else if(btnDeleteCourse != null){
//            requestContext.addAttributeToSession(CURRENT_USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourse);
            return DELETE_COURSE_CONTEXT;
        }
           return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<ReviewDto> getAllUserReview(int user_id, List<CourseDto> listOfCourses){
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (CourseDto courseDto:
                listOfCourses ) {
            try{
                ReviewDto reviewDto = ((ReviewService) reviewService).findReviewByCourseIdAndUserId(courseDto.getId(),user_id);
                reviewDtoList.add(reviewDto);
            }catch (NoSuchElementException exception){
                //log
            }
        }
        return reviewDtoList;
    }
}
