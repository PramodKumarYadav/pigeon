package database;

import java.sql.SQLException;

public interface ISeeder {
    public void seedDatabase(DBConnection connection) throws SQLException;

    public void cleanDatabase(DBConnection connection) throws SQLException;
}
