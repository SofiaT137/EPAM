package com.epam.jwd.Dao.impl;

import com.epam.jwd.Dao.Dao;
import com.epam.jwd.Dao.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.Dao.connection_pool.ConnectionPool;
import com.epam.jwd.Dao.exception.DAOException;
import com.epam.jwd.Dao.model.course.Course;
import com.epam.jwd.Dao.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao<Course,Integer> {

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    private static final Logger LOGGER = LogManager.getLogger(CourseDao.class);

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
    private static final String INFO_CANNOT_FIND_ANY_COURSE = "I cannot find any course!";
    private static final String ERROR_CANNOT_FIND_ANY_COURSE_BY_NAME = "I cannot find any course by it's name!";
    private static final String ERROR_CANNOT_ADD_USER_INTO_COURSE = "I cannot add this user into course!";
    private static final String ERROR_CANNOT_FIND_ANY_AVAILABLE_COURSES= "I cannot find any available courses for this person!";
    private static final String ERROR_CANNOT_FIND_PEOPLE_INTO_COURSE = "I cannot find any person on this course";
    private static final String ERROR_CANNOT_DELETE_THIS_PERSON_FROM_COURSE = "I cannot delete this person from course";
    private static final String ERROR_CANNOT_DELETE_FORM_USER_HAS_COURSE_WHERE_COURSE_ID = "I cannot delete from user_has_course all fields where course_id";
    private static final String ERROR_SOMETHING_WRONG_WITH_SQL_REQUEST = "Something wrong with sql request. Check the data!";


    @Override
    public Integer save(Course course) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,course.getName());
            preparedStatement.setDate(2, course.getStartCourse());
            preparedStatement.setDate(3,course.getEndCourse());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int courseId = resultSet.getInt(1);
            course.setId(courseId);
            return courseId;
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        throw new DAOException(ERROR_CANNOT_SAVE_COURSE);
    }

    public Boolean addUserIntoCourse(String courseName, String firstName,String lastName){
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER_INTO_COURSE)){
            Course course = findCourseByName(courseName).get(0);
            UserDao userDAO = new UserDao();
            User user = userDAO.findUserByFirstNameAndLastName(firstName,lastName).get(0);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setInt(2,course.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_ADD_USER_INTO_COURSE);
            return false;
        }  finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Course> getUserAvailableCourses(String firstName,String lastName){
        List<Course> courses = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_AVAILABLE_USER_COURSES)){
            UserDao userDAO = new UserDao();
            User user = userDAO.findUserByFirstNameAndLastName(firstName,lastName).get(0);
            preparedStatement.setInt(1,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                courses.add(findById(resultSet.getInt("course_id")));
            }
        } catch (SQLException  exception) {
            LOGGER.info(ERROR_CANNOT_FIND_ANY_AVAILABLE_COURSES);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return courses;
    }

    public List<User> getAllUserAtCourse(String courseName){
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS_AT_COURSE)){
            CourseDao courseDAO = new CourseDao();
            UserDao userDAO = new UserDao();
            Course course = courseDAO.findCourseByName(courseName).get(0);
            preparedStatement.setInt(1,course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                users.add(userDAO.findById(resultSet.getInt("user_id")));
            }
        } catch (SQLException exception) {
            LOGGER.info(ERROR_CANNOT_FIND_PEOPLE_INTO_COURSE);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public Boolean deleteUserFromCourse(String courseName, String firstName,String lastName){
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_FROM_COURSE)){
            Course course = findCourseByName(courseName).get(0);
            UserDao userDAO = new UserDao();
            User user = userDAO.findUserByFirstNameAndLastName(firstName,lastName).get(0);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setInt(2,course.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_THIS_PERSON_FROM_COURSE);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Boolean deleteAllFieldsUserHasCourseByCourseId(int courseId){
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_FROM_USER_HAS_COURSE)){
            preparedStatement.setInt(1,courseId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_FORM_USER_HAS_COURSE_WHERE_COURSE_ID);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Course course) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_BY_ID)) {
            preparedStatement.setString(1,course.getName());
            preparedStatement.setDate(2, course.getStartCourse());
            preparedStatement.setDate(3, course.getEndCourse());
            preparedStatement.setInt(4,course.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_UPDATE_COURSE);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
    @Override
    public Boolean delete(Course course) {
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_BY_ID)) {
            preparedStatement.setInt(1,course.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_DELETE_COURSE);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COURSE)){
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = returnCourseList(resultSet);
        } catch (SQLException exception) {
            LOGGER.info(INFO_CANNOT_FIND_ANY_COURSE);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return courses;
    }

    @Override
    public Course findById(Integer id) {
        Course course = null;
        Connection connection = connectionPool.takeConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            course = returnCourseList(resultSet).get(0);
        } catch (SQLException exception) {
            LOGGER.error(INFO_CANNOT_FIND_ANY_COURSE);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return course;
    }

    public List<Course> findCourseByName(String name){
        List<Course> courseList = null;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_NAME)) {
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            courseList = returnCourseList(resultSet);
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ANY_COURSE_BY_NAME);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return courseList;
    }

    private List<Course> returnCourseList(ResultSet resultSet){
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
