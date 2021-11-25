package com.epam.jwd.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessionListener implements HttpSessionAttributeListener {

    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        LOGGER.info("Session: " + event.getSession() + "Name: " + event.getName() + " "
                + "value: " + event.getValue() + " "
                + " was added.");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOGGER.info("Session: " + event.getSession() + "Name: " + event.getName() + " "
                + "value: " + event.getValue() + " "
                + " was removed.");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        LOGGER.info("Session: " + event.getSession() + "Name: " + event.getName() + " "
                + "value: " + event.getValue() + " "
                + " was replaced.");
    }
}
