import org.h2.tools.RunScript;

import java.io.FileReader;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseResetter {

    private static final String URL = "jdbc:h2:~/test_db";
    private static final String USER = "test";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void resetDatabase() throws SQLException {
        clearDB();
        Connection connection = DatabaseResetter.getConnection();
        try {
            RunScript.execute(connection, new FileReader("src/main/resources/sql/database_export.sql"));
            connection.close();
        } catch (IOException e) {
            connection.close();
            e.printStackTrace();
        }
    }

    public static void clearDB() throws SQLException {
        Connection connection = DatabaseResetter.getConnection();
        Statement statement = connection.createStatement();
        String query = " DROP ALL OBJECTS;";
        statement.executeUpdate(query);
        connection.close();
    }
}