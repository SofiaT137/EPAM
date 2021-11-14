package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.ReviewService;

import java.util.ArrayList;
import java.util.List;

public class UserPageCommand implements Command {

    private static final Command INSTANCE = new UserPageCommand();
    private static final String SEE_USER_RESULT_JSP = "/WEB-INF/jsp/user_result.jsp";
    private static final String GET_COURSE_JSP = "/WEB-INF/jsp/get_course_user.jsp";
    private static final String DELETE_COURSE_JSP = "/WEB-INF/jsp/delete_course_user.jsp";
    private final Service<ReviewDto, Integer> reviewService = new ReviewService();
    private static final String USER_REVIEW_JSP_COLLECTION_ATTRIBUTE = "user_review";


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
            return GET_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private static final ResponseContext DELETE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnSeeResults = requestContext.getParameterFromJSP("btnSeeResults");
        String btnGetCourse = requestContext.getParameterFromJSP("btnGetCourse");
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");
        String btnLogOut = requestContext.getParameterFromJSP("btnLogOut");

        if (btnSeeResults != null){
            UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
            List<CourseDto> courseDtoList = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
            List<ReviewDto> reviewDtoList = getAllUserReview(userDto.getId(),courseDtoList);
            requestContext.addAttributeToJSP(USER_REVIEW_JSP_COLLECTION_ATTRIBUTE, reviewDtoList);
            return SEE_USER_RESULT_CONTEXT;
        }else if(btnGetCourse != null){
            //get all possible courses without user's courses
            return GET_COURSE_CONTEXT;
        }else if(btnDeleteCourse != null){
            //get all user's courses and send it on next JSP
            return DELETE_COURSE_CONTEXT;
        }
           return DefaultCommand.getInstance().execute(requestContext);
    }

    private List<ReviewDto> getAllUserReview(int user_id, List<CourseDto> listOfCourses){
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (CourseDto courseDto:
                listOfCourses ) {
            reviewDtoList.add(((ReviewService) reviewService).findReviewByCourseIdAndUserId(courseDto.getId(),user_id));
        }
        return reviewDtoList;
    }
}
