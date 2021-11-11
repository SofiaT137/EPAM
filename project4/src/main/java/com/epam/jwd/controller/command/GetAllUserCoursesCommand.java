package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.impl.CourseService;

public class GetAllUserCoursesCommand implements Command {

    private static final Command INSTANCE = new GetAllUserCoursesCommand();
    private final Service<CourseDto,Integer> service = new CourseService();
    private static final String USER_COURSE_JSP = "/WEB-INF/jsp/user_courses.jsp";
    private static final String USERS_HAVE_COURSES_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final ResponseContext GET_USERS_COURSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return USER_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllUserCoursesCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}
