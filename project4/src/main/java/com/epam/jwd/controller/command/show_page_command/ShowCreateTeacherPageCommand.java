package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

/**
 * The command shows "create teacher (by administrator)" page
 */
public class ShowCreateTeacherPageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateTeacherPageCommand();

    private static final String CREATE_TEACHER_PAGE_JSP = "/WEB-INF/jsp/create_teacher.jsp";
    private static final String ALL_UNIVERSITY_TEACHERS_JSP_COLLECTION_ATTRIBUTE = "all_teachers";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowCreateTeacherPageCommand() {

    }

    private static final ResponseContext CREATE_TEACHER_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return CREATE_TEACHER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> allTeachers = (List<UserDto>) requestContext.getAttributeFromSession("allTeachers");
        final int recordsPerPage = 5;
        int numberOfRecords = allTeachers.size();
        int numberOfPages  = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
        int page = 1;
        if(requestContext.getParameterFromJSP("page") != null){
            page = Integer.parseInt(requestContext.getParameterFromJSP("page"));
        }
        if (page < 1 || page > numberOfPages){
            page = 1;
        }
        List<UserDto> teachersOnPage = allTeachers.subList((page-1)*recordsPerPage,Math.min(page*recordsPerPage,numberOfRecords));
        requestContext.addAttributeToJSP(ALL_UNIVERSITY_TEACHERS_JSP_COLLECTION_ATTRIBUTE, teachersOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, numberOfPages);
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);
        return CREATE_TEACHER_CONTEXT;
    }
}
