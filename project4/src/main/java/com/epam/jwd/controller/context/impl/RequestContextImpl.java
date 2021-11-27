package com.epam.jwd.controller.context.impl;

import com.epam.jwd.controller.context.api.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

public class RequestContextImpl implements RequestContext{

    private final HttpServletRequest request;
//    private HttpSession session;

    public RequestContextImpl(HttpServletRequest request) {
        this.request = request;
//        this.session = request.getSession(false);
    }
    @Override
    public void addAttributeToJSP(String name, Object attribute) {
        request.setAttribute(name,attribute);
    }

    @Override
    public String getParameterFromJSP(String name) {
        return request.getParameter(name);
    }

    @Override
    public void addAttributeToSession(String name, Object attribute) {
        this.request.getSession().setAttribute(name,attribute);
    }

    @Override
    public Object getAttributeFromSession(String name) {
        return this.request.getSession(false).getAttribute(name);
    }

    @Override
    public void invalidateCurrentSession() {

        request.getSession(false).invalidate();

//        session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
    }
}
