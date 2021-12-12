package com.epam.jwd.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.text.MessageFormat;


public class SessionListener implements HttpSessionAttributeListener {

    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    private String eventToString(HttpSessionBindingEvent event){
        return MessageFormat.format("Name: {0}, value: {1}", event.getName(),event.getValue());
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String msg = MessageFormat.format("{0} was added.", eventToString(event));
        LOGGER.info(msg);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String msg = MessageFormat.format("{0} was removed.", eventToString(event));
        LOGGER.info(msg);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String msg = MessageFormat.format("{0} was replaced.", eventToString(event));
        LOGGER.info(msg);
    }
}
