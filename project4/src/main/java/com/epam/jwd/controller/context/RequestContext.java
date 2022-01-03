package com.epam.jwd.controller.context;

/**
 * The context of the request
 */
public interface RequestContext {
    /**
     * Add attribute to JSP
     * @param name of the attribute
     * @param attribute
     */
    void addAttributeToJSP(String name,Object attribute);

    /**
     * Get parameter from JSP
     * @param name
     * @return String parameter
     */
    String getParameterFromJSP(String name);

    /**
     * Add attribute to session
     * @param name
     * @param attribute
     */
    void addAttributeToSession(String name,Object attribute);

    /**
     * Get Attribute from session
     * @param name
     * @return attribute
     */
    Object getAttributeFromSession(String name);

    /**
     * Invalidate current session
     */
    void invalidateCurrentSession();
}
