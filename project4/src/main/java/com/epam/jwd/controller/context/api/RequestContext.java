package com.epam.jwd.controller.context.api;

public interface RequestContext {
    void addAttributeToJSP(String name,Object attribute);
    String getParameterFromJSP(String name);
    void addAttributeToSession(String name,Object attribute);
    Object getAttributeFromSession(String name);
}
