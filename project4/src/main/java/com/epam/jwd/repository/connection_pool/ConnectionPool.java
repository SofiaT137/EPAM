package com.epam.jwd.repository.connection_pool;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init();
    boolean shutDown();
    Connection takeConnection();
    void returnConnection(Connection connection);
}
