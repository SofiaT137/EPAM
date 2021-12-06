package com.epam.jwd.controller.filter;

import com.epam.jwd.service.dto.userdto.AccountDto;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(servletNames = "app")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private static final String AUTHENTICATION_MESSAGE = "AuthenticationFilter initialized";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log(AUTHENTICATION_MESSAGE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;


        String uri = req.getRequestURI();
        String commandName = req.getParameter("command");
        HttpSession session = req.getSession(false);

        if (session == null && commandName != null){
            res.sendRedirect("/index.jsp");
            return;
        }

        if (session != null && !(uri.endsWith("/index.jsp"))){
            AccountDto accountDto = (AccountDto)session.getAttribute("registerAccount");
            if (accountDto == null && (commandName == null
                                            || !(commandName.equals("SELECT_REGISTRATION_OR_LOG_IN")
                                                || commandName.equals("SHOW_ERROR_PAGE_COMMAND")
                                                || commandName.equals("CHANGE_LANGUAGE_COMMAND")))){
                res.sendRedirect("/index.jsp");
                return;
            }
        }
        filterChain.doFilter(req,res);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
