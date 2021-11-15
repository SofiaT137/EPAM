package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class SignUpToCourseCommand implements Command {

    private static final Command INSTANCE = new SignUpToCourseCommand();

    private static final String GET_COURSE_JSP = "/WEB-INF/jsp/get_course_user.jsp";

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance(){
        return INSTANCE;
    }

    private SignUpToCourseCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return null;
    }
}
