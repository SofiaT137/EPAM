package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ShowRegisterPageCommand implements Command {

    private static final Command INSTANCE = new ShowRegisterPageCommand();
    private static final String REGISTER_PAGE_JSP = "/WEB-INF/jsp/register_user.jsp";

    private static final ResponseContext REGISTER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REGISTER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }


    private ShowRegisterPageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return REGISTER_PAGE_CONTEXT;
    }
}
