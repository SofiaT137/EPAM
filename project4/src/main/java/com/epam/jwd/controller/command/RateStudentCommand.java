package com.epam.jwd.controller.command;

import com.epam.jwd.DAO.model.user.User;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.context.api.RequestContext;
import com.epam.jwd.controller.context.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.impl.ReviewService;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class RateStudentCommand implements Command {

    private static final Command INSTANCE = new RateStudentCommand();
    private static final String ERROR_SESSION_COLLECTION_ATTRIBUTE = "errorName";
    private static final String CANNOT_FIND_USER_MESSAGE = "This user does not exist!";
    private static final String ERROR_COURSE_COMMAND = "/controller?command=SHOW_ERROR_PAGE_COMMAND";
    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_RATE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_COURSE_COMMAND";
    private static final String USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE = "studentsCourse";

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

    private static final ResponseContext TEACHER_RESULT_CONTEXT = new ResponseContext() {

        @Override
        public String getPage() {
            return TEACHER_RESULT_COMMAND;
        }

        @Override
        public boolean isRedirected() {
            return true;
        }
    };


    @Override
    public ResponseContext execute(RequestContext requestContext) {

        String btnAddReview = requestContext.getParameterFromJSP("btnAddReview");
        String btnGetBack = requestContext.getParameterFromJSP("btnGetBack");

        String firstName = new String(((requestContext.getParameterFromJSP("lblFirstName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
        String lastName = new String(((requestContext.getParameterFromJSP("lblLastName")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
        String group = requestContext.getParameterFromJSP("lblGroup");
        String grade = requestContext.getParameterFromJSP("lblGrade");
        String review = new String(((requestContext.getParameterFromJSP("lblReview")).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);

        List<UserDto> allCourseUser = (List<UserDto>) requestContext.getAttributeFromSession("studentsCourse");
        CourseDto courseDto = (CourseDto) requestContext.getAttributeFromSession("selectedCourse");

        if (btnAddReview !=null) {

            List<UserDto> getAllUserByFullName = allCourseUser.stream()
                    .filter((userDto1) -> userDto1.getFirst_name().equals(firstName) && userDto1.getLast_name().equals(lastName))
                    .collect(Collectors.toList());

            UserDto currentStudent = getAllUserByFullName.stream()
                    .filter((userDto1) -> userDto1.getGroup_id() == Integer.parseInt(group))
                    .findFirst()
                    .orElse(null);

            if (currentStudent == null) {
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
        else if (btnGetBack != null){
            return TEACHER_RESULT_CONTEXT;
        }
        return DefaultCommand.getInstance().execute(requestContext);
    }
}
