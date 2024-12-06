package AutoServiceNew;

import java.sql.Connection;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            System.out.println("Успешное подключение!");
        } else {
            System.out.println("Подключение не удалось.");
        }
    }
}

