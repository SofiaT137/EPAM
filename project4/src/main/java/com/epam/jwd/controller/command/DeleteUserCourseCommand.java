package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 *  The command of deleting a user from course
 */
public class DeleteUserCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCourseCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new DeleteUserCourseCommand();

    private static final String USER_RESULT_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_PAGE_COMMAND";

    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "notFinishedCourses";
    private static final String CANNOT_FIND_COURSE_BY_NAME_INTO_POSSIBLE_COURSES = "cannotFindThisCourseIntoPossible";
    private static final String DELETE_COURSE_BUTTON = "btnDeleteCourse";
    private static final String GET_BACK_BUTTON = "btnGetBack";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME = "courseName";

    private static final String SOMETHING_WENT_WRONG_EXCEPTION = "Something was wrong when we were trying to delete this course.";

    public static Command getInstance() {
        return INSTANCE;
    }

    private DeleteUserCourseCommand() {

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

    private static final ResponseContext USER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_RESULT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        List<CourseDto> possibleDeleteCoursesList = (List<CourseDto>) requestContext.getAttributeFromSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE);
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE);
        String btnDeleteCourse = requestContext.getParameterFromJSP(DELETE_COURSE_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        String courseName = requestContext.getParameterFromJSP(COURSE_NAME);

        if (btnGetBack != null) {
            return USER_RESULT_CONTEXT;
        }

        if (btnDeleteCourse != null) {

            List<CourseDto> listOfCourses = getCourseByName(courseName,possibleDeleteCoursesList);

            if (listOfCourses.isEmpty()){
                LOGGER.info(CANNOT_FIND_COURSE_BY_NAME_INTO_POSSIBLE_COURSES);
                ERROR_HANDLER.setError(CANNOT_FIND_COURSE_BY_NAME_INTO_POSSIBLE_COURSES,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            CourseDto courseDtoForDelete;

            try{
                courseDtoForDelete = getCourse(listOfCourses);
            }catch (IndexOutOfBoundsException exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            if (!deleteUserFromCourse(courseDtoForDelete,userDto)) {
                LOGGER.error(SOMETHING_WENT_WRONG_EXCEPTION);
                ERROR_HANDLER.setError(SOMETHING_WENT_WRONG_EXCEPTION,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            try{
                removeCourseFromList(possibleDeleteCoursesList,courseDtoForDelete);
            }catch (Exception exception){
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            List<CourseDto> userCourses;
            try{
                removeCourseFromList(userCourse,courseDtoForDelete);
            }catch (DAOException exception){
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourse);
            requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, possibleDeleteCoursesList);
        }
        return REFRESH_PAGE_CONTEXT;
    }

    private List<CourseDto> getCourseByName(String name,List<CourseDto> allPossibleCourses){
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (CourseDto course : allPossibleCourses) {
            if (course.getName().equals(name)){
                courseDtoList.add(course);
            }
        }
        return courseDtoList;
    }

    private CourseDto getCourse(List<CourseDto> listOfCourses){
        return listOfCourses.get(0);
    }

    private boolean deleteUserFromCourse(CourseDto courseDto, UserDto currentUser){
        return ((CourseServiceImpl) courseService).deleteUserFromCourse(courseDto, currentUser);
    }

    private void removeCourseFromList(List<CourseDto> possibleCourses, CourseDto courseDto){
        possibleCourses.remove(courseDto);
    }

    private List<CourseDto> getUserAvailableCourses(UserDto currentUser){
        return ((CourseServiceImpl) courseService).getUserAvailableCourses(currentUser.getFirstName(), currentUser.getLastName());
    }


}
