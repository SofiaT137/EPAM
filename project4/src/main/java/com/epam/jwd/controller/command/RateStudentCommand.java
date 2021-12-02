package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class RateStudentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RateStudentCommand.class);

    private static final Command INSTANCE = new RateStudentCommand();

    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE = "studentsCourse";

    private static final String CANNOT_FIND_USER_MESSAGE = "This user does not exist!";

    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_RATE_PAGE_COMMAND";

        private final Service<ReviewDto, Integer> reviewService = new ReviewService();

    public static Command getInstance() {
        return INSTANCE;
    }

    private RateStudentCommand() {

    }

    private static final ResponseContext REFRESH_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return REFRESH_PAGE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    private static final ResponseContext ERROR_PAGE_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return ERROR_COURSE_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnAddReview = requestContext.getParameterFromJSP("btnAddReview");

        String firstName = requestContext.getParameterFromJSP("lblFirstName");
        String lastName = requestContext.getParameterFromJSP("lblLastName");
        String group_name = requestContext.getParameterFromJSP("group_name");
        String grade = requestContext.getParameterFromJSP("lblGrade");
        String review = requestContext.getParameterFromJSP("lblReview");

        List<UserDto> allCourseUser = (List<UserDto>) requestContext.getAttributeFromSession("studentsCourse");
        CourseDto courseDto = (CourseDto) requestContext.getAttributeFromSession("selectedCourse");

        if (btnAddReview !=null) {

            List<UserDto> getAllUserByFullName = allCourseUser.stream()
                    .filter((userDto1) -> userDto1.getFirst_name().equals(firstName) && userDto1.getLast_name().equals(lastName))
                    .collect(Collectors.toList());

            UserDto currentStudent = getAllUserByFullName.stream()
                    .filter((userDto1) -> userDto1.getGroup_name().equals(group_name))
                    .findFirst()
                    .orElse(null);

            if (currentStudent == null) {
                LOGGER.error(CANNOT_FIND_USER_MESSAGE);
                requestContext.addAttributeToSession(ERROR_SESSION_COLLECTION_ATTRIBUTE, CANNOT_FIND_USER_MESSAGE);
                return ERROR_PAGE_CONTEXT;
            }

            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setUser_id(currentStudent.getId());
            reviewDto.setCourse_name(courseDto.getName());
            reviewDto.setGrade(Integer.parseInt(grade));
            reviewDto.setReview(review);

           reviewService.create(reviewDto);

            List<UserDto> listOfStudents = (List<UserDto>) requestContext.getAttributeFromSession("studentsCourse");
            listOfStudents.remove(currentStudent);
            requestContext.addAttributeToSession(USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE,listOfStudents);
            return REFRESH_PAGE_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
