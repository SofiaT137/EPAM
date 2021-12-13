package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

/**
 * The command shows "possible courses for user(student)" page
 */
public class ShowPossibleCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowPossibleCoursePageCommand();

    private static final String GET_COURSE_JSP = "/WEB-INF/jsp/get_course_user.jsp";
    private static final String POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE = "possible_courses";

    private static final ResponseContext GET_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowPossibleCoursePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userPossibleCourse = (List<CourseDto>) requestContext.getAttributeFromSession("possibleCourses");
        requestContext.addAttributeToJSP(POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE, userPossibleCourse);
        return GET_COURSE_CONTEXT;
    }
}
