package com.epam.jwd.dao.connection_pool;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger(DBConnectionManager.class);

    private static final String DB_URL = "DB_URL";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";
    private static final String CHARACTER_ENCODING = "CHARACTER_ENCODING";
    private static final String DATABASE_PROPERTIES = "database.properties";


    private static MysqlDataSource mySqlDataSource = null;

    private DBConnectionManager() {
    }

    public static MysqlDataSource getMysqlProperties() {
        if (Objects.isNull(mySqlDataSource))
            initDataSource();
        return mySqlDataSource;
    }

    private static void initDataSource() {
        Properties properties = new Properties();
        try (InputStream inputStream = DBConnectionManager.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES)) {
            properties.load(inputStream);
            mySqlDataSource = new MysqlDataSource();
            mySqlDataSource.setURL(properties.getProperty(DB_URL));
            mySqlDataSource.setUser(properties.getProperty(USER));
            mySqlDataSource.setPassword(properties.getProperty(PASSWORD));
            mySqlDataSource.setCharacterEncoding(properties.getProperty(CHARACTER_ENCODING));
        } catch (IOException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
