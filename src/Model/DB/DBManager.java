package Model.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance = null;
    private Connection con = null;

    private DBManager() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/webshop?useSSL=false&serverTimezone=UTC",
                    "webshop",
                    "StrongPass123!"
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kunde inte ansluta till databasen", e);
        }
    }

    private static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            if (getInstance().con == null || getInstance().con.isClosed()) {
                getInstance().connect();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kunde inte kolla anslutningen", e);
        }
        return getInstance().con;
    }
}