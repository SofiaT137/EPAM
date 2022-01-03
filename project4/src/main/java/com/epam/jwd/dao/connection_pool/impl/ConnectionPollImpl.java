package com.epam.jwd.dao.connection_pool.impl;

import com.epam.jwd.dao.connection_pool.ConnectionPool;
import com.epam.jwd.dao.connection_pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
    private static final int INITIAL_POOL_SIZE = 5;

    private boolean initialized = false;

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPollImpl.class);

    private static final String CONNECTION_WAS_CREATED = "Connection was created" ;

    private final BlockingQueue<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private final BlockingQueue<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

    private static ConnectionPool instance;

    private ConnectionPollImpl() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPollImpl();
            instance.initialize();
        }
        return instance;
    }

    @Override
    public synchronized void initialize() {
        if (!initialized){
            try{
                Class.forName(DRIVER);
            } catch (ClassNotFoundException exception) {
                LOGGER.error(exception.getMessage());
            }
            try{
                for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                    createConnection();
                }
                initialized = true;
                LOGGER.info(CONNECTION_WAS_CREATED);
            } catch (SQLException exception) {
                LOGGER.info(exception.getMessage());
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
    public synchronized Connection takeConnection()  {
        while(availableConnections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException exception) {
                LOGGER.error(exception.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        final ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if (connection != null && givenAwayConnections.remove((ProxyConnection) connection)) {
            availableConnections.add((ProxyConnection) connection);
            this.notifyAll();
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
            LOGGER.error(exception.getMessage());
        }
    }
}
