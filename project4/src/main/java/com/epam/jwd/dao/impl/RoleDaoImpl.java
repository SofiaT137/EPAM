package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.RoleDao;
import com.epam.jwd.dao.connection_pool.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.dao.model.user.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoImpl implements RoleDao {

    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);

    private static final String SQL_FIND_ROLE_BY_NAME = "SELECT * FROM role WHERE name =  ?";
    private static final String SQL_FIND_ROLE_BY_ID = "SELECT * FROM role WHERE role_id =  ?";

    private static final String ERROR_CANNOT_FIND_ROLE_BY_NAME = "I cannot find this role by name!";
    private static final String ERROR_CANNOT_FIND_ROLE_BY_ID = "I cannot find this role by id!";

    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "name";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public int getIdByRoleName(String roleName) {
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_NAME)) {
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(ROLE_ID);
            }
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ROLE_BY_NAME);
            throw new DAOException(ERROR_CANNOT_FIND_ROLE_BY_NAME);
        }  finally {
            connectionPool.returnConnection(connection);
        }
        throw new DAOException(ERROR_CANNOT_FIND_ROLE_BY_NAME);
    }

    @Override
    public Role getRoleById(int roleId) {
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID)) {
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Role.getByName(resultSet.getString(ROLE_NAME));
            }
        } catch (SQLException exception) {
            LOGGER.error(ERROR_CANNOT_FIND_ROLE_BY_ID);
            throw new DAOException(ERROR_CANNOT_FIND_ROLE_BY_ID);
        } finally {
            connectionPool.returnConnection(connection);
        }
        throw new DAOException(ERROR_CANNOT_FIND_ROLE_BY_ID);
    }
}
