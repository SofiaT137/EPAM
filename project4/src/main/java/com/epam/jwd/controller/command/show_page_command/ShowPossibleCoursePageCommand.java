package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "possible courses for user(student)" page
 */
public class ShowPossibleCoursePageCommand implements Command {

    private static final Command INSTANCE = new ShowPossibleCoursePageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final String GET_COURSE_JSP = "/WEB-INF/jsp/get_course_user.jsp";
    private static final String POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE = "possible_courses";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final String POSSIBLE_COURSES = "possibleCourses";

    private static final ResponseContext GET_COURSE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_COURSE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowPossibleCoursePageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<CourseDto> userPossibleCourse = (List<CourseDto>) requestContext.getAttributeFromSession(POSSIBLE_COURSES);

        ERROR_HANDLER.flushError(requestContext);
        Pagination pagination = new PaginationImpl(userPossibleCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> possibleCoursesOnPage = userPossibleCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(POSSIBLE_COURSES_JSP_COLLECTION_ATTRIBUTE, possibleCoursesOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return GET_COURSE_CONTEXT;
    }
}
