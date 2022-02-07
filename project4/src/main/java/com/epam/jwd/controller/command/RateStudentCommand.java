package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.exception.CommandException;
import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.controller.context.ResponseContext;
import com.epam.jwd.service.Service;
import com.epam.jwd.service.dto.coursedto.CourseDto;
import com.epam.jwd.service.dto.reviewdto.ReviewDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.error_handler.ErrorHandler;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The command of estimating students
 */
public class RateStudentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RateStudentCommand.class);
    private static final ErrorHandler ERROR_HANDLER = ErrorHandler.getInstance();

    private static final Command INSTANCE = new RateStudentCommand();

    private static final String USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE = "studentsCourse";
    private static final String ADD_REVIEW_BUTTON = "btnAddReview";
    private static final String FIRST_NAME_LABEL = "lblFirstName";
    private static final String LAST_NAME_LABEL = "lblLastName";
    private static final String GROUP_NAME = "groupName";
    private static final String GRADE_LABEL = "lblGrade";
    private static final String REVIEW_LABEL = "lblReview";
    private static final String SELECTED_COURSE = "selectedCourse";

    private static final String CANNOT_FIND_USER_MESSAGE = "cannotFindUserByFullName";
    private static final String CANNOT_FIND_USER_INTO_GROUP = "cannotFindStudentInGroup";
    private static final String INCORRECT_GRADE = "incorrectGrade";

    private static final String REFRESH_PAGE_COMMAND = "/controller?command=SHOW_RATE_PAGE_COMMAND";
    private static final String TEACHER_RESULT_COMMAND = "/controller?command=SHOW_TEACHER_PAGE_COMMAND";

        private final Service<ReviewDto, Integer> reviewService = new ReviewServiceImpl();

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

        String btnAddReview = requestContext.getParameterFromJSP(ADD_REVIEW_BUTTON);
        String firstName = requestContext.getParameterFromJSP(FIRST_NAME_LABEL);
        String lastName = requestContext.getParameterFromJSP(LAST_NAME_LABEL);
        String groupName = requestContext.getParameterFromJSP(GROUP_NAME);
        int grade = Integer.parseInt(requestContext.getParameterFromJSP(GRADE_LABEL));
        String review = requestContext.getParameterFromJSP(REVIEW_LABEL);


        List<UserDto> allUsersOnCourse = (List<UserDto>) requestContext.getAttributeFromSession(USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE);
        List<UserDto> listOfStudents = (List<UserDto>) requestContext.getAttributeFromSession(USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE);
        CourseDto courseDto = (CourseDto) requestContext.getAttributeFromSession(SELECTED_COURSE);

        UserDto currentStudent;

        if (btnAddReview !=null) {

            try{

            List<UserDto> foundedUsers = getAllUserByFullName(allUsersOnCourse,firstName,lastName);

            if (foundedUsers.isEmpty()){
                throw new CommandException(CANNOT_FIND_USER_MESSAGE);
            }

             currentStudent = findStudentInGroup(foundedUsers,groupName);

            if (currentStudent == null) {
                throw new CommandException(CANNOT_FIND_USER_INTO_GROUP);
            }

            if (grade < 0 || grade > 10){
                throw new CommandException(INCORRECT_GRADE);
            }

            createReview(currentStudent,courseDto,grade,review);
            listOfStudents.remove(currentStudent);

            }catch (Exception exception){
                LOGGER.error(exception.getMessage());
                ERROR_HANDLER.setError(exception.getMessage(), requestContext);
                return REFRESH_PAGE_CONTEXT;
            }
            requestContext.addAttributeToSession(USERS_ON_COURSE_SESSION_COLLECTION_ATTRIBUTE,listOfStudents);
        }
        return REFRESH_PAGE_CONTEXT;
    }

    private List<UserDto> getAllUserByFullName(List<UserDto> allCourseUser,String firstName,String lastName){
        return allCourseUser.stream()
                .filter(user -> user.getFirstName().equals(firstName) && user.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    private UserDto findStudentInGroup (List<UserDto> getAllUserByFullName, String groupName){
        return getAllUserByFullName.stream()
                .filter(userDto1 -> userDto1.getGroupName().equals(groupName))
                .findFirst()
                .orElse(null);
    }

    private void createReview(UserDto currentStudent, CourseDto currentCourse, Integer grade, String review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setFirstName(currentStudent.getFirstName());
        reviewDto.setLastName(currentStudent.getLastName());
        reviewDto.setCourseName(currentCourse.getName());
        reviewDto.setGrade(grade);
        reviewDto.setReview(review);
        reviewService.create(reviewDto);
    }
}
