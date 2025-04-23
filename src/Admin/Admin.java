package Admin;

import Auth.User;

public class Admin extends User {
    private final String username;
    private final String password;

    public Admin(int id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }

    @Override
    public void showUserType() {
        System.out.println("🛡️ Detta är en admin.");
    }

    @Override
    public String getDisplayName() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
