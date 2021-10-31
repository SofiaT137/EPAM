package com.epam.jwd.repository.connection_pool.impl;

import com.epam.jwd.repository.connection_pool.api.ConnectionPool;
import com.epam.jwd.repository.connection_pool.exception.ConnectionPoolException;

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
    private static int INITIAL_POOL_SIZE = 4;
    private static int MAX_POOL_SIZE = 5;

    private boolean initialized = false;

    private BlockingQueue<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private BlockingQueue<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

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
    public void initialize() {
        if (!initialized){
            try{
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
//                logger
            }
            try{
                for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                    createConnection();
                }
                initialized = true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

    }

    @Override
    public void shutDown() {
        closeConnections();
        initialized = false;
        availableConnections.clear();
        givenAwayConnections.clear();
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        if (!availableConnections.isEmpty()) {
            availableConnections.poll();
        } else if (availableConnections.size() < MAX_POOL_SIZE) {
            try {
                createConnection();
            } catch (SQLException exception) {
                //logger
            }
        }
        while (availableConnections.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                //log.error();
            }
        }
        return availableConnections.poll();
    }

    @Override
    public void returnConnection(Connection connection) {
        if (givenAwayConnections.contains(connection)) {
            givenAwayConnections.remove((ProxyConnection) connection);
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (availableConnections.size() + givenAwayConnections.size() < INITIAL_POOL_SIZE
                ||availableConnections.isEmpty()){
                availableConnections.add((ProxyConnection) connection);
            }
            else {
                closeConnection((ProxyConnection) connection);
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
