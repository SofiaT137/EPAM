package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.RoleDao;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.model.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class RoleDAO implements RoleDao {

    private static final String SQL_FIND_ROLE_BY_NAME = "SELECT * FROM role WHERE name =  ?";
    private static final String SQL_FIND_ROLE_BY_ID = "SELECT * FROM role WHERE role_id =  ?";

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public int getIdByRoleName(String roleName) {
        Role role = null;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_NAME);
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("role_id");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
        }
        throw new NoSuchElementException("I can't find this role!");
    }

    @Override
    public Role getRoleById(int role_id) {
        Role role = null;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID);
            preparedStatement.setInt(1, role_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Role.getByName(resultSet.getString("name"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
        }
        throw new NoSuchElementException("I can't find this role!");
    }
}
