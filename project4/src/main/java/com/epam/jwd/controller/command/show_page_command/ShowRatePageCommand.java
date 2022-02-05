package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.groupdto.GroupDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "rate student" page
 */
public class ShowRatePageCommand implements Command {

    private static final Command INSTANCE = new ShowRatePageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String RATE_STUDENT_JSP = "/WEB-INF/jsp/rate_student.jsp";

    private static final String COURSE_STUDENT_JSP_COLLECTION_ATTRIBUTE = "students_course";
    private static final String ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE = "all_groups";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";

    private static final ResponseContext RATE_STUDENT_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return RATE_STUDENT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowRatePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<UserDto> userOnCourse = (List<UserDto>) requestContext.getAttributeFromSession("studentsCourse");
        List<GroupDto> groupDtoList = (List<GroupDto>) requestContext.getAttributeFromSession("allGroups");
        ERROR_HANDLER.flushError(requestContext);

        Pagination pagination = new PaginationImpl(userOnCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<UserDto> blockedUsersOnPage = userOnCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(ALL_GROUPS_JSP_COLLECTION_ATTRIBUTE, groupDtoList);
        requestContext.addAttributeToJSP(COURSE_STUDENT_JSP_COLLECTION_ATTRIBUTE, blockedUsersOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return RATE_STUDENT_PAGE_CONTEXT;
    }
}
