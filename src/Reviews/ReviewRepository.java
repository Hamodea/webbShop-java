package Reviews;

import java.sql.*;
import java.util.ArrayList;

public class ReviewRepository {
    private static final String URL = "jdbc:sqlite:webbutiken1.db";

    public boolean addReview(int customerId, int productId, int rating, String comment) throws SQLException {
        String sql = "INSERT INTO reviews (customer_id, product_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, rating);
            pstmt.setString(4, comment);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public double getAverageRating(int productId) throws SQLException {
        String sql = "SELECT AVG(rating) as avg_rating FROM reviews WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
            return 0;
        }
    }

    public ArrayList<String> getReviewsByProductId(int productId) throws SQLException {
        ArrayList<String> reviews = new ArrayList<>();
        String sql = "SELECT r.rating, r.comment, c.name FROM reviews r JOIN customers c ON r.customer_id = c.customer_id WHERE r.product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String review = String.format("⭐ %d - %s (av %s)",
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getString("name"));
                reviews.add(review);
            }
        }
        return reviews;
    }
}
