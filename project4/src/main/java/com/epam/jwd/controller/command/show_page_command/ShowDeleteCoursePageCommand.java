package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

/**
 * The command shows "delete course (by teacher)" page
 */
public class ShowDeleteCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowDeleteCoursePageCommand();

    private static final String DELETE_COURSE_PAGE_JSP = "/WEB-INF/jsp/delete_course.jsp";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";

    public static Command getInstance() {
        return INSTANCE;
    }

    private static final ResponseContext DELETE_COURSE_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private ShowDeleteCoursePageCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, userCourse);
        return DELETE_COURSE_PAGE_CONTEXT;
    }
}

