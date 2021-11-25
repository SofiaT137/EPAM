package com.epam.jwd.controller.context.impl;

import com.epam.jwd.controller.context.api.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestContextImpl implements RequestContext{

    private final HttpServletRequest request;
    private HttpSession session;

    public RequestContextImpl(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
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
        this.session.setAttribute(name,attribute);
    }

    @Override
    public Object getAttributeFromSession(String name) {
        return this.session.getAttribute(name);
    }

    @Override
    public void invalidateCurrentSession() {
        session = request.getSession(false);
        if (session != null) {
            request.getSession().invalidate();
        }
    }
}
