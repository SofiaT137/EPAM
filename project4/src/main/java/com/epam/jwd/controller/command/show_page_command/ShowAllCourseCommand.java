package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

/**
 *  The command of getting of all university courses
 */

public class ShowAllCourseCommand implements Command {

    private static final Command INSTANCE = new ShowAllCourseCommand();

    private static final String COURSES_JSP = "/WEB-INF/jsp/all_courses.jsp";

    private static final String COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE = "all_courses";


    public static Command getInstance(){
        return INSTANCE;
    }

    private ShowAllCourseCommand(){

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
        List<CourseDto> allCourse =  (List<CourseDto>) requestContext.getAttributeFromSession("allCourses");
        requestContext.addAttributeToJSP(COURSES_JSP_COURSES_COLLECTION_ATTRIBUTE, allCourse);
        return GET_COURSE_CONTEXT;
    }
}