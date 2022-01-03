package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;

/**
 * The command of changing language
 */
public class ChangeLanguageCommand implements Command {

    private static final String UPDATE_PAGE_JSP = "/WEB-INF/jsp/main.jsp";
    private static final String CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE = "language";
    private static final String CURRENT_LANGUAGE_JSP_COLLECTION_ATTRIBUTE = "language";


    private static final ResponseContext UPDATE_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return UPDATE_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private static final Command INSTANCE = new ChangeLanguageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ChangeLanguageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String language = requestContext.getParameterFromJSP(CURRENT_LANGUAGE_JSP_COLLECTION_ATTRIBUTE);

        if (language != null){
            requestContext.addAttributeToSession(CURRENT_LANGUAGE_SESSION_COLLECTION_ATTRIBUTE, language);
            return UPDATE_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
