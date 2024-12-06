package AutoServiceNew;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/autoservice";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Соединение с базой данных установлено.");
            } catch (SQLException e) {
                System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}

