package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.student.CommandStudent;

public interface Command {
    ResponseContext execute(RequestContext context);
    static Command of(String name){
       return CommandStudent.getCommand(name);
    }

}
