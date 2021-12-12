package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;

/**
 *  The command interface
 */
public interface Command {
    /**
     * @param requestContext  The context of the request
     * @return The context of the response
     */
    ResponseContext execute(RequestContext requestContext);

    /**
     * @param name Command name
     * @return The requested command
     */
    static Command of(String name){
        return Commands.getCommand(name);
    }
}
