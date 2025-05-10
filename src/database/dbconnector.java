package src.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlite:taskmanager.db";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
