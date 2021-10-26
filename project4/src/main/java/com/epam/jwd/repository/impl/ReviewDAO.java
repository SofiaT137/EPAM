package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.course.Course;
import com.epam.jwd.repository.model.review.Review;
import com.epam.jwd.repository.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO implements DAO<Review, Integer> {

    private static final String SQL_SAVE_REVIEW = "INSERT INTO review (user, course, grade, review) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_REVIEW_BY_ID = "UPDATE review SET user = ?, course = ?, grade = ? review = ? WHERE review_id = ?";
    private static final String SQL_DELETE_REVIEW_BY_ID = "DELETE FROM review WHERE id = ?";
    private static final String SQL_FIND_ALL_REVIEW = "SELECT * FROM review";
    private static final String SQL_FIND_REVIEW_BY_ID = "SELECT * FROM review WHERE review_id =  ?";

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public Review save(Review review) {
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_REVIEW);
            preparedStatement.setObject(1,review.getUser());
            preparedStatement.setObject(2,review.getCourse());
            preparedStatement.setInt(3,review.getGrade());
            preparedStatement.setString(4,review.getReview());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int review_id = resultSet.getInt(1);
            review.setId(review_id);
            preparedStatement.close();
            resultSet.close();
            return review;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    @Override
    public Boolean update(Review review) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_REVIEW_BY_ID);
            preparedStatement.setObject(1,review.getUser());
            preparedStatement.setObject(2,review.getCourse());
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
            while (resultSet.next()) {
                reviews.add(returnReview(resultSet));
            }
            preparedStatement.close();
            resultSet.close();
            return reviews;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    private Review returnReview(ResultSet resultSet) {
        Review review = new Review();
        try {
            review.setId(resultSet.getInt(1));
            review.setUser((User) resultSet.getObject(2));
            review.setCourse((Course) resultSet.getObject(3));
            review.setGrade(resultSet.getInt(4));
            review.setReview(resultSet.getString(5));
        } catch (SQLException e) {
            //TODO log and throw exception;
        }
        return review;
    }

    @Override
    public Review findById(Integer id) {
        Review review = null;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                review =  returnReview(resultSet);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return review;
    }
}
