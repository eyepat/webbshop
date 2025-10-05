package Model.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance = null;
    private Connection con = null;

    private DBManager() { connect(); }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.0.4:3306/webshop" +
                            "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "webshop",
                    "StrongPass123!"
            );
        } catch (Exception e) {
            throw new RuntimeException("Kunde inte ansluta till databasen", e);
        }
    }

    private static DBManager getInstance() {
        if (instance == null) instance = new DBManager();
        return instance;
    }

    public static Connection getConnection() {
        try {
            DBManager db = getInstance();
            if (db.con == null || db.con.isClosed() || !db.con.isValid(2)) {
                db.connect();
            }
            return db.con;
        } catch (SQLException e) {
            throw new RuntimeException("Kunde inte kolla anslutningen", e);
        }
    }
}
