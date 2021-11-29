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

import java.util.ArrayList;
import java.util.List;


public class DeleteUserCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCourseCommand.class);

    private static final Command INSTANCE = new DeleteUserCourseCommand();

    private static final String USER_RESULT_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";

    private final Service<CourseDto, Integer> courseService = new CourseService();

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";

    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";

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

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> courseDtoList = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        if (btnDeleteCourse != null){

            String name = requestContext.getParameterFromJSP("Course_name");

            List<CourseDto> listOfCourses;

            try{
                listOfCourses = ((CourseService) courseService).filterCourse(name);
            }catch (ServiceException exception){
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            CourseDto courseDtoForDelete = listOfCourses.get(0);

            Boolean result = ((CourseService) courseService).deleteUserFromCourse(courseDtoForDelete,userDto);
            if (!result){
                LOGGER.error(SOMETHING_WENT_WRONG_EXCEPTION);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, SOMETHING_WENT_WRONG_EXCEPTION);
                return ERROR_PAGE_CONTEXT;
            }

            courseDtoList.remove(courseDtoForDelete);

            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, courseDtoList);
            return REFRESH_PAGE_CONTEXT;
        }else if(btnGetBack !=null){

            List<CourseDto> user_courses = new ArrayList<>();
            try{
                user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            }catch (ServiceException exception){
                LOGGER.info(exception.getMessage());
            }
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            return USER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
