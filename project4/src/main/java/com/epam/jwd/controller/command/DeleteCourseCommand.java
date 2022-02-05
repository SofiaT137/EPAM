package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

/**
 * The command of deleting courses
 */
public class DeleteCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteCourseCommand.class);

    private static final Command INSTANCE = new DeleteCourseCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String DELETE_COURSE_BUTTON = "btnDeleteCourse";
    private static final String GET_BACK_BUTTON = "btnGetBack";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME = "courseName";

    private static final String CANNOT_FIND_COURSE_MESSAGE = "cannotFindThisCourse";
    private static final String CANNOT_DELETE_ALL_USER_MESSAGE = "cannotDeleteAllUserFromCourse";

    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";

    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

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

    private static final ResponseContext TEACHER_RESULT_CONTEXT  = new ResponseContext() {

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

        String btnDeleteCourse = requestContext.getParameterFromJSP(DELETE_COURSE_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        UserDto currentUser = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        String courseName = requestContext.getParameterFromJSP(COURSE_NAME);

        if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }
        if (btnDeleteCourse != null){
            List<CourseDto> courseList = getCourseListByName(courseName);

            if (courseList.isEmpty()){
                LOGGER.error(CANNOT_FIND_COURSE_MESSAGE);
                ERROR_HANDLER.setError(CANNOT_FIND_COURSE_MESSAGE,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            CourseDto courseForDelete = getCourse(courseList);

            if (!deleteAllUsersFromCourse(courseForDelete)){
                ERROR_HANDLER.setError(CANNOT_DELETE_ALL_USER_MESSAGE,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            try{
                deleteCourse(courseForDelete);
            }catch (ServiceException exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, getAllCoursesAfterDelete(currentUser));
            return REFRESH_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }

    private boolean deleteAllUsersFromCourse(CourseDto courseDtoForDelete){
        return ((CourseServiceImpl)courseService).deleteAllCourseInUserHasCourse(courseDtoForDelete.getId());
    }
    private List<CourseDto> getAllCoursesAfterDelete(UserDto currentUser){
        return ((CourseServiceImpl) courseService).getUserAvailableCourses(currentUser.getFirstName(),currentUser.getLastName());
    }
    private List<CourseDto> getCourseListByName(String courseName){
        return ((CourseServiceImpl)courseService).filterCourse(courseName);
    }
    private CourseDto getCourse(List<CourseDto> allCoursesWithName){
        return allCoursesWithName.get(0);
    }
    private void deleteCourse (CourseDto courseForDelete){
        courseService.delete(courseForDelete);
    }
}
