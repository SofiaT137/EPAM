package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

public class ShowErrorPageCommand implements Command{

    private static final Command INSTANCE = new ShowErrorPageCommand();
    private static final String ERROR_JSP = "/WEB-INF/jsp/error.jsp";
    private static final String ERROR_NAME_JSP_COLLECTION_ATTRIBUTE = "error_name";
    private static final String ERROR_NAME_SESSION_COLLECTION_ATTRIBUTE = "errorName";


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowErrorPageCommand() {

    }

    private static final ResponseContext ERROR_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ERROR_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String message = (String) requestContext.getAttributeFromSession(ERROR_NAME_SESSION_COLLECTION_ATTRIBUTE);
        requestContext.addAttributeToJSP(ERROR_NAME_JSP_COLLECTION_ATTRIBUTE,message);
        return ERROR_CONTEXT;
    }
}
