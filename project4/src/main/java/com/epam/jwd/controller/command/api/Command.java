package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;


public interface Command {
    ResponseContext execute(RequestContext requestContext);
    static Command of(String name){
        return Commands.getCommand(name);
    }
}
