package Admin;

import java.sql.*;

public class AdminRepository {
    private static final String URL = "jdbc:sqlite:webbutiken1.db";

    public Admin findByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        }

        return null; // Om ingen admin hittas
    }
}
