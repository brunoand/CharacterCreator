package com.example.connectionmysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {

    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        if (connection==null || connection.isClosed())
        {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/mydb";
        String username="bruno";
        String password="123456";
        connection= DriverManager.getConnection(url,username, password);
    }
}
