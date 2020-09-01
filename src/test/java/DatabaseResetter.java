import javax.imageio.IIOException;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseResetter {

    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "tes";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void resetDatabase() throws SQLException {
        String string;
        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = DatabaseResetter.getConnection();
        clearDB();
        try {
            FileReader fileReader = new FileReader(new File("src/main/resources/sql/database_export.sql"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            bufferedReader.close();
            String query = stringBuilder.toString();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (IOException e) {
            connection.close();
            System.out.println("*** Error : " + e.toString());
            e.printStackTrace();
            System.out.println(stringBuilder.toString());
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