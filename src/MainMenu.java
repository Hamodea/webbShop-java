import Auth.SessionManager;
import Auth.User;
import Cart.CartController;
import Customers.CustomerController;
import Auth.UserLogin;
import Orders.OrderController;
import Products.ProductController;
import Reviews.ReviewController;

import Utils.ExitBanner;

import java.sql.SQLException;
import java.util.Scanner;
import static utils.AnsiColors.*;

public class MainMenu {
    private final CustomerController customerController;
    private final Scanner scanner;
    private final ProductController productController;
    private final UserLogin userLogin;
    private final OrderController orderController;
    private final CartController cartController;
    private final ReviewController reviewController;



    public MainMenu(CustomerController customerController, Scanner scanner, ProductController productController,
                    UserLogin userLogin, OrderController orderController, CartController cartController,
                    ReviewController reviewController) {
        this.customerController = customerController;
        this.scanner = scanner;
        this.productController = productController;
        this.userLogin = userLogin;
        this.orderController = orderController;
        this.cartController = cartController;
        this.reviewController = reviewController;
    }

    public void show() throws SQLException {
        while (true) {
            printMainMenu();
            System.out.print(YELLOW + "Ditt val: " + RESET);
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> customerController.customerMenu();
                case "2" -> productController.productMenu();
                case "3" -> orderController.orderMenu();
                case "4" -> {
                    if (SessionManager.getInstance().isLoggedIn()) {
                        SessionManager.getInstance().logout();
                        System.out.println(GREEN + "✅ Du är nu utloggad." + RESET);
                    } else {
                        User user = userLogin.login();
                        if (user != null) {
                            SessionManager.getInstance().login(user);
                            System.out.println(GREEN + "✅ Inloggning lyckades!" + RESET);
                        } else {
                            System.out.println(RED + "❌ Inloggning misslyckades." + RESET);
                        }
                    }
                }
                case "5" -> customerController.createNewUser();
                case "6" -> {
                    if (SessionManager.getInstance().isLoggedIn()) {
                        cartController.showCartMenu();
                    } else {
                        System.out.println(RED + "❌ Du måste vara inloggad för att se kundvagnen." + RESET);
                    }
                }
                case "7" -> {
                    if (SessionManager.getInstance().isLoggedIn()) {
                        reviewController.showReviewMenu();
                    } else {
                        System.out.println(RED + "❌ Du måste vara inloggad för att se recensioner." + RESET);
                    }
                }
                case "0" -> {
                    System.out.println(RED + "🛑 Programmet avslutas..." + RESET);
                    System.out.println(GREEN + ExitBanner.endArt());

                    return;
                }
                default -> System.out.println(RED + "❗ Ogiltigt val. Försök igen." + RESET);
            }
        }
    }

    private void printMainMenu() {
        System.out.println(BOLD + CYAN + "\n╔════════════════════════════════════╗");
        System.out.println("║             HUVUDMENY              ║");
        System.out.println("╠════════════════════════════════════╣");

        System.out.println("║ 1: Kundmeny                        ║");
        System.out.println("║ 2: Produktmeny                     ║");
        System.out.println("║ 3: Ordermeny                       ║");
        System.out.println("║ 4: " + (SessionManager.getInstance().isLoggedIn() ? "Logga ut" : "Logga in") + "                        ║");
        System.out.println("║ 5: Registrera ny kund              ║");

        if (SessionManager.getInstance().isLoggedIn()) {
            printLoggedInExtras();
        }

        System.out.println(CYAN +"║ 0: Avsluta programmet              ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
    }

    private void printLoggedInExtras() {
        System.out.println("║ 6: Visa kundvagn                   ║");
        System.out.println("║ 7: Recensioner                     ║");
        System.out.println("╠════════════════════════════════════╣");

        User user = SessionManager.getInstance().getLoggedInUser();
        System.out.println(GREEN + "║ Inloggad som: " + user.getDisplayName() +  user.showUserType() +"        ║" + RESET);
        user.showUserType();


    }
}
