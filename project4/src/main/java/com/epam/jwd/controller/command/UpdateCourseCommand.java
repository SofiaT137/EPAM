package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;

import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.sql.Date;
import java.util.List;


public class UpdateCourseCommand implements Command {

    private static final Command INSTANCE = new UpdateCourseCommand();
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";
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
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");

        if (btnUpdate != null) {
            Integer id = Integer.parseInt(requestContext.getParameterFromJSP("lblCourseId"));
            String course_name = requestContext.getParameterFromJSP("lblCourseName");
            Date start_date = Date.valueOf(requestContext.getParameterFromJSP("lblStartDate"));
            Date end_date = Date.valueOf(requestContext.getParameterFromJSP("lblEndDate"));

            CourseDto courseDto;
            try{
                courseDto = courseService.getById(id);
            }catch (DAOException exception){
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }
            courseDto.setName(course_name);
            courseDto.setStartCourse(start_date);
            courseDto.setEndCourse(end_date);

            try {
               courseService.update(courseDto);
            } catch (ServerException exception) {
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, exception.getMessage());
                return ERROR_PAGE_CONTEXT;
            }

            List<CourseDto> coursesAfterChanging = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, coursesAfterChanging);
            return REFRESH_PAGE_CONTEXT;

        }else if (btnGetBack != null){
            return TEACHER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
