import Customers.CustomerController;
import Orders.OrderController;
import Products.ProductController;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final CustomerController customerController;
    private final Scanner scanner;
    private final ProductController productController;

    private final OrderController orderController;

    // ANSI färgkoder
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public MainMenu(CustomerController customerController, Scanner scanner, ProductController productController, OrderController orderController) {
        this.customerController = customerController;
        this.scanner = scanner;
        this.productController = productController;
        this.orderController = orderController;
    }

    public void show() throws SQLException {
        while (true) {
            System.out.println(BOLD + CYAN + "╔════════════════════════════════════╗");
            System.out.println("║           HUVUDMENY                ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1" + CYAN + ": Kunder                          ║");
            System.out.println("║ 2" + CYAN + ": Produkter                       ║");
            System.out.println("║ 3" + CYAN + ": Ordrer                          ║");
            System.out.println("║ 0" + CYAN + ": Avsluta program                 ║");
            System.out.println("╚════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "Ditt val: " + RESET);

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    customerController.customerMenu();
                    break;
                case "2":
                    productController.productMenu();

                case "3":
                    orderController.orderMenu();
                    break;


                case "0":
                    System.out.println(RED + "Avslutar programmet..." + RESET);
                    return;
                default:
                    System.out.println(RED + "Ogiltigt val. Försök igen." + RESET);
            }
        }
    }
}
