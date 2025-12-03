package autoservice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String DB_URL = "autoservice.jdbc:postgresql://localhost:5432/autoservice";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = new Properties();
                props.setProperty("user", DB_USER);
                props.setProperty("password", DB_PASSWORD);
                props.setProperty("ssl", "false");

                connection = DriverManager.getConnection(DB_URL, props);
                System.out.println("Подключение к базе данных установлено");
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Подключение к базе данных закрыто");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
