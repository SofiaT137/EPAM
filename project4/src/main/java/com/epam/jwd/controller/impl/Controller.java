package com.epam.jwd.controller.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.RequestContextImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;


import java.io.IOException;

//@WebServlet(urlPatterns = "/elective")
public class Controller extends HttpServlet {

    private static final String COMMAND_PARAM = "command";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(COMMAND_PARAM);
        Command command = Command.of(commandName);
        ResponseContext commandResult = command.execute(new RequestContextImpl(req));
        if (commandResult.isRedirected()){
            resp.sendRedirect(commandResult.getPage());
        }else {
            RequestDispatcher dispatcher =  req.getRequestDispatcher(commandResult.getPage());
            dispatcher.forward(req,resp);
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req,resp);
//    }


}
