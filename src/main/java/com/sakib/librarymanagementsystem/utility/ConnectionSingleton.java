package com.sakib.librarymanagementsystem.utility;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    private static final String DB_NAME = "library";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "root";
    private static final String DB_HOST = "localhost";
    private static final String DB_PASSWORD = "Sakib@#72542";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public static final ConnectionSingleton instance = new ConnectionSingleton();   // Singleton instance
    // Public method to get connection
    @Getter
    private static Connection connection;          // JDBC connection

    // Private constructor
    private ConnectionSingleton() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    // Public method to get singleton instance
//    public static ConnectionSingleton getInstance() {
//        if (instance == null) {
//            synchronized (ConnectionSingleton.class) {
//                if (instance == null) {
//                    instance = new ConnectionSingleton();
//                }
//            }
//        }
//        return instance;
//    }

}
