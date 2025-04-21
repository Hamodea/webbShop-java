package Admin;

import Auth.User;


public class Admin extends User {

    private String role; // valfritt, om du vill särskilja olika admins

    public Admin(int id, String name, String email) {
        super(id, name, email);
        this.role = "Admin"; // standardroll
    }

    @Override
    public void showUserType() {
        System.out.println("🛡️ Detta är en admin.");
    }

    public String getRole() {
        return role;
    }
}
