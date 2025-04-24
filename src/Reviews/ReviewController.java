package Reviews;

import Auth.SessionManager;
import Auth.User;
import Customers.Customer;
import java.sql.SQLException;
import java.util.Scanner;
import static utils.AnsiColors.*;

public class ReviewController {
    private final Scanner scanner;
    private final ReviewService reviewService = new ReviewService();



    public ReviewController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showReviewMenu() throws SQLException {
        while (true) {
            System.out.println(BLUE + """
            **************************************
            ║         RECENSIONSMENY             ║
            **************************************
            ║ 1: Lämna recension                 ║
            ║ 2: Visa genomsnittligt betyg       ║
            ║ 0: Tillbaka till huvudmeny         ║
            **************************************
            """ + RESET);
            System.out.print(YELLOW + "Ditt val: " + RESET);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> leaveReview();
                case "2" -> showAverageRating();
                case "0" -> {
                    System.out.println(GREEN + "🔙 Tillbaka till huvudmeny..." + RESET);
                    return;
                }
                default -> System.out.println(RED + "❗ Ogiltigt val. Försök igen." + RESET);
            }
        }
    }


    private void leaveReview() throws SQLException {
        User user = SessionManager.getInstance().getLoggedInUser();
        if (!(user instanceof Customer customer)) {
            System.out.println(RED + "❌ Endast kunder kan lämna recensioner." + RESET);
            return;
        }

        System.out.print("Ange produktens ID: ");
        int productId = Integer.parseInt(scanner.nextLine());

        System.out.print("Ge betyg (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());

        if (rating < 1 || rating > 5) {
            System.out.println(RED + "❌ Betyget måste vara mellan 1 och 5." + RESET);
            return;
        }

        System.out.print("Skriv en kommentar: ");
        String comment = scanner.nextLine();

        boolean success = reviewService.addReview(customer.getId(), productId, rating, comment);

        if (success) {
            System.out.println(GREEN + "✅ Recension sparad!" + RESET);
        } else {
            System.out.println(RED + "❌ Kunde inte spara recensionen." + RESET);
        }
    }


    private void showAverageRating() throws SQLException {
        System.out.print("Ange produktens ID: ");
        int productId = Integer.parseInt(scanner.nextLine());

        double average = reviewService.getAverageRating(productId);
        if (average > 0) {
            System.out.printf(GREEN + "⭐ Genomsnittligt betyg: %.2f" + RESET + "%n", average);
        } else {
            System.out.println(RED + "❌ Inga recensioner hittades för denna produkt." + RESET);
        }
    }
}
