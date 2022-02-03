package com.epam.jwd.controller;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.controller.context.impl.RequestContextImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

/**
 * The controller presents a web servlet
 */
@WebServlet(urlPatterns = "/controller", name = "app")
public class Controller extends HttpServlet {

    private static final String COMMAND_PARAM = "command";

    /**
     * Process request method
     * @param request
     * @param response
     * @throws ServletException exception
     * @throws IOException exception
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND_PARAM);
        Command command = Command.of(commandName);
        RequestContext requestContext = new RequestContextImpl(request);
        ResponseContext commandResult = command.execute(requestContext);
         if (commandResult.isRedirected()){
            response.sendRedirect(commandResult.getPage());
        }else {
            RequestDispatcher dispatcher =  request.getRequestDispatcher(commandResult.getPage());
            dispatcher.forward(request,response);
        }
    }

    /**
     * DoGet method
     * @param req request
     * @param resp response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    /**
     * DoPost method
     * @param req request
     * @param resp response
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }


}