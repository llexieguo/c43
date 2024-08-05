package cs.toronto.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class databaseManager {
    private static Connection connection;

    public static void connect() {
        String url = "jdbc:postgresql://127.0.0.1:5432/mydb";
        String dbUsername = "postgres";
        String dbPassword = "postgres";

        try {
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
