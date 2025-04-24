package Admin;

import java.sql.*;
import java.util.ArrayList;

public class AdminRepository {
    private static final String URL = "jdbc:sqlite:webbutiken1.db";

    public ArrayList<Admin> getAll() throws SQLException {
        ArrayList<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admins";

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
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";

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

    public Admin addNewAdmin(String username, String password) throws SQLException {
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int adminId = generatedKeys.getInt(1);
                    return new Admin(adminId, username, password);
                } else {
                    throw new SQLException("⚠️ Skapandet av admin misslyckades – inget ID returnerat.");
                }
            }
        }
    }

}
