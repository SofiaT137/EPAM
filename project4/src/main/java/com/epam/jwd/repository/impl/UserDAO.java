package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User,Integer> {

    private static final String SQL_SAVE_USER = "INSERT INTO user (role_id, group_id, first_name, last_name, login, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id =  ?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET role_id = ?, group_id = ?, first_name = ?, last_name = ? login = ? password = ? WHERE user_id = ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public User save(User user) {
            try(Connection connection = connectionPool.takeConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER);
                preparedStatement.setInt(1,user.getRole_id());
                preparedStatement.setInt(2,user.getGroup_id());
                preparedStatement.setString(3,user.getFirst_name());
                preparedStatement.setString(4,user.getLast_name());
                preparedStatement.setString(5,user.getLogin());
                preparedStatement.setString(6,user.getPassword());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int user_id = resultSet.getInt(1);
                user.setId(user_id);
                preparedStatement.close();
                resultSet.close();
                return user;
            } catch (SQLException | InterruptedException exception) {
                //TODO log and throw exception;
                return null;
            }
        }

    @Override
    public Boolean update(User user) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
            preparedStatement.setInt(1,user.getRole_id());
            preparedStatement.setInt(2,user.getGroup_id());
            preparedStatement.setString(3,user.getFirst_name());
            preparedStatement.setString(4,user.getLast_name());
            preparedStatement.setString(5,user.getLogin());
            preparedStatement.setString(6,user.getPassword());
            preparedStatement.setInt(7,user.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public Boolean delete(User user) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            preparedStatement.setInt(1,user.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(returnUser(resultSet));
            }
            preparedStatement.close();
            resultSet.close();
            return users;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

        @Override
    public User findById(Integer id) {
            User user = null;
            try(Connection connection = connectionPool.takeConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user =  returnUser(resultSet);
                }
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException | InterruptedException exception) {
                //TODO log and throw exception;
                return null;
            }
            return user;
        }

    private User returnUser(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt(1));
            user.setRole_id(resultSet.getInt(2));
            user.setGroup_id(resultSet.getInt(3));
            user.setFirst_name(resultSet.getString(4));
            user.setLast_name(resultSet.getString(5));
            user.setLogin(resultSet.getString(6));
            user.setPassword(resultSet.getString(7));

        } catch (SQLException e) {
            //logger
        }
        return user;
    }

 }
