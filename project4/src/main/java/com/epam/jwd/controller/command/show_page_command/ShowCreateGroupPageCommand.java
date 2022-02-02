package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

/**
 * The command shows "create group (by administrator)" page
 */
public class ShowCreateGroupPageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateGroupPageCommand();
    private static final String CREATE_GROUP_PAGE_JSP = "/WEB-INF/jsp/create_group.jsp";
    private static final String UNIVERSITY_GROUPS_JSP_COLLECTION_ATTRIBUTE = "university_groups";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateGroupPageCommand() {

    }

    private static final ResponseContext CREATE_GROUP_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_GROUP_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<GroupDto> universityGroups = (List<GroupDto>) requestContext.getAttributeFromSession("universityGroups");
        final int recordsPerPage = 5;
        int numberOfRecords = universityGroups.size();
        int numberOfPages  = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
        int page = 1;
        if(requestContext.getParameterFromJSP("page") != null){
            page = Integer.parseInt(requestContext.getParameterFromJSP("page"));
        }
        if (page < 1 || page > numberOfPages){
            page = 1;
        }
        List<GroupDto> universityGroupOnPage = universityGroups.subList((page-1)*recordsPerPage,Math.min(page*recordsPerPage,numberOfRecords));
        requestContext.addAttributeToJSP(UNIVERSITY_GROUPS_JSP_COLLECTION_ATTRIBUTE, universityGroupOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, numberOfPages);
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return CREATE_GROUP_PAGE_CONTEXT;
    }
}
