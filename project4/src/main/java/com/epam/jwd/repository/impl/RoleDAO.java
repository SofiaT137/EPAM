package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.DAO;
import com.epam.jwd.repository.connection_pool.impl.ConnectionPollImpl;
import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.model.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements DAO<Role, Integer> {

    private static final String SQL_SAVE_ROLE = "INSERT INTO role (name) VALUES (?)";
    private static final String SQL_DELETE_ROLE = "DELETE from role WHERE name = ?";
    private static final String SQL_FIND_ROlES_BY_NAME = "SELECT * FROM role WHERE name = ?;";
    private static final String SQL_FIND_ALL_ROLE = "SELECT * FROM role";
    private static final String SQL_FIND_ROLE_BY_ID = "SELECT * FROM role WHERE role_id =  ?";


    private final ConnectionPool connectionPool = ConnectionPollImpl.getInstance();

    public  Role createRole(String name){
        return new Role(name);
    }

    @Override
    public Integer save(Role role){
        try(Connection connection = connectionPool.takeConnection()){
            ResultSet resultSet;
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ROLE);
            preparedStatement.setString(1,role.getName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int role_id = resultSet.getInt(1);
            role.setId(role_id);
            preparedStatement.close();
            resultSet.close();
            return role_id;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return -1;
        }
    }

    @Override
    public Boolean update(Role entity) {
        return false;
    }

    @Override
    public Boolean delete (Role role){
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ROLE);
            preparedStatement.setString(1, role.getName());
            Boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return false;
        }
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles;
        try(Connection connection = connectionPool.takeConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ROLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            roles = returnRoleList(resultSet);
            preparedStatement.close();
            resultSet.close();
            return roles;
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
    }

    @Override
    public Role findById(Integer id) {
        Role role = null;
        try(Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role =  returnRoleList(resultSet).get(0);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return role;
    }

    public List<Role> filterRole(String roleName){
        List<Role> roleList;
        try (Connection connection = connectionPool.takeConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROlES_BY_NAME);
            preparedStatement.setString(1,roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            roleList = returnRoleList(resultSet);
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException | InterruptedException exception) {
            //TODO log and throw exception;
            return null;
        }
        return roleList;
    }

    private List<Role> returnRoleList (ResultSet resultSet){
        List<Role> roleList = new ArrayList<>();
        try {
            if (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("role_id"));
                role.setName(resultSet.getString("name"));
                roleList.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }
}
