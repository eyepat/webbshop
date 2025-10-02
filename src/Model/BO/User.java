package Model.BO;

import UI.UserInfo;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

public class User {
    private final int id;
    private final String username;
    private final String passwordHash; // format: sha256:<saltB64>:<hashB64>

    // protected så att Model.DB.UserDB (subklass) kan kalla super(...)
    protected User(int id, String username, String passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }

    // ===== API som Facade använder =====

    public static Optional<UserInfo> login(String username, String password) {
        if (!validInput(username, password)) return Optional.empty();
        User u = Model.DB.UserDB.loadByUsername(username.trim());
        if (u == null) return Optional.empty();
        if (!verifyPassword(password, u.passwordHash)) return Optional.empty();
        return Optional.of(new UserInfo(u.id, u.username));
    }

    public static Optional<UserInfo> signup(String username, String password) {
        if (!validInput(username, password)) return Optional.empty();
        String u = username.trim();
        if (Model.DB.UserDB.loadByUsername(u) != null) return Optional.empty(); // upptaget

        String stored = hashPassword(password); // sha256:<saltB64>:<hashB64>
        User created = Model.DB.UserDB.create(u, stored);
        if (created == null) return Optional.empty();
        return Optional.of(new UserInfo(created.id, created.username));
    }

    // ===== Lösenord (SHA-256 + salt) =====

    private static String hashPassword(String password) {
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            byte[] hash = sha256(concat(salt, password.getBytes("UTF-8")));
            return "sha256:" + Base64.getEncoder().encodeToString(salt) + ":" +
                    Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean verifyPassword(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            if (parts.length != 3 || !"sha256".equalsIgnoreCase(parts[0])) return false;
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expected = Base64.getDecoder().decode(parts[2]);
            byte[] actual = sha256(concat(salt, password.getBytes("UTF-8")));
            return constantTimeEquals(expected, actual);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] sha256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(data);
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] r = new byte[a.length + b.length];
        System.arraycopy(a, 0, r, 0, a.length);
        System.arraycopy(b, 0, r, a.length, b.length);
        return r;
    }

    private static boolean constantTimeEquals(byte[] x, byte[] y) {
        if (x.length != y.length) return false;
        int r = 0;
        for (int i = 0; i < x.length; i++) r |= (x[i] ^ y[i]);
        return r == 0;
    }

    private static boolean validInput(String u, String p) {
        if (u == null || p == null) return false;
        String uu = u.trim();
        return !uu.isEmpty() && p.length() >= 3;
    }
}
