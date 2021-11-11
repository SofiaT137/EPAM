package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.impl.ReviewService;

import java.util.List;

public class GetAllReviewCommand implements Command{

    private static final Command INSTANCE = new GetAllReviewCommand();
    private final Service<ReviewDto,Integer> service = new ReviewService();
    private static final String ALL_REVIEW_JSP = "/WEB-INF/jsp/reviews.jsp";
    private static final String REVIEW_JSP_REVIEWS_COLLECTION_ATTRIBUTE = "reviews";

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

    public static Command getInstance(){
        return INSTANCE;
    }

    private GetAllReviewCommand(){

    }

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        List<ReviewDto> reviewsList = service.getAll();
        requestContext.addAttributeToJSP(REVIEW_JSP_REVIEWS_COLLECTION_ATTRIBUTE,reviewsList);
        return GET_REVIEW_CONTEXT;
    }
}

