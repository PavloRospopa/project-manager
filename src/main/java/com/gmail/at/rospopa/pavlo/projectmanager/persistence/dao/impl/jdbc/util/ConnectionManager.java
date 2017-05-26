package com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.impl.jdbc.util;

import com.gmail.at.rospopa.pavlo.projectmanager.application.ServiceLocator;
import com.gmail.at.rospopa.pavlo.projectmanager.util.PropertiesLoader;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_USER = "jdbc.user";
    private static final String JDBC_PASSWORD = "jdbc.password";

    private DataSource dataSource;

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ConnectionManager fromJNDI(String jndiEntry) {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envContext.lookup(jndiEntry);

            return new ConnectionManager(dataSource);
        }
        catch (NamingException e){
            LOGGER.error("Cannot obtain JNDI DataSource object", e);
        }

        return null;
    }

    public static ConnectionManager fromProperties() {
        PropertiesLoader propertiesLoader = (PropertiesLoader) ServiceLocator.INSTANCE.get(PropertiesLoader.class);
        Properties dbProperties = propertiesLoader.getDbProperties();

        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setURL(dbProperties.getProperty(JDBC_URL));
            dataSource.setUser(dbProperties.getProperty(JDBC_USER));
            dataSource.setPassword(dbProperties.getProperty(JDBC_PASSWORD));

            return new ConnectionManager(dataSource);
        } catch (SQLException e) {
            LOGGER.error("Cannot instantiate OracleDataSource object", e);
        }

        return null;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }
        catch (SQLException e){
            LOGGER.error("Cannot obtain connection to the database", e);
        }
        return connection;
    }

    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot close connection to the database", e);
        }
    }
}
