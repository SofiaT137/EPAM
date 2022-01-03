package com.epam.jwd.controller.command;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;


import java.util.List;

/**
 *  The command of getting of all university users
 */
public class GetAllUserCommand implements Command {

    private static final Command INSTANCE = new GetAllUserCommand();

    private static final String USERS_JSP = "/WEB-INF/jsp/all_users.jsp";

    private static final String USERS_JSP_USERS_COLLECTION_ATTRIBUTE = "all_users";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllUserCommand(){

    }

    private static final ResponseContext GET_USERS_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return USERS_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        final int recordsPerPage = 5;
        List<UserDto> allUsers = (List<UserDto>) requestContext.getAttributeFromSession("allUsers");
        int numberOfRecords = allUsers.size();
        int numberOfPages  = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
        int page = 1;
        if(requestContext.getParameterFromJSP("page") != null){
            page = Integer.parseInt(requestContext.getParameterFromJSP("page"));
        }
        if (page < 1 || page > numberOfPages){
            page = 1;
        }
        List<UserDto> usersOnPage = allUsers.subList((page-1)*recordsPerPage, Math.min(page*recordsPerPage,numberOfRecords));
        requestContext.addAttributeToJSP(USERS_JSP_USERS_COLLECTION_ATTRIBUTE, usersOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, numberOfPages);
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);
        return GET_USERS_CONTEXT;
    }
}
