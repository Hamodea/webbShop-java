package Admin;

import java.sql.*;
import java.util.ArrayList;

public class AdminRepository {
    private static final String URL = "jdbc:sqlite:webbutiken1.db";

    public ArrayList<Admin> getAll() throws SQLException {
        ArrayList<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admin";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                admins.add(admin);
            }
        }

        return admins;
    }

    public Admin findAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        }

        return null; // Om ingen admin hittas
    }
}
