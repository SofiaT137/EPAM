package com.epam.jwd.DAO.impl;

import com.epam.jwd.DAO.api.DAO;
import com.epam.jwd.DAO.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.DAO.connection_pool.api.ConnectionPool;
import com.epam.jwd.DAO.exception.DAOException;
import com.epam.jwd.DAO.model.group.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements DAO<Group,Integer> {

    private static final Logger LOGGER = LogManager.getLogger(GroupDAO.class);

    private static final String SQL_SAVE_GROUP = "INSERT INTO university_group (name) VALUES (?)";
    private static final String SQL_FIND_ALL_GROUP = "SELECT * FROM university_group";
    private static final String SQL_FIND_GROUP_BY_ID = "SELECT * FROM university_group WHERE university_group_id =  ?";
    private static final String SQL_FIND_GROUP_BY_NAME = "SELECT * FROM university_group WHERE name = ?";
    private static final String SQL_DELETE_GROUP_BY_NAME = "DELETE from university_group WHERE name = ?";
    private static final String SQL_UPDATE_GROUP_BY_ID = "UPDATE university_group SET name = ? WHERE university_group_id = ?";

    private static final String CANNOT_SAVE_THIS_GROUP = "Cannot save this group";
    private static final String CANNOT_UPDATE_THIS_GROUP = "Cannot update this group";
    private static final String CANNOT_DELETE_THIS_GROUP = "Cannot delete this group";
    private static final String CANNOT_FIND_THIS_GROUP_BY_NAME = "I cannot fins this group by its name";
    private static final String CANNOT_FIND_THIS_GROUP_BY_ID = "Cannot delete this group by it's id";
    private static final String NO_GROUPS_AT_UNIVERSITY = "This university does not have any groups";

    private static final String UNIVERSITY_GROUP_ID = "university_group_id";
    private static final String NAME = "name";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();


    @Override
    public Integer save(Group group) {
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_GROUP, Statement.RETURN_GENERATED_KEYS);
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
            LOGGER.error(CANNOT_SAVE_THIS_GROUP);
            throw new DAOException(CANNOT_SAVE_THIS_GROUP);
        }
    }
    @Override
    public Boolean update(Group group) {
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_GROUP_BY_ID);
            preparedStatement.setString(1,group.getName());
            preparedStatement.setInt(2,group.getId());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(CANNOT_UPDATE_THIS_GROUP);
            return false;
        }
    }


    @Override
    public Boolean delete(Group group) {
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_GROUP_BY_NAME);
            preparedStatement.setString(1, group.getName());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(CANNOT_DELETE_THIS_GROUP);
            return false;
        }
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups;
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_GROUP);
            ResultSet resultSet = preparedStatement.executeQuery();
            groups = returnGroupList(resultSet);
            preparedStatement.close();
            resultSet.close();
            return groups;
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(NO_GROUPS_AT_UNIVERSITY);
            throw new DAOException(NO_GROUPS_AT_UNIVERSITY);
        }
    }

    @Override
    public Group findById(Integer id) {
        Group group;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_GROUP_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            group =  returnGroupList(resultSet).get(0);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(CANNOT_FIND_THIS_GROUP_BY_ID);
            throw new DAOException(CANNOT_FIND_THIS_GROUP_BY_ID);
        }
        return group;
    }

    public Group filterGroup(String groupName){
        Group group;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_GROUP_BY_NAME);
            preparedStatement.setString(1,groupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Group> list = returnGroupList(resultSet);
            if (list.size() == 0){
                throw new DAOException(CANNOT_FIND_THIS_GROUP_BY_NAME);
            }
            group = list.get(0);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            LOGGER.error(CANNOT_FIND_THIS_GROUP_BY_NAME);
            throw new DAOException(CANNOT_FIND_THIS_GROUP_BY_NAME);
        }
        return group;
    }

    private List<Group> returnGroupList (ResultSet resultSet){
        List<Group> groupList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt(UNIVERSITY_GROUP_ID));
                group.setName(resultSet.getString(NAME));
                groupList.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }
}
