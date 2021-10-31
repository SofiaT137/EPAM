package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.group.Group;
import com.epam.jwd.repository.model.user.Account;
import com.epam.jwd.repository.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User,Integer> {

    private static final String SQL_SAVE_USER = "INSERT INTO user (account_id, university_group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE user_id =  ?";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE user SET account_id, university_group_id = ?, first_name = ? last_name = ? WHERE user_id = ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    public User createUser(String accountLogin, String groupName, String firstName,String lastName){
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.filterAccount(accountLogin).get(0);
        GroupDAO groupDAO =  new GroupDAO();
        Group group = groupDAO.filterGroup(groupName).get(0);
        return new User(account.getId(),group.getId(),firstName,lastName);
    }

    @Override
    public Integer save(User user) {
            try(Connection connection = connectionPool.takeConnection()){
                ResultSet resultSet;
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,user.getAccount_id());
                preparedStatement.setInt(2,user.getGroup_id());
                preparedStatement.setString(3,user.getFirst_name());
                preparedStatement.setString(4,user.getLast_name());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int user_id = resultSet.getInt(1);
                user.setId(user_id);
                preparedStatement.close();
                resultSet.close();
                return user_id;
            } catch (SQLException | InterruptedException exception) {
                //TODO log and throw exception;
                return null;
            }
        }

    @Override
    public Boolean update(User user) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
            preparedStatement.setInt(1,user.getAccount_id());
            preparedStatement.setInt(2,user.getGroup_id());
            preparedStatement.setString(3,user.getFirst_name());
            preparedStatement.setString(4,user.getLast_name());
            preparedStatement.setInt(5,user.getId());
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
      return false;
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
            user.setAccount_id(resultSet.getInt(2));
            user.setGroup_id(resultSet.getInt(3));
            user.setFirst_name(resultSet.getString(4));
            user.setLast_name(resultSet.getString(5));

        } catch (SQLException e) {
            //logger
        }
        return user;
    }

 }
