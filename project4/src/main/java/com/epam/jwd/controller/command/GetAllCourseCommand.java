package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.impl.CourseService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllCourseCommand implements Command {

    private static final Command INSTANCE = new GetAllCourseCommand();
    private final Service<CourseDto,Integer> service = new CourseService();
    private static final String COURSES_JSP = "/WEB-INF/jsp/courses.jsp";
    private static final String COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE = "courses";

    private static final ResponseContext GET_COURSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return COURSES_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllCourseCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> courseList = service.getAll();
        requestContext.addAttributeToJSP(COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE,courseList);
        return GET_COURSE_CONTEXT;
    }
}
