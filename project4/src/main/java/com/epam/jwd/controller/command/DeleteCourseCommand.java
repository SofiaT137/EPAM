package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;
import com.epam.jwd.service.impl.ReviewService;
import com.epam.jwd.service.impl.UserService;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteCourseCommand implements Command {

    private static final Command INSTANCE = new DeleteCourseCommand();
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String CANNOT_FIND_COURSE_MESSAGE = "I can't find this course";
    private static final String CANNOT_DELETE_COURSE_MESSAGE = "I can't delete all courses by course_id";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private final Service<ReviewDto, Integer> reviewService = new ReviewService();
    private final Service<UserDto, Integer> userService = new UserService();

    public static Command getInstance() {
        return INSTANCE;
    }

    private DeleteCourseCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_COMMAND;
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

    private static final ResponseContext TEACHER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_RESULT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");

        if (btnDeleteCourse != null){
            String id = requestContext.getParameterFromJSP("lblDelete");
            int course_id = Integer.parseInt(id);
            CourseDto courseDtoForDelete = courseService.getById(course_id);
            if (courseDtoForDelete == null){
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,CANNOT_FIND_COURSE_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }
            //delete all review where course_id
            List<ReviewDto> listOfThisCourseReview = new ArrayList<>();
            try {
                listOfThisCourseReview = ((ReviewService)reviewService).findReviewByCourseId(course_id);
            }catch (DAOException exception){
                //log
            }
            if (listOfThisCourseReview.size() != 0){
                for (ReviewDto review:
                        listOfThisCourseReview) {
                    try {
                        reviewService.delete(review);
                    } catch (ServerException e) {
                        //log
                    }
                }
            }
            Boolean result = ((CourseService)courseService).deleteAllCourseInUSERHAsCourse(course_id);
            if (!result) {
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, CANNOT_DELETE_COURSE_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }
                CourseDto courseForDelete = courseService.getById(course_id);
                try{
                    courseService.delete(courseForDelete);
                }catch (Exception exception){
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                    return ERROR_PAGE_CONTEXT;
                }

            List<CourseDto> coursesAfterDelete = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, coursesAfterDelete);
            return REFRESH_PAGE_CONTEXT;
        }else if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
