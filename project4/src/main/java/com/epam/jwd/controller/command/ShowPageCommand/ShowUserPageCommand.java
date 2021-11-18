package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;


public class ShowUserPageCommand implements Command {

    private static final Command INSTANCE = new ShowUserPageCommand();

    private static final String USER_PAGE_JSP = "/WEB-INF/jsp/user_page.jsp";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowUserPageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, userCourse);
        return USER_PAGE_CONTEXT;
    }
}
