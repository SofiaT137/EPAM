package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * The command of deleting courses
 */
public class DeleteCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteCourseCommand.class);

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

        if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }

        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");

        if (btnDeleteCourse != null){

            String name = requestContext.getParameterFromJSP("Course_name");

            List<CourseDto> courseList = ((CourseService) courseService).filterCourse(name);

            if (courseList.isEmpty()){
                LOGGER.error(CANNOT_FIND_COURSE_MESSAGE);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,CANNOT_FIND_COURSE_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }

            CourseDto courseDtoForDelete = courseList.get(0);

            List<ReviewDto> listOfThisCourseReview = new ArrayList<>();
            try {
                listOfThisCourseReview = ((ReviewService)reviewService).findReviewByCourseId(courseDtoForDelete.getId());
            }catch (DAOException exception){
                LOGGER.error(exception.getMessage());
            }
            if (!(listOfThisCourseReview.isEmpty())){
                for (ReviewDto review:
                        listOfThisCourseReview) {
                    try {
                        reviewService.delete(review);
                    } catch (ServiceException exception) {
                        LOGGER.error(exception.getMessage());
                    }
                }
            }
            boolean result = ((CourseService)courseService).deleteAllCourseInUSERHAsCourse(courseDtoForDelete.getId());

            if (!result) {
                LOGGER.error(CANNOT_FIND_COURSE_MESSAGE);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, CANNOT_DELETE_COURSE_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }
                CourseDto courseForDelete = courseService.getById(courseDtoForDelete.getId());
                try{
                    courseService.delete(courseForDelete);
                }catch (Exception exception){
                    LOGGER.error(exception.getMessage());
                    requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                    return ERROR_PAGE_CONTEXT;
                }

            List<CourseDto> coursesAfterDelete = new ArrayList<>();
                try{
                    coursesAfterDelete = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirstName(),userDto.getLastName());
                }
                catch (ServiceException exception){
                    LOGGER.info(exception.getMessage());
                }
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, coursesAfterDelete);
            return REFRESH_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
