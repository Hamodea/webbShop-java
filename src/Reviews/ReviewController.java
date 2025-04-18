package Reviews;

import Auth.SessionManager;
import Customers.Customer;
import java.sql.SQLException;
import java.util.Scanner;

public class ReviewController {
    private Scanner scanner = new Scanner(System.in);
    private final ReviewService reviewService = new ReviewService();

    // ANSI-färger
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    public ReviewController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showReviewMenu() throws SQLException {
        while (true) {
            System.out.println("\n=============================\nRECENSIONSMENY\n=============================");
            System.out.println("1. Lämna recension");
            System.out.println("2. Visa genomsnittligt betyg för en produkt");
            System.out.println("0. Tillbaka till huvudmeny");
            System.out.print(YELLOW + "Ditt val: " + RESET);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> leaveReview();
                case "2" -> showAverageRating();
                case "0" -> {
                    System.out.println(GREEN + "Tillbaka till huvudmeny..." + RESET);
                    return;
                }
                default -> System.out.println(RED + "Ogiltigt val. Försök igen." + RESET);
            }
        }
    }

    private void leaveReview() throws SQLException {
        Customer customer = SessionManager.getLoggedInCustomer();
        if (customer == null) {
            System.out.println(RED + "Du måste vara inloggad för att lämna en recension." + RESET);
            return;
        }

        System.out.print("Ange produktens ID: ");
        int productId = Integer.parseInt(scanner.nextLine());
        System.out.print("Ge betyg (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv en kommentar: ");
        String comment = scanner.nextLine();

        boolean success = reviewService.addReview(customer.getCustomer_id(), productId, rating, comment);

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
