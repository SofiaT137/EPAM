package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "delete course (by teacher)" page
 */
public class ShowDeleteCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowDeleteCoursePageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String DELETE_COURSE_PAGE_JSP = "/WEB-INF/jsp/delete_course.jsp";

    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final String NOT_FINISHED_USER_COURSE = "notFinishedCourse";
    private static final String CURRENT_USER = "currentUser";

    public static Command getInstance() {
        return INSTANCE;
    }

    private static final ResponseContext DELETE_COURSE_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return DELETE_COURSE_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    private ShowDeleteCoursePageCommand() {

    }
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(NOT_FINISHED_USER_COURSE);
        ERROR_HANDLER.flushError(requestContext);
        Pagination pagination = new PaginationImpl(userCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> blockedUsersOnPage = userCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, blockedUsersOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return DELETE_COURSE_PAGE_CONTEXT;
    }
}

