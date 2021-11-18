package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

public class GetBackUserMenuCommand implements Command {

    private static final Command INSTANCE = new GetBackUserMenuCommand();
    private static final String USER_PAGE_JSP = "/controller?command=SHOW_USER_PAGE_COMMAND";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String CURRENT_USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private GetBackUserMenuCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        if (btnGetBack !=null){
            UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
            List<CourseDto> courseDtoList = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
            requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
            requestContext.addAttributeToJSP(CURRENT_USER_COURSE_JSP_COLLECTION_ATTRIBUTE, courseDtoList);
            return USER_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
