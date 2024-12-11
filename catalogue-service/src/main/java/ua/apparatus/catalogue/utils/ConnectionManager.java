package ua.apparatus.catalogue.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL_KEY = "url";
    private static final String USERNAME_KEY = "username";
    private  static final String PASSWORD_KEY = "password";  // ctrl+shift+u - TO UPPERCASE

    private ConnectionManager() {
    }

    public static Connection open() throws SQLException {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
