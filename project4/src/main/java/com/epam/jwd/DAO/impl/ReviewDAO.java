package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.model.course.Course;
import com.epam.jwd.DAO.model.review.Review;
import com.epam.jwd.DAO.model.user.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO implements DAO<Review, Integer> {

    private static final String SQL_SAVE_REVIEW = "INSERT INTO review (user_id, course_id, grade, review) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_REVIEW_BY_ID = "UPDATE review SET user_id = ?, course_id = ?, grade = ? review = ? WHERE review_id = ?";
    private static final String SQL_DELETE_REVIEW_BY_ID = "DELETE FROM review WHERE id = ?";
    private static final String SQL_FIND_ALL_REVIEW = "SELECT * FROM review";
    private static final String SQL_FIND_REVIEW_BY_ID = "SELECT * FROM review WHERE review_id =  ?";
    private static final String SQL_FIND_ACCOUNTS_BY_USER_ID = "SELECT * FROM review WHERE user_id = ?;";

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    public Review createReview(String userFirstName,String userLastName,String courseName,int grade,String review){
        UserDAO userDAO = new UserDAO();
        User user = userDAO.filterUser(userFirstName,userLastName).get(0);
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.filterCourse(courseName).get(0);
        return new Review(user.getId(),course.getId(),grade,review);
    }

    @Override
    public Integer save(Review review) {
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_REVIEW, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,review.getUser_id());
            preparedStatement.setInt(2,review.getCourse_id());
            preparedStatement.setInt(3,review.getGrade());
            preparedStatement.setString(4,review.getReview());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int review_id = resultSet.getInt(1);
            review.setId(review_id);
            preparedStatement.close();
            resultSet.close();
            return review_id;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    @Override
    public Boolean update(Review review) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_REVIEW_BY_ID);
            preparedStatement.setInt(1,review.getUser_id());
            preparedStatement.setInt(2,review.getCourse_id());
            preparedStatement.setInt(3,review.getGrade());
            preparedStatement.setString(4,review.getReview());
            preparedStatement.setInt(5,review.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public Boolean delete(Review review) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW_BY_ID);
            preparedStatement.setInt(1,review.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_REVIEW);
            ResultSet resultSet = preparedStatement.executeQuery();
            reviews = returnReviewList(resultSet);
            preparedStatement.close();
            resultSet.close();
            return reviews;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    @Override
    public Review findById(Integer id) {
        Review review = null;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            review = returnReviewList(resultSet).get(0);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return review;
    }


    public List<Review> filterReview(int user_id){
        List<Review> reviewList;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNTS_BY_USER_ID);
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            reviewList = returnReviewList(resultSet);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return reviewList;
    }

    private List<Review> returnReviewList (ResultSet resultSet){
        List<Review> reviewList = new ArrayList<>();
        try {
            if (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("review_id"));
                review.setUser_id(resultSet.getInt("user_id"));
                review.setCourse_id(resultSet.getInt("course_id"));
                review.setGrade(resultSet.getInt("grade"));
                review.setReview(resultSet.getString("review"));
                reviewList.add(review);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return reviewList;
    }
}
