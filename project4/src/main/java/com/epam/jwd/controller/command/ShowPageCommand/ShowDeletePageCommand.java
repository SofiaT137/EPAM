package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

public class ShowDeletePageCommand implements Command {

    private static final Command INSTANCE = new ShowDeletePageCommand();

    private static final String DELETE_COURSE_JSP = "/WEB-INF/jsp/delete_course_user.jsp";
    private static final String POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final ResponseContext DELETE_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowDeletePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
        requestContext.addAttributeToJSP(POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE, userCourse);
        return DELETE_COURSE_CONTEXT;
    }
}
