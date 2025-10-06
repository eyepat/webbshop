package Model.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static volatile DBManager instance = null;
    private Connection con = null;

    private DBManager() { connect(); }

    // Läs env-var eller system property, annars default
    private static String env(String key, String def) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) v = System.getProperty(key);
        return (v == null || v.isBlank()) ? def : v;
    }

    private void connect() {
        try {

            String host = env("DB_HOST", "127.0.0.1");
            String port = env("DB_PORT", "3306");
            String db   = env("DB_NAME", "webshop");
            String user = env("DB_USER", "webshop");
            String pass = env("DB_PASS", "StrongPass123!");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + db
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
                    + "&useUnicode=true&characterEncoding=utf8"
                    + "&connectionCollation=utf8mb4_0900_ai_ci"
                    + "&connectTimeout=3000&socketTimeout=5000";

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("Kunde inte ansluta till databasen", e);
        }
    }

    private static DBManager getInstance() {
        DBManager i = instance;
        if (i == null) {
            synchronized (DBManager.class) {
                i = instance;
                if (i == null) instance = i = new DBManager();
            }
        }
        return i;
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
