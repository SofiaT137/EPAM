package com.epam.jwd.controller.custom_tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ErrorTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(ErrorTag.class);

    private Object errorName;
    private static final String TAG_EXCEPTION = "Something wrong with error start tag!";

    public void setErrorName(String userName) {
        this.errorName = userName;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("You have some trouble: " + this.errorName);
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
            throw new JspException(TAG_EXCEPTION);
        }
        return SKIP_BODY;
    }
}
