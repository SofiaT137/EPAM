package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

public class ShowPossiblerCoursesCommand implements Command {

    private static final Command INSTANCE = new ShowPossiblerCoursesCommand();

    private static final String REFRESH_COURSE_JSP = "/WEB-INF/jsp/get_course_user.jsp";
    private static final String POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE = "possible_courses";

    private static final ResponseContext REFRESH_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REFRESH_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowPossiblerCoursesCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> possibleCourses = (List<CourseDto>) requestContext.getAttributeFromSession("possibleCourses");
        requestContext.addAttributeToJSP(POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE, possibleCourses);
        return REFRESH_COURSE_CONTEXT;
    }
}
