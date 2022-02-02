package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;


import java.util.List;

/**
 *  The command of getting of all university reviews
 */
public class GetAllReviewCommand implements Command {

    private static final Command INSTANCE = new GetAllReviewCommand();

    private static final String ALL_REVIEW_JSP = "/WEB-INF/jsp/all_review.jsp";

    private static final String REVIEW_JSP_REVIEWS_COLLECTION_ATTRIBUTE = "all_reviews";

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllReviewCommand(){

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
        requestContext.addAttributeToJSP(REVIEW_JSP_REVIEWS_COLLECTION_ATTRIBUTE,allReview);
        return GET_REVIEW_CONTEXT;
    }
}

