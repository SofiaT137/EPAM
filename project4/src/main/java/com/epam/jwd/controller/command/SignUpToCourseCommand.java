package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.exception.CommandException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The command of signing up student to course
 */
public class SignUpToCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SignUpToCourseCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new SignUpToCourseCommand();

    private static final String GET_COURSE_JSP = "/controller?command=SHOW_POSSIBLE_PAGE_COMMAND";
    private static final String USER_RESULT_JSP = "/controller?command=SHOW_USER_PAGE_COMMAND";

    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "possibleCourses";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CANNOT_FIND_COURSE_BY_NAME_INTO_POSSIBLE_COURSES = "cannotFindThisCourseIntoPossible";
    private static final String SOMETHING_WENT_WRONG_EXCEPTION = "somethingWentWrong";
    private static final String GET_COURSE_BUTTON = "btnGetCourse";
    private static final String GET_BACK_BUTTON = "btnGetBack";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME = "courseName";

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext USER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_RESULT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    public static Command getInstance(){
        return INSTANCE;
    }

    private SignUpToCourseCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> possibleCoursesList = (List<CourseDto>) requestContext.getAttributeFromSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE);
        String btnGetCourse = requestContext.getParameterFromJSP(GET_COURSE_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        String courseName = requestContext.getParameterFromJSP(COURSE_NAME);

        if (btnGetBack != null) {
            return USER_RESULT_CONTEXT;
        }
        if (btnGetCourse != null) {

            List<CourseDto> listOfCourses = getCourseByName(courseName,possibleCoursesList);

            try {

                if (listOfCourses.isEmpty()) {
                    throw new CommandException(CANNOT_FIND_COURSE_BY_NAME_INTO_POSSIBLE_COURSES);
                }

                CourseDto findCourse = getCourse(listOfCourses);

                if (!addUserIntoCourse(findCourse, userDto)) {
                    throw new CommandException(SOMETHING_WENT_WRONG_EXCEPTION);
                }

                removeCourseFromList(possibleCoursesList, findCourse);

                List<CourseDto> userCourses = getUserAvailableCourses(userDto);

                requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, userCourses);
                requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, possibleCoursesList);

            }catch (Exception exception){
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
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

        private boolean addUserIntoCourse(CourseDto courseDto, UserDto currentUser){
            return ((CourseServiceImpl) courseService).addUserIntoCourse(courseDto, currentUser);
        }

        private void removeCourseFromList(List<CourseDto> possibleCourses, CourseDto courseDto){
            possibleCourses.remove(courseDto);
        }

        private List<CourseDto> getUserAvailableCourses(UserDto currentUser){
            return ((CourseServiceImpl) courseService).getUserAvailableCourses(currentUser.getFirstName(), currentUser.getLastName());
        }


}

