package com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util;

import com.gmail.at.rospopa.pavlo.projectmanager.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnectionManager extends ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String JDBC_TEST_USER = "jdbc.test.user";
    private static final String JDBC_TEST_PASSWORD = "jdbc.test.password";
    private static final String JDBC_URL = "jdbc.url";

    public TestConnectionManager() {

    }

    @Override
    public Connection getConnection() {
        Properties dbProp = PropertiesLoader.getInstance().getDbProperties();

        String user = dbProp.getProperty(JDBC_TEST_USER);
        String password = dbProp.getProperty(JDBC_TEST_PASSWORD);
        return getConnection(user, password);
    }

    private Connection getConnection(String user, String password) {
        Connection connection = null;
        try {
            String url = PropertiesLoader.getInstance().getDbProperties().getProperty(JDBC_URL);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOGGER.error(String.format("Cannot obtain connection to the database. " +
                    "User: %s, password: %s", user, password), e);
        }
        return connection;
    }
}