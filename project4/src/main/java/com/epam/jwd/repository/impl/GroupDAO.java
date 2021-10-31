package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.group.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDAO implements DAO<Group,Integer> {

    private static final String SQL_SAVE_GROUP = "INSERT INTO group (name) VALUES (?)";
    private static final String SQL_FIND_ALL_GROUP = "SELECT * FROM group";
    private static final String SQL_FIND_GROUP_BY_ID = "SELECT * FROM group WHERE group_id =  ?";
    private static final String SQL_DELETE_GROUP_BY_ID = "DELETE FROM group WHERE group_id = ?";
    private static final String SQL_UPDATE_GROUP_BY_ID = "UPDATE group SET name = ? WHERE group_id = ?";

    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    @Override
    public Integer save(Group group) {
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_GROUP);
            preparedStatement.setString(1,group.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int group_id = resultSet.getInt(1);
            group.setId(group_id);
            preparedStatement.close();
            resultSet.close();
            return group_id;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }
    @Override
    public Boolean update(Group entity) {
        return null;
    }

    @Override
    public Boolean delete(Group entity) {
        return null;
    }

    @Override
    public List<Group> findAll() {
        return null;
    }

    @Override
    public Group findById(Integer id) {
        return null;
    }
}
