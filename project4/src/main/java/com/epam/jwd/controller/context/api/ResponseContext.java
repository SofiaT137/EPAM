package com.epam.jwd.controller.context.api;

/**
 * The context of the request
 */
public interface ResponseContext {
    /**
     * Get page
     * @return page
     */
    String getPage();

    /**
     *
     * @return
     */
    boolean isRedirected();
}
