package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.pagination.Pagination;
import com.epam.jwd.service.pagination.impl.PaginationImpl;


import java.util.List;
import java.util.Map;

/**
 *  The command of getting of all university reviews
 */
public class ShowAllReviewCommand implements Command {

    private static final Command INSTANCE = new ShowAllReviewCommand();

    private static final String ALL_REVIEW_JSP = "/WEB-INF/jsp/all_review.jsp";

    private static final String REVIEW_JSP_REVIEWS_COLLECTION_ATTRIBUTE = "all_reviews";
    private static final String NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE = "number_of_pages";
    private static final String CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE = "current_page";

    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String NUMBER_OF_PAGES = "numberOfPages";

    public static Command getInstance(){
        return INSTANCE;
    }

    private ShowAllReviewCommand(){

    }

    private static final ResponseContext GET_REVIEW_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ALL_REVIEW_JSP;
        }

        @Override
        public boolean isRedirected() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<ReviewDto> allReview = (List<ReviewDto>) requestContext.getAttributeFromSession("allReviews");

        Pagination pagination = new PaginationImpl(allReview.size());
        int page = pagination.getPage(requestContext);
        Map<String,Integer> paginationInfo = pagination.getPagination(page);

        List<ReviewDto> reviewOnPage = allReview.subList(paginationInfo.get(FROM),paginationInfo.get(TO));
        requestContext.addAttributeToJSP(REVIEW_JSP_REVIEWS_COLLECTION_ATTRIBUTE,reviewOnPage);
        requestContext.addAttributeToJSP(NUMBER_OF_PAGE_JSP_COLLECTION_ATTRIBUTE, paginationInfo.get(NUMBER_OF_PAGES));
        requestContext.addAttributeToJSP(CURRENT_PAGE_JSP_COLLECTION_ATTRIBUTE, page);

        return GET_REVIEW_CONTEXT;
    }
}

