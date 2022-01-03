package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;

import java.util.List;

/**
 *  The command shows "update course" page
 */
public class ShowUpdateCourseCommand implements Command {

    private static final Command INSTANCE = new ShowUpdateCourseCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowUpdateCourseCommand() {

    }

    private static final String UPDATE_PAGE_JSP = "/WEB-INF/jsp/update_course.jsp";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";


    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return UPDATE_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, userCourse);
        return USER_PAGE_CONTEXT;
    }
}
