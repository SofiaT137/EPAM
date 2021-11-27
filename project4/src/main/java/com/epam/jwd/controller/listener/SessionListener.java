package com.epam.jwd.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionAttributeListener {

    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        LOGGER.info("Name: " + event.getName() + " "
                + "value: " + event.getValue() + " "
                + " was added.");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOGGER.info("Name: " + event.getName() + " "
                + "value: " + event.getValue() + " "
                + " was removed.");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        LOGGER.info("Name: " + event.getName() + " "
                + "value: " + event.getValue() + " "
                + " was replaced.");
    }


//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//      LOGGER.info(se.getSession() + " was created!");
//    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        LOGGER.info(se.getSession() + " was destroyed!");
//    }
}
