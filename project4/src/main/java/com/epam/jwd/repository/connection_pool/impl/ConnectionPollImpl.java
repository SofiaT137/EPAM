package com.epam.jwd.repository.connection_pool.impl;

import com.epam.jwd.repository.connection_pool.api.ConnectionPool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPollImpl implements ConnectionPool {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/elective1";
    public static final String USER = "root";
    public static final String PASSWORD = "13041993Sofia";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static int INITIAL_POOL_SIZE = 5;

    private boolean initialized = false;

    private final BlockingQueue<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private final BlockingQueue<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

    private static ConnectionPool INSTANCE;

    private ConnectionPollImpl() {
    }

    public static ConnectionPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPollImpl();
            INSTANCE.initialize();
        }
        return INSTANCE;
    }

    @Override
    public synchronized void initialize() {
        if (!initialized){
            try{
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                //logger
            }
            try{
                for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                    createConnection();
                }
                initialized = true;
                //log info connections was created
            } catch (SQLException exception) {
                // logger
            }
        }
    }

    @Override
    public synchronized void shutDown() {
        closeConnections();
        initialized = false;
        availableConnections.clear();
        givenAwayConnections.clear();
    }

    @Override
    public synchronized Connection takeConnection() throws InterruptedException {
        while(availableConnections.isEmpty()) {
            this.wait();
        }
        final ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        return connection;
    }


    @Override
    public synchronized void returnConnection(Connection connection) {
        if (connection != null) {
            if (givenAwayConnections.remove((ProxyConnection) connection)) {
                availableConnections.add((ProxyConnection) connection);
                notifyAll();
            }
        }
    }

    private void createConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        ProxyConnection proxyConnection = new ProxyConnection(connection,this);
        availableConnections.add(proxyConnection);
    }

    private void closeConnections() {
        closeConnections(this.availableConnections);
        closeConnections(this.givenAwayConnections);
    }

    private void closeConnections(Collection<ProxyConnection> connections) {
        for (ProxyConnection connection : connections) {
            closeConnection(connection);
        }
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            connection.realClose();
        }catch (SQLException exception){
            //log
        }
    }
}
