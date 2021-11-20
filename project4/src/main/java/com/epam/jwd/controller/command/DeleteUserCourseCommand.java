package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.CourseService;

import java.util.List;
import java.util.NoSuchElementException;

public class DeleteUserCourseCommand implements Command {

    private static final Command INSTANCE = new DeleteUserCourseCommand();
    private static final String USER_RESULT_COMMAND = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_PAGE_COMMAND";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private final Service<CourseDto, Integer> courseService = new CourseService();
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String USER_COURSE_SESSION_COLLECTION_ATTRIBUTE = "userCourse";
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String CANNOT_FIND_COURSE_MESSAGE = "I can't find this course";

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
            //TODO validator for id
            String id = requestContext.getParameterFromJSP("lblDelete");
            int course_id = Integer.parseInt(id);
            CourseDto courseDtoForDelete = courseService.getById(course_id);
            if (courseDtoForDelete == null){
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE,CANNOT_FIND_COURSE_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }
            Boolean result = ((CourseService) courseService).deleteUserFromCourse(courseDtoForDelete,userDto);
            if (!result){
                throw new NoSuchElementException("Something was wrong when we were trying to delete this course.");
            }
            List<CourseDto> user_courses = ((CourseService) courseService).getUserAvailableCourses(userDto.getFirst_name(),userDto.getLast_name());
            requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, user_courses);
            requestContext.addAttributeToSession(USER_COURSE_SESSION_COLLECTION_ATTRIBUTE, user_courses);
            return REFRESH_PAGE_CONTEXT;
        }else if(btnGetBack !=null){
            return USER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
