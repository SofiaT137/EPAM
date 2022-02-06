package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.error_handler.ErrorHandler;


/**
 * The default command
 */
public class DefaultCommand implements Command {

    private static final Command INSTANCE = new DefaultCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();
    private static final String MAIN_JSP = "/WEB-INF/jsp/main.jsp";

    public static Command getInstance(){
        return INSTANCE;
    }

    private DefaultCommand(){

    }

    private static final ResponseContext DEFAULT_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return MAIN_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };



    @Override
    public ResponseContext execute(RequestContext context) {
        ERROR_HANDLER.flushError(context);
        return DEFAULT_PAGE_CONTEXT;
    }
}
