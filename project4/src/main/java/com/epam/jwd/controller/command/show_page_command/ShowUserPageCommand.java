package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 *  The command shows "user(student)" page
 */
public class ShowUserPageCommand implements Command {

    private static final Command INSTANCE = new ShowUserPageCommand();
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowUserPageCommand() {

    }

    private static final String USER_PAGE_JSP = "/WEB-INF/jsp/student_page.jsp";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String USER_REVIEW_JSP_COLLECTION_ATTRIBUTE = "user_review";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final String CURRENT_USER = "currentUser";
    private static final String USER_COURSE = "userCourse";
    private static final String USER_REVIEW = "userReview";

    private static final ResponseContext USER_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return USER_PAGE_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession(CURRENT_USER);
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession(USER_COURSE);
        List<ReviewDto> reviewDtoList = (List<ReviewDto>) requestContext.getAttributeFromSession(USER_REVIEW);
        ERROR_HANDLER.flushError(requestContext);
        Pagination pagination = new PaginationImpl(userCourse.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<CourseDto> coursesOnPage = userCourse.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, coursesOnPage);
        requestContext.addAttributeToJSP(USER_REVIEW_JSP_COLLECTION_ATTRIBUTE, reviewDtoList);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return USER_PAGE_CONTEXT;
    }
}
