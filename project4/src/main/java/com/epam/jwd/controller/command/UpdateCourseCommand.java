package com.epam.jwd.controller.command;

import com.epam.jwd.dao.exception.DAOException;
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

import java.sql.Date;
import java.util.List;

/**
 * The command of updating course
 */
public class UpdateCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UpdateCourseCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new UpdateCourseCommand();

    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_UPDATE_COURSE_COMMAND";

    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String CANNOT_FIND_THIS_COURSE_BY_NAME = "cannotFindCourseByName";
    private static final String YOU_ARE_NOT_THE_MENTOR = "youNotTheMentor";
    private static final String UPDATE_COURSE_BUTTON = "btnUpdate";
    private static final String CURRENT_USER = "currentUser";
    private static final String COURSE_NAME_LABEL = "lblCourseName";
    private static final String START_DATE_LABEL = "lblStartDate";
    private static final String END_DATE_LABEL = "lblEndDate";

    private final Service<CourseDto, Integer> courseService = new CourseServiceImpl();

    public static Command getInstance() {
        return INSTANCE;
    }

    private UpdateCourseCommand() {

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

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnUpdate = requestContext.getParameterFromJSP(UPDATE_COURSE_BUTTON);
        UserDto teacher = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);

        if (btnUpdate != null) {

            String courseName = requestContext.getParameterFromJSP(COURSE_NAME_LABEL);
            Date startDate = Date.valueOf(requestContext.getParameterFromJSP(START_DATE_LABEL));
            Date endDate = Date.valueOf(requestContext.getParameterFromJSP(END_DATE_LABEL));

            List<CourseDto> list = ((CourseServiceImpl) courseService).filterCourse(courseName);

            if (list.isEmpty()){
                ERROR_HANDLER.setError(CANNOT_FIND_THIS_COURSE_BY_NAME,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            if (!checkIfThisTeacherMentor(teacher,list.get(0))){
                ERROR_HANDLER.setError(YOU_ARE_NOT_THE_MENTOR,requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            try {
               updateCourse(courseName,startDate,endDate,list.get(0));
            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(),requestContext);
            }
            List<CourseDto> coursesAfterChanging = ((CourseServiceImpl) courseService).getUserAvailableCourses(teacher.getFirstName(),teacher.getLastName());
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, coursesAfterChanging);
        }

        return REFRESH_PAGE_CONTEXT;
    }

    private void updateCourse(String courseName,Date startDate,Date endDate,CourseDto course){
        course.setName(courseName);
        course.setStartCourse(startDate);
        course.setEndCourse(endDate);
        courseService.update(course);
    }

    private boolean checkIfThisTeacherMentor(UserDto teacher,CourseDto courseDto){
        List<CourseDto> allUserCourses = ((CourseServiceImpl)courseService).getUserAvailableCourses(teacher.getFirstName(),teacher.getLastName());
        for (CourseDto course:
                allUserCourses) {
            if (courseDto.equals(course)){
                return true;
            }
        }
        return false;
    }
}
