package com.epam.jwd.controller.custom_tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ErrorTag extends TagSupport {

    private Object errorName;
    private static final String TAG_EXCEPTION = "Something wrong with error start tag!";

    public void setErrorName(String userName) {
        this.errorName = userName;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("You have some trouble: " + this.errorName);
        } catch (IOException e) {
            //logger
            throw new JspException(TAG_EXCEPTION);
        }
        return SKIP_BODY;
    }
}
