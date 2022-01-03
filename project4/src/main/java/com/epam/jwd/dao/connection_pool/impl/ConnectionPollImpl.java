package com.epam.jwd.dao.connection_pool.impl;

import com.epam.jwd.dao.connection_pool.ConnectionPool;
import com.epam.jwd.dao.connection_pool.DBConnectionManager;
import com.epam.jwd.dao.connection_pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPollImpl implements ConnectionPool {

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
        try{
            final ProxyConnection connection = availableConnections.take();
            givenAwayConnections.add(connection);
            return connection;
        }catch (InterruptedException exception){
            Thread.currentThread().interrupt();
            LOGGER.error(exception.getMessage());
        }
        throw new IllegalStateException();
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        try {
            availableConnections.put(givenAwayConnections.take());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            LOGGER.error(exception.getMessage());
        }
    }


    private void createConnection() throws SQLException{
        Connection connection = DBConnectionManager.getMysqlProperties().getConnection();
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
