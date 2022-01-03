package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

/**
 * The command shows "create course (by teacher)" page
 */

public class ShowCreateCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateCoursePageCommand();
    private static final String CREATE_COURSE_JSP = "/WEB-INF/jsp/create_course.jsp";
    private static final String TEACHER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final ResponseContext TEACHER_CREATE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateCoursePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
        requestContext.addAttributeToJSP(TEACHER_COURSE_JSP_COLLECTION_ATTRIBUTE, userCourse);
        return TEACHER_CREATE_COURSE_CONTEXT;
    }
}
