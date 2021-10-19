package com.epam.jwd.repository.connection_pool;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPollImpl implements ConnectionPool{

    private static final ConnectionPool INSTANCE = new ConnectionPollImpl();
    public static final String DB_URL = "jdbc:mysql://localhost:3306/elective1";
    public static final String USER = "root";
    public static final String PASSWORD = "13041993Sofia";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static int INITIAL_POOL_SIZE = 4;

    private boolean initialized = false;

    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<Connection>(INITIAL_POOL_SIZE);
    private BlockingQueue<Connection> givenAwayConQueue = new ArrayBlockingQueue<Connection>(INITIAL_POOL_SIZE);

    private ConnectionPollImpl (){
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }


    @Override
    public boolean init() {
        return false;
    }

    @Override
    public boolean shutDown() {
        return false;
    }

    @Override
    public Connection takeConnection() {
        return null;
    }

    @Override
    public void returnConnection(Connection connection) {

    }
}
