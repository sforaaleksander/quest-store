package com.codecool.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlJDBC {
    private static Connection connection = null;

    public Connection getConnection() {
        final String url = "jdbc:postgresql://ec2-54-75-244-161.eu-west-1.compute.amazonaws.com:5432/da9vidtssmmkj9";
        final String user = "psvvypnkwffifs";
        final String password = "5a21cf41def123115cdeaaa367e70f31907b2b0b73a1f643b212e2e8bf097327";

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password); // set user and password
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Opened database successfully");
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }
}
