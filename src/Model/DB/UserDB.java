package Model.DB;

import Model.BO.User;

import java.sql.*;

public class UserDB extends User {

    // SELECT
    public static User loadByUsername(String username) {
        String sql = "SELECT id, username, password_hash FROM T_USER WHERE username = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserDB(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password_hash")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ej hittad
    }

    // INSERT
    public static User create(String username, String passwordHash) {
        String sql = "INSERT INTO T_USER(username, password_hash) VALUES(?, ?)";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new UserDB(id, username, passwordHash);
                }
            }
        } catch (SQLIntegrityConstraintViolationException dup) {
            // UNIQUE(username) → redan upptaget
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // private ctor så endast denna klass kan skapa BO-instanser från DB-rader
    private UserDB(int id, String username, String passwordHash) {
        super(id, username, passwordHash);
    }
}
