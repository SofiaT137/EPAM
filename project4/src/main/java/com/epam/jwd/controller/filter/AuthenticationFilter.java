package com.epam.jwd.controller.filter;

import com.epam.jwd.service.dto.userdto.AccountDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);

    private static final String AUTHENTICATION_MESSAGE = "AuthenticationFilter initialized";

    private static final String INDEX_JSP = "/index.jsp";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext();
        LOGGER.info(AUTHENTICATION_MESSAGE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;


        String uri = req.getRequestURI();
        String commandName = req.getParameter("command");
        HttpSession session = req.getSession(false);

        if (session == null && commandName != null){
            res.sendRedirect(INDEX_JSP);
            return;
        }

        if (session != null && !(uri.endsWith(INDEX_JSP))){
            AccountDto accountDto = (AccountDto)session.getAttribute("registerAccount");
            if (accountDto == null && (commandName == null
                                            || !(commandName.equals("SELECT_REGISTRATION_OR_LOG_IN")
                                            || commandName.equals("CHANGE_LANGUAGE_COMMAND")))){
                res.sendRedirect(INDEX_JSP);
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
