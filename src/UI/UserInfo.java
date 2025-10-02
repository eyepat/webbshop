package UI;

public class UserInfo {
    private final int id;
    private final String username;

    public UserInfo(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}