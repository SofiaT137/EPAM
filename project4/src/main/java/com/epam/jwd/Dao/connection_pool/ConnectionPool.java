package com.epam.jwd.Dao.connection_pool;

import java.sql.Connection;

public interface ConnectionPool {
    void initialize();
    void shutDown();
    Connection takeConnection();
    void returnConnection(Connection connection);
}
