package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "create teacher (by administrator)" page
 */
public class ShowCreateTeacherPageCommand implements Command {

    private static final Command INSTANCE = new ShowCreateTeacherPageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String CREATE_TEACHER_PAGE_JSP = "/WEB-INF/jsp/create_teacher.jsp";

    private static final String ALL_UNIVERSITY_TEACHERS_JSP_COLLECTION_ATTRIBUTE = "all_teachers";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";


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
        ERROR_HANDLER.flushError(requestContext);
        Pagination pagination = new PaginationImpl(allTeachers.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<UserDto> teachersOnPage = allTeachers.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(ALL_UNIVERSITY_TEACHERS_JSP_COLLECTION_ATTRIBUTE, teachersOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);
        return CREATE_TEACHER_CONTEXT;
    }
}
