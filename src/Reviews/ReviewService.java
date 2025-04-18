package Reviews;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewService {

    private final ReviewRepository reviewRepository = new ReviewRepository();

    public boolean addReview(int customerId, int productId, int rating, String comment) throws SQLException {
        if (rating < 1 || rating > 5) {
            System.out.println("❌ Betyg måste vara mellan 1 och 5.");
            return false;
        }
        return reviewRepository.addReview(customerId, productId, rating, comment);
    }

    public double getAverageRating(int productId) throws SQLException {
        return reviewRepository.getAverageRating(productId);
    }

    public ArrayList<String> getReviewsByProduct(int productId) throws SQLException {
        return reviewRepository.getReviewsByProductId(productId);
    }
}
