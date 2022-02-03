package com.epam.jwd.service.error_handler;

import com.epam.jwd.controller.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorHandler {

    private static final Logger LOGGER = LogManager.getLogger(ErrorHandler.class);

    private static final ErrorHandler INSTANCE = new ErrorHandler();

    private static final String ERROR_MESSAGE_SESSION_COLLECTION_ATTRIBUTE = "errorMsg";

    private ErrorHandler() {
    }

    public static ErrorHandler getInstance() {
        return INSTANCE;
    }

    public void setError(String errorName,RequestContext requestContext){
        requestContext.addAttributeToSession(ERROR_MESSAGE_SESSION_COLLECTION_ATTRIBUTE,errorName);
    }

    public void flushError(RequestContext requestContext){
        String errorName = (String) requestContext.getAttributeFromSession(ERROR_MESSAGE_SESSION_COLLECTION_ATTRIBUTE);
        if (errorName != null){
            requestContext.addAttributeToJSP(ERROR_MESSAGE_SESSION_COLLECTION_ATTRIBUTE, errorName);
            requestContext.deleteAttributeFromSession(ERROR_MESSAGE_SESSION_COLLECTION_ATTRIBUTE);
        }
    }
}
