package com.epam.jwd.controller.command.show_page_command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;

import java.util.List;

/**
 * The command shows "user(student) results" page
 */
public class ShowReviewPageCommand implements Command {

    private static final Command INSTANCE = new ShowReviewPageCommand();
    private static final String GET_RESULT_JSP = "/WEB-INF/jsp/user_result.jsp";
    private static final String USER_REVIEW_JSP_COLLECTION_ATTRIBUTE = "user_review";

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
        List<ReviewDto> reviewDtoList = (List<ReviewDto>) requestContext.getAttributeFromSession("userReview");
        requestContext.addAttributeToJSP(USER_REVIEW_JSP_COLLECTION_ATTRIBUTE,reviewDtoList);
        return GET_RESULT_CONTEXT;
    }
}
