package com.epam.jwd.controller.context.impl;

import com.epam.jwd.controller.context.api.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class RequestContextImpl implements RequestContext{

    private final HttpServletRequest request;

    public RequestContextImpl(HttpServletRequest request) {
        this.request = request;
    }
    @Override
    public void addAttributeToJSP(String name, Object attribute) {
        request.setAttribute(name,attribute);
    }
}
