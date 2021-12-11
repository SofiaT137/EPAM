package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.DAO.model.course.Course;
import com.epam.jwd.DAO.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements DAO<Course,Integer> {

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    private static final Logger LOGGER = LogManager.getLogger(CourseDAO.class);

    private static final String SQL_SAVE_COURSE = "INSERT INTO course (name, start_date, end_date) VALUES (?, ?, ?)";
    private static final String SQL_ADD_USER_INTO_COURSE = "INSERT INTO user_has_course (user_id, course_id) VALUES (?, ?)";
    private static final String SQL_UPDATE_COURSE_BY_ID = "UPDATE course SET name = ?, start_date = ?, end_date = ? WHERE course_id = ?";
    private static final String SQL_FIND_ALL_COURSE = "SELECT * FROM course";
    private static final String SQL_FIND_COURSE_BY_ID = "SELECT * FROM course WHERE course_id =  ?";
    private static final String SQL_FIND_COURSE_BY_NAME = "SELECT * FROM course WHERE name = ?;";
    private static final String SQL_FIND_AVAILABLE_USER_COURSES = "SELECT * FROM user_has_course WHERE user_id = ?";
    private static final String SQL_FIND_ALL_USERS_AT_COURSE = "SELECT * FROM user_has_course WHERE course_id =  ?";
    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM course WHERE course_id = ?";
    private static final String SQL_DELETE_USER_FROM_COURSE = "DELETE FROM user_has_course WHERE user_id = ? and course_id = ?";
    private static final String SQL_DELETE_COURSE_FROM_USER_HAS_COURSE = "DELETE FROM user_has_course WHERE course_id = ?";


    private static final String ERROR_CANNOT_SAVE_COURSE = "I cannot create this course!";
    private static final String ERROR_CANNOT_UPDATE_COURSE= "I cannot update this course!";
    private static final String ERROR_CANNOT_DELETE_COURSE = "I cannot delete this course!";
    private static final String ERROR_CANNOT_FIND_ANY_COURSE = "I cannot find any course!";
    private static final String ERROR_CANNOT_FIND_ANY_COURSE_BY_NAME = "I cannot find any course by it's name!";
    private static final String ERROR_CANNOT_ADD_USER_INTO_COURSE = "I cannot add this user into course!";
    private static final String ERROR_CANNOT_FIND_ANY_AVAILABLE_COURSES= "I cannot find any available courses for this person!";
    private static final String ERROR_CANNOT_FIND_PEOPLE_INTO_COURSE = "I cannot find any person on this course";
    private static final String ERROR_CANNOT_DELETE_THIS_PERSON_FROM_COURSE = "I cannot delete this person from course";
    private static final String ERROR_CANNOT_DELETE_FORM_USER_HAS_COURSE_WHERE_COURSE_ID = "I cannot delete from user_has_course all fields where course_id";
    private static final String ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST = "Something wrong with sql request. Check the data!";


    @Override
    public Integer save(Course course) {
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,course.getName());
            preparedStatement.setDate(2, course.getStartCourse());
            preparedStatement.setDate(3,course.getEndCourse());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int courseId = resultSet.getInt(1);
            course.setId(courseId);
            preparedStatement.close();
            resultSet.close();
            return courseId;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(exception.getMessage());
        }
        throw new DAOException(ERROR_CANNOT_SAVE_COURSE);
    }

    public Boolean addUserIntoCourse(String courseName, String firstName,String lastName){
        try(Connection connection = connectionPool.takeConnection()){
            Course course = filterCourse(courseName).get(0);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.filterUser(firstName,lastName).get(0);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER_INTO_COURSE);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setInt(2,course.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_ADD_USER_INTO_COURSE);
            return false;
        }
    }

    public List<Course> getUserAvailableCourses(String firstName,String lastName){
        List<Course> courses = new ArrayList<>();
        try(Connection connection = connectionPool.takeConnection()){
            UserDAO userDAO = new UserDAO();
            User user = userDAO.filterUser(firstName,lastName).get(0);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_AVAILABLE_USER_COURSES);
            preparedStatement.setInt(1,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                courses.add(findById(resultSet.getInt("course_id")));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.info(ERROR_CANNOT_FIND_ANY_AVAILABLE_COURSES);
        }
        return courses;
    }

    public List<User> getAllUserAtCourse(String courseName){
        List<User> users = new ArrayList<>();
        try(Connection connection = connectionPool.takeConnection()){
            CourseDAO courseDAO = new CourseDAO();
            UserDAO userDAO = new UserDAO();
            Course course = courseDAO.filterCourse(courseName).get(0);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS_AT_COURSE);
            preparedStatement.setInt(1,course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                users.add(userDAO.findById(resultSet.getInt("user_id")));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.info(ERROR_CANNOT_FIND_PEOPLE_INTO_COURSE);
        }
        return users;
    }

    public Boolean deleteUserFromCourse(String courseName, String firstName,String lastName){
        try(Connection connection = connectionPool.takeConnection()){
            Course course = filterCourse(courseName).get(0);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.filterUser(firstName,lastName).get(0);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_FROM_COURSE);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setInt(2,course.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_THIS_PERSON_FROM_COURSE);
            return false;
        }
    }

    public Boolean deleteAllFieldsUserHasCourseByCourseId(int courseId){
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_FROM_USER_HAS_COURSE);
            preparedStatement.setInt(1,courseId);
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_FORM_USER_HAS_COURSE_WHERE_COURSE_ID);
            return false;
        }
    }


    @Override
    public Boolean update(Course course) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_BY_ID);
            preparedStatement.setString(1,course.getName());
            preparedStatement.setDate(2, course.getStartCourse());
            preparedStatement.setDate(3, course.getEndCourse());
            preparedStatement.setInt(4,course.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_UPDATE_COURSE);
            return false;
        }
    }
    @Override
    public Boolean delete(Course course) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_BY_ID);
            preparedStatement.setInt(1,course.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_COURSE);
            return false;
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COURSE);
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = returnCourseList(resultSet);
            preparedStatement.close();
            resultSet.close();
            return courses;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ANY_COURSE);
            return null;
        }
    }

    @Override
    public Course findById(Integer id) {
        Course course = null;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            course = returnCourseList(resultSet).get(0);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ANY_COURSE);
            return null;
        }
        return course;
    }

    public List<Course> filterCourse(String name){
        List<Course> courseList;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_NAME);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            courseList = returnCourseList(resultSet);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ANY_COURSE_BY_NAME);
            return null;
        }
        return courseList;
    }

    private List<Course> returnCourseList (ResultSet resultSet){
        List<Course> courseList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("name"));
                course.setStartCourse(resultSet.getDate("start_date"));
                course.setEndCourse(resultSet.getDate("end_date"));
                courseList.add(course);
            }
        } catch (SQLException exception) {
            LOGGER.error(ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST);
        }
        return courseList;
    }

}
