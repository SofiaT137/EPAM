package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SignUpToCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SignUpToCourseCommand.class);

    private static final Command INSTANCE = new SignUpToCourseCommand();

    private static final String GET_COURSE_JSP = "/controller?command=SHOW_POSSIBLE_PAGE_COMMAND";
    private static final String USER_RESULT_JSP = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private final Service<CourseDto, Integer> courseService = new CourseService();

    private static final String POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE = "possibleCourses";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

    private static final String SOMETHING_WENT_WRONG_EXCEPTION = "Something went wrong when we were trying to add this user into course.";

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
        String btnGetCourse = requestContext.getParameterFromJSP("btnGetCourse");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> possibleCoursesList = (List<CourseDto>) requestContext.getAttributeFromSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE);

        if (btnGetCourse != null){
            String name = requestContext.getParameterFromJSP("Course_name");

            List<CourseDto> listOfCourses;

            try{
                listOfCourses = ((CourseService) courseService).filterCourse(name);
            }catch (ServiceException exception){
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            CourseDto findCourse = listOfCourses.get(0);

            Boolean result = ((CourseService) courseService).addUserIntoCourse(findCourse,userDto);
            if (!result){
               LOGGER.error(SOMETHING_WENT_WRONG_EXCEPTION);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, SOMETHING_WENT_WRONG_EXCEPTION);
                return ERROR_PAGE_CONTEXT;
            }
            possibleCoursesList.remove(findCourse);
            List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            requestContext.addAttributeToSession(POSSIBLE_COURSES_SESSION_COLLECTION_ATTRIBUTE, possibleCoursesList);
            return REFRESH_PAGE_CONTEXT;
        }else if (btnGetBack != null){
            return USER_RESULT_CONTEXT;
        }
        else {
            return DefaultCommand.getInstance().execute(requestContext);
        }
    }
}
