package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.exception.CommandException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

/**
 * The  command of creating course
 */
public class CreateCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(CreateCourseCommand.class);

    private static final Command INSTANCE = new CreateCourseCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_CREATE_COURSE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String ERROR_NOT_UNIQUE_COURSE_NAME = "notUniqueCourseName";
    private static final String ADD_COURSE_BUTTON = "btnAddCourse";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME_LABEL = "lblCourseName";
    private static final String START_DATE_LABEL = "lblStartDate";
    private static final String END_DATE_LABEL = "lblEndDate";
    private static final String GET_BACK_BUTTON = "btnGetBack";


    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();


    public static Command getInstance() {
        return INSTANCE;
    }

    private CreateCourseCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REFRESH_PAGE_COMMAND;
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

        String btnAddCourse = requestContext.getParameterFromJSP(ADD_COURSE_BUTTON);
        String btnGetBack = requestContext.getParameterFromJSP(GET_BACK_BUTTON);
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);

        if(btnGetBack !=null){
            return TEACHER_RESULT_CONTEXT;
        }

        if (btnAddCourse !=null) {

            String courseName = requestContext.getParameterFromJSP(COURSE_NAME_LABEL);
            Date startDate = Date.valueOf(requestContext.getParameterFromJSP(START_DATE_LABEL));
            Date endDate = Date.valueOf(requestContext.getParameterFromJSP(END_DATE_LABEL));

            try {
                if (Boolean.FALSE.equals(ifCourseExists(courseName))) {
                    throw new CommandException(ERROR_NOT_UNIQUE_COURSE_NAME);
                }
                CourseDto newCourse = createCourse(courseName,startDate,endDate);
                ((CourseServiceImpl) courseService).addUserIntoCourse(newCourse,userDto);
            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
                return REFRESH_PAGE_CONTEXT;
            }

            List<CourseDto> coursesAfterAddingNewCourse = ((CourseServiceImpl) courseService).getUserAvailableCourses(userDto.getFirstName(),userDto.getLastName());
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, coursesAfterAddingNewCourse);
        }
        return REFRESH_PAGE_CONTEXT;
    }

    private CourseDto createCourse (String courseName,Date startDate,Date endDate){
        CourseDto newCourse = new CourseDto();
        newCourse.setName(courseName);
        newCourse.setStartCourse(startDate);
        newCourse.setEndCourse(endDate);
        courseService.create(newCourse);
        return newCourse;
    }

    private Boolean ifCourseExists(String courseName){
        return ((CourseServiceImpl)courseService).filterCourse(courseName).isEmpty();
    }
}
