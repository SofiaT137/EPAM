package com.epam.jwd.controller.custom_tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class HelloTag extends TagSupport {

    private Object userName;
    private static final String TAG_EXCEPTION = "Something wrong with start tag!";

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("Hello, " + this.userName);
        } catch (IOException e) {
            //logger
            throw new JspException(TAG_EXCEPTION);
        }
        return SKIP_BODY;
    }
}

