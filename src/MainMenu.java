import Auth.SessionManager;
import Cart.CartController;
import Customers.Customer;
import Customers.CustomerController;
import Customers.CustomerLogin;
import Orders.OrderController;
import Products.ProductController;
import Reviews.ReviewController;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final CustomerController customerController;
    private final Scanner scanner;
    private final ProductController productController;
    private final CustomerLogin customerLogin;
    private final OrderController orderController;
    private final CartController cartController;
    private final ReviewController reviewController;

    // ANSI färgkoder
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";


    public MainMenu(CustomerController customerController, Scanner scanner, ProductController productController, CustomerLogin customerLogin, OrderController orderController, CartController cartController, ReviewController reviewController) {
        this.customerController = customerController;
        this.scanner = scanner;
        this.productController = productController;
        this.customerLogin = customerLogin;
        this.orderController = orderController;
        this.cartController = cartController;
        this.reviewController = reviewController;
    }



    public void show() throws SQLException {
        while (true) {
            System.out.println(BOLD + CYAN + "\n╔════════════════════════════════════╗");
            System.out.println("║           HUVUDMENY                ║");
            System.out.println("╠════════════════════════════════════╣");

            System.out.println("║ 1: Kundmeny                        ║");
            System.out.println("║ 2: Produktmeny                     ║");
            System.out.println("║ 3: Ordermeny                       ║");
            System.out.println("║ 4: Logga in                        ║");
            System.out.println("║ 5: Registrera ny kund              ║");
            if (SessionManager.isLoggedIn()) {
                System.out.println("║ 6: Visa kundvagn                   ║");
                System.out.println("║ 7: Recensioner                     ║");
                System.out.printf(GREEN +"║ Inloggad som: %-20s ║\n", SessionManager.getLoggedInCustomer().getName() + RESET);
            }

            System.out.println("║ 0: Avsluta programmet             ║");
            System.out.println("╚════════════════════════════════════╝" + RESET);

            System.out.print(YELLOW + "Ditt val: " + RESET);
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> customerController.customerMenu();
                case "2" -> productController.productMenu();
                case "3" -> orderController.orderMenu();
                case "4" -> {
                    Customer customer = customerLogin.login();
                    if (customer != null) {
                        SessionManager.login(customer);
                        System.out.println(GREEN + "✅ Inloggning lyckades!" + RESET);
                    } else {
                        System.out.println(RED + "❌ Inloggning misslyckades." + RESET);
                    }
                }
                case "5" -> customerController.createNewUser();
                case "6" -> {
                    if (SessionManager.isLoggedIn()) {
                        cartController.showCartMenu();
                    } else {
                        System.out.println(RED + "❌ Du måste vara inloggad för att se kundvagnen." + RESET);
                    }
                }
                case "7" -> {
                    if (SessionManager.isLoggedIn()) {
                        reviewController.showReviewMenu();
                    } else {
                        System.out.println(RED + "❌ Du måste vara inloggad för att se recensioner." + RESET);
                    }
                }
                case "0" -> {
                    System.out.println(RED + "🛑 Programmet avslutas..." + RESET);
                    return;
                }
                default -> System.out.println(RED + "❗ Ogiltigt val. Försök igen." + RESET);
            }
        }
    }

}
