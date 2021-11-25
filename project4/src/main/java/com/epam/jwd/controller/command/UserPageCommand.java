package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.ReviewService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class UserPageCommand implements Command {

    private static final Command INSTANCE = new UserPageCommand();
    private static final String GET_REVIEW_COMMAND = "/controller?command=SHOW_REVIEW_PAGE_COMMAND";
    private static final String GET_COURSE_COMMAND = "/controller?command=SHOW_POSSIBLE_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_PAGE_COMMAND";
    private final Service<ReviewDto, Integer> reviewService = new ReviewService();
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private static final String USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE = "userReview";
    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "possibleCourses";


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
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        String btnSeeResults = requestContext.getParameterFromJSP("btnSeeResults");
        String btnGetCourse = requestContext.getParameterFromJSP("btnGetCourse");
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");

        if (btnSeeResults != null){
            List<ReviewDto> reviewDtoList = getAllUserReview(userDto.getId(),userCourse);
            requestContext.addAttributeToSession(USER_REVIEW_SESSION_COLLECTION_ATTRIBUTE, reviewDtoList);
            return SEE_USER_RESULT_CONTEXT;
        }else if(btnGetCourse != null){
            List<CourseDto> courseList = new ArrayList<>();
            try {
                courseList = courseService.getAll();
            }catch (ServiceException exception){
                //log
            }
            List<CourseDto> possibleCourses = findUserPossibleToSignInCourses(courseList,userCourse);
            requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, possibleCourses);
            return GET_COURSE_CONTEXT;
        }else if(btnDeleteCourse != null){
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

    private List<CourseDto> findUserPossibleToSignInCourses(List<CourseDto> allCourses, List<CourseDto> userCourses){
        List<CourseDto> result = new ArrayList<>();
        long millis=System.currentTimeMillis();
        Date dateForCheck = new Date(millis);

        allCourses.removeAll(userCourses);
        if (allCourses.size() != 0){
            result = allCourses.stream()
                    .filter(courseDto -> courseDto.getEndCourse().after(dateForCheck))
                    .collect(Collectors.toList());
        }

        return result;
    }
}
