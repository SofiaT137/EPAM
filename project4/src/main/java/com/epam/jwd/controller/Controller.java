package com.epam.jwd.controller;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.controller.context.impl.RequestContextImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet(urlPatterns = "/controller", name = "app")
public class Controller extends HttpServlet {

    private static final String COMMAND_PARAM = "command";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND_PARAM);
        Command command = Command.of(commandName);
        ResponseContext commandResult = command.execute(new RequestContextImpl(request));
        if (commandResult.isRedirected()){
            response.sendRedirect(commandResult.getPage());
        }else {
            RequestDispatcher dispatcher =  request.getRequestDispatcher(commandResult.getPage());
            dispatcher.forward(request,response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }


}