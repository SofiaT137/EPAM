package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

public class TeacherPageCommand implements Command {

    private static final Command INSTANCE = new TeacherPageCommand();
    private static final String RATE_STUDENT_COMMAND = "/controller?command=SHOW_RATE_PAGE_COMMAND";
    private static final String CREATE_COURSE_COMMAND = "/controller?command=SHOW_CREATE_COURSE_PAGE_COMMAND";
    private static final String DELETE_COURSE_COMMAND = "/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND";

    public static Command getInstance() {
        return INSTANCE;
    }

    private TeacherPageCommand() {

    }

    private static final ResponseContext RATE_STUDENT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return RATE_STUDENT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext CREATE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext DELETE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");

        String btnRateStudent = requestContext.getParameterFromJSP("btnRateStudent");
        String btnCreateCourse = requestContext.getParameterFromJSP("btnCreateCourse");
        String btnDeleteCourse = requestContext.getParameterFromJSP("btnDeleteCourse");

        if (btnRateStudent != null){
            //give me all user with role "Student"
            //write it to the session and
        }else if (btnCreateCourse != null){
            return CREATE_COURSE_CONTEXT;
        }else if(btnDeleteCourse != null){
            return DELETE_COURSE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
