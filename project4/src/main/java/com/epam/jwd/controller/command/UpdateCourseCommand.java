package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
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

import java.sql.Date;
import java.util.List;

/**
 * The command of updating course
 */
public class UpdateCourseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UpdateCourseCommand.class);

    private static final Command INSTANCE = new UpdateCourseCommand();

    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_UPDATE_COURSE_COMMAND";

    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";

    private final Service<CourseDto, Integer> courseService = new CourseService();

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

        String btnUpdate = requestContext.getParameterFromJSP("btnUpdate");
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");

        if (btnUpdate != null) {

            String courseName = requestContext.getParameterFromJSP("Course_name");
            Date startDate = Date.valueOf(requestContext.getParameterFromJSP("lblStartDate"));
            Date endDate = Date.valueOf(requestContext.getParameterFromJSP("lblEndDate"));

            CourseDto courseDto;

            try{
                List<CourseDto> list = ((CourseService) courseService).filterCourse(courseName);
                courseDto = list.get(0);
            }catch (DAOException exception){
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            courseDto.setName(courseName);
            courseDto.setStartCourse(startDate);
            courseDto.setEndCourse(endDate);

            try {
               courseService.update(courseDto);
            } catch (ServiceException exception) {
                LOGGER.error(exception.getMessage());
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            List<CourseDto> coursesAfterChanging = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirstName(),userDto.getLastName());
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, coursesAfterChanging);
            return REFRESH_PAGE_CONTEXT;

        } return DefaultCommand.getInstance().execute(requestContext);
    }
}
