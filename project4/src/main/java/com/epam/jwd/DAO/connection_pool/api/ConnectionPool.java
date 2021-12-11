package com.epam.jwd.DAO.connection_pool.api;

import java.sql.Connection;

public interface ConnectionPool {
    void initialize();
    void shutDown();
    Connection takeConnection();
    void returnConnection(Connection connection);
}
