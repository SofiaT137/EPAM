package com.epam.jwd.controller.command.ShowPageCommand;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;

import java.util.List;

/**
 *  The command shows "user(student)" page
 */
public class ShowUserPageCommand implements Command {

    private static final Command INSTANCE = new ShowUserPageCommand();

    public static Command getInstance() {
        return INSTANCE;
    }

    private ShowUserPageCommand() {

    }

    private static final String USER_PAGE_JSP = "/WEB-INF/jsp/student_page.jsp";
    private static final String CURRENT_USER_JSP_COLLECTION_ATTRIBUTE = "current_user";
    private static final String USER_COURSE_JSP_COLLECTION_ATTRIBUTE = "user_course";
    private static final String USER_REVIEW_JSP_COLLECTION_ATTRIBUTE = "user_review";

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
        UserDto userDto = (UserDto) requestContext.getAttributeFromSession("currentUser");
        List<CourseDto> userCourse = (List<CourseDto>) requestContext.getAttributeFromSession("userCourse");
        List<ReviewDto> reviewDtoList = (List<ReviewDto>) requestContext.getAttributeFromSession("userReview");
        requestContext.addAttributeToJSP(CURRENT_USER_JSP_COLLECTION_ATTRIBUTE, userDto);
        requestContext.addAttributeToJSP(USER_COURSE_JSP_COLLECTION_ATTRIBUTE, userCourse);
        requestContext.addAttributeToJSP(USER_REVIEW_JSP_COLLECTION_ATTRIBUTE, reviewDtoList);
        return USER_PAGE_CONTEXT;
    }
}
