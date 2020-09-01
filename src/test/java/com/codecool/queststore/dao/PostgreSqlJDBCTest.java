package com.codecool.queststore.dao;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class PostgreSqlJDBCTest {

    private static final String URL = "jdbc:h2:~/test_db";
    private static final String USER = "test";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @BeforeAll
    public static void resetDatabase() throws SQLException {
        clearDB();
        Connection connection = PostgreSqlJDBCTest.getConnection();
        try {
            RunScript.execute(connection, new FileReader("src/main/resources/sql/jdbc_test_db.sql"));
            connection.close();
        } catch (IOException e) {
            connection.close();
            e.printStackTrace();
        }
    }

    public static void clearDB() throws SQLException {
        Connection connection = PostgreSqlJDBCTest.getConnection();
        Statement statement = connection.createStatement();
        String query = " DROP ALL OBJECTS;";
        statement.executeUpdate(query);
        connection.close();
    }

    @Test
    public void main() {
        try {
            PostgreSqlJDBCTest.resetDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}