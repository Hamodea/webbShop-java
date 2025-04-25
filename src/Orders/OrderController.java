package Orders;

import Customers.Customer;
import Products.Products;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import static utils.AnsiColors.*;

public class OrderController {



    OrderRepository orderRepository = new OrderRepository();
    OrderService orderService = new OrderService();
    Scanner scanner ;

    public OrderController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void orderMenu() throws SQLException {
        String choice;

        while (true) {
            printOrderMenu();
            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Ange ditt kund-ID: ");
                    int customerId = Integer.parseInt(scanner.nextLine());
                    showOrderHistory(customerId);
                }
                case "2" -> createNewOrder();
                case "0" -> {
                    System.out.println(RED + "🔙 Tillbaka till huvudmeny." + RESET);
                    return;
                }
                default -> System.out.println(RED + "❌ Ogiltigt val. Försök igen." + RESET);
            }
        }
    }

    private void printOrderMenu() {
        System.out.println(BOLD + CYAN + "╔════════════════════════════════════╗");
        System.out.println("║             ORDERMENY              ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ 1: Visa orderhistorik              ║");
        System.out.println("║ 2: Lägg ny order                   ║");
        System.out.println("║ 0: Tillbaka till huvudmeny         ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.print(YELLOW + "Ditt val: " + RESET);
    }

    private void showOrderHistory(int customerId) throws SQLException {
        ArrayList<Order> orders = orderService.getOrderHistory(customerId);

        if (orders.isEmpty()) {
            System.out.println(RED + "❌ Ingen orderhistorik hittades för kund-ID " + customerId + "." + RESET);
            return;
        }

        for (Order o : orders) {
            System.out.println(GREEN + "\n🧾 Order ID: " + o.getOrderId() + " | Datum: " + o.getOrderDate() + RESET);
            for (OrderProduct item : o.getItems()) {
                System.out.printf("   - %s (%d st) à %.2f kr\n",
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice());
            }
            System.out.printf("   💵 Totalt: %.2f kr\n", o.getTotalPrice());
            System.out.println("-".repeat(40));
        }
    }

    private void createNewOrder() throws SQLException {
        ArrayList<OrderProduct> orderItems = new ArrayList<>();

        System.out.print("Ange kundens ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        while (true) {
            System.out.print("Ange produkt-ID (eller 0 för att avsluta): ");
            int productId = Integer.parseInt(scanner.nextLine());

            if (productId == 0) break;

            System.out.print("Ange antal: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Products product = orderService.getProductById(productId);
            if (product == null) {
                System.out.println("❌ Produkten hittades inte.");
                continue;
            }

            if (quantity > product.getStock()) {
                System.out.println("❌ Inte tillräckligt i lager. Tillgängligt: " + product.getStock());
                continue;
            }

            OrderProduct orderProduct = new OrderProduct(product, quantity);
            orderItems.add(orderProduct);
        }

        if (orderItems.isEmpty()) {
            System.out.println("⚠️ Ingen produkt valdes. Order avbröts.");
            return;
        }

        Order order = new Order(customerId, orderItems);
        int orderId = orderService.placeOrder(order); //

        if (orderId > 0) {
            System.out.println("✅ Order har lagts med ID: " + orderId);
            System.out.printf("💰 Totalsumma: %.2f kr\n", order.getTotalPrice());
        } else {
            System.out.println("❌ Något gick fel vid orderläggning.");
        }
    }








}
