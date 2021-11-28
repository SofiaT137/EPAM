package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

public class GetAllCourseCommand implements Command {

    private static final Command INSTANCE = new GetAllCourseCommand();

    private static final String COURSES_JSP = "/WEB-INF/jsp/all_courses.jsp";

    private static final String COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE = "all_courses";


    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllCourseCommand(){

    }

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


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> all_course =  (List<CourseDto>) requestContext.getAttributeFromSession("allCourses");
        requestContext.addAttributeToJSP(COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE, all_course);
        return GET_COURSE_CONTEXT;
    }
}
