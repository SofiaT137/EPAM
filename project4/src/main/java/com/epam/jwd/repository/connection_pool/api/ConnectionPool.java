package com.epam.jwd.repository.connection_pool.api;

import java.sql.Connection;

public interface ConnectionPool {

    boolean initialize();
    boolean shutDown();
    Connection takeConnection() throws InterruptedException;
    void returnConnection(Connection connection);
}
