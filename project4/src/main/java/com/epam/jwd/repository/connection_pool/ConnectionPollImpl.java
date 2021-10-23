package com.epam.jwd.repository.connection_pool;

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
    private static int INITIAL_POOL_SIZE = 4;

    private boolean initialized = false;

    private BlockingQueue<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private BlockingQueue<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

    private static ConnectionPool INSTANCE;

    private ConnectionPollImpl() {
    }

    public static ConnectionPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPollImpl();
        }
        return INSTANCE;
    }

    @Override
    public boolean initialize() {
        try {
            if (!initialized) {
                initializeConnections(INITIAL_POOL_SIZE);
                initialized = true;
                return true;
            }
        } catch (ConnectionPoolException exception) {
            //logger
        }
        return false;
    }

    @Override
    public boolean shutDown() {
        if (initialized) {
            closeConnections();
            initialized = false;
            return true;
        }
        return false;
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        while (availableConnections.isEmpty()) {
            this.wait();
        }
        final ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        return connection;
    }

    @Override
    public void returnConnection(Connection connection) {
        if (givenAwayConnections.contains(connection)) {
            availableConnections.add((ProxyConnection) connection);
            this.notifyAll();
        } else {
            //log
        }
    }

    private boolean initializeConnections(int amount) throws ConnectionPoolException {
        try {
            for (int i = 0; i < amount; i++) {
                final Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                final ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.add(proxyConnection);
            }
            return true;
        } catch (SQLException exception) {
            throw new ConnectionPoolException();
        }
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
