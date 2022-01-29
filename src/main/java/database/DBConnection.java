package database;

import com.typesafe.config.Config;
import config.EnvFactory;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.jsoup.helper.Validate.fail;

@Slf4j
/*
  Generic rule to follow: The class calling the connection must close the connection.
  (object -> dbConnection.getConnection() will give connection to close).
  When using a try-with-resources statement, the connection is automatically closed
  https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
 */
public class DBConnection implements AutoCloseable {
    private static final Config config = EnvFactory.getInstance().getConfig();
    private static final String DB_CONNECTION_STRING = config.getString("DB_CONNECTION_STRING");
    private static final String DB_USER = config.getString("DB_USER");
    private static final String DB_PASSWORD = config.getString("DB_PASSWORD");
    private Connection connection;

    public DBConnection(String database) {
        setConnection(database);
    }

    public Connection getConnection() {
        return connection;
    }

    private void setConnection(String database) {
        try {
            connection = DriverManager.getConnection(DB_CONNECTION_STRING + database, DB_USER, DB_PASSWORD);
            if (connection != null) {
                log.debug("DBConnection Successful!");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DBConnection failed!", e);
        }
    }

    public ResultSet getResultSet(String sql) {
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            fail("executeQuery failed!");
            throw new IllegalStateException("executeQuery failed!", e);
        }
    }

    public void setResultSet(String sql) {
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            fail("executeUpdate failed!");
            throw new IllegalStateException("executeUpdate failed!", e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
