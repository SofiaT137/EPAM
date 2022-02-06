package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.dao.model.review.Review;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;

import java.util.List;
import java.util.Map;

/**
 * The command shows "user(student) results" page
 */
public class ShowReviewPageCommand implements Command {

    private static final Command INSTANCE = new ShowReviewPageCommand();
    private static final String GET_RESULT_JSP = "/WEB-INF/jsp/user_result.jsp";
    private static final String USER_REVIEW_JSP_COLLECTION_ATTRIBUTE = "user_review";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final String USER_REVIEW = "userReview";

    private static final ResponseContext GET_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return GET_RESULT_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

    ShowReviewPageCommand() {

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<ReviewDto> reviewDtoList = (List<ReviewDto>) requestContext.getAttributeFromSession(USER_REVIEW);

        Pagination pagination = new PaginationImpl(reviewDtoList.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<ReviewDto> reviewsOnPage = reviewDtoList.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(USER_REVIEW_JSP_COLLECTION_ATTRIBUTE, reviewsOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);
        return GET_RESULT_CONTEXT;
    }
}
