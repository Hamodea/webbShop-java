package Cart;

import Auth.SessionManager;
import Orders.Order;
import Orders.OrderProduct;
import Orders.OrderRepository;
import Products.ProductRepository;
import Products.Products;
import Auth.User;
import static utils.AnsiColors.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CartController {
    private final Scanner scanner;
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final ArrayList<OrderProduct> cart = new ArrayList<>();

    public CartController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showCartMenu() throws SQLException {
        while (true) {
            System.out.println(BLUE + """
            **************************************
            ║           🛒KUNDVAGN               ║
            **************************************
            ║ 1: Visa kundvagn                   ║
            ║ 2: Lägg till produkt i kundvagn    ║
            ║ 3: Ta bort produkt                 ║
            ║ 4: Ändra antal                     ║
            ║ 5: Genomför köp                    ║
            ║ 0: Tillbaka till huvudmeny         ║
            **************************************
            """ + RESET);
            System.out.print(YELLOW + "Ditt val: " + RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> viewCart();
                case "2" -> addProduct();
                case "3" -> removeProduct();
                case "4" -> updateQuantity();
                case "5" -> checkout();
                case "0" -> { return; }
                default -> System.out.println(RED + "❌ Ogiltigt val." + RESET);
            }
        }
    }


    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Kundvagnen är tom.");
            return;
        }
        System.out.println("\nInnehåll i kundvagnen:");
        for (OrderProduct op : cart) {
            System.out.printf("- %s | Antal: %d | Pris/st: %.2f kr | Totalt: %.2f kr\n",
                    op.getProduct().getName(), op.getQuantity(), op.getProduct().getPrice(), op.getTotal());
        }
    }

    private void addProduct() throws SQLException {
        System.out.print("Ange produktnamn: ");
        String name = scanner.nextLine();

        Products product = productRepository.getProductByName(name);
        if (product == null) {
            System.out.println("❌ Produkten hittades inte.");
            return;
        }

        System.out.print("Ange antal: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if (quantity <= 0 || quantity > product.getStock()) {
            System.out.println("❌ Ogiltigt antal eller för stort antal i förhållande till lagersaldo.");
            return;
        }

        for (OrderProduct op : cart) {
            if (op.getProduct().getProduct_id() == product.getProduct_id()) {
                op.setQuantity(op.getQuantity() + quantity);
                System.out.println("✅ Antalet uppdaterades i kundvagnen.");
                return;
            }
        }

        cart.add(new OrderProduct(product, quantity));
        System.out.println("✅ Produkt tillagd i kundvagnen.");
    }

    private void removeProduct() {
        System.out.print("Ange produktnamn att ta bort: ");
        String name = scanner.nextLine();

        OrderProduct toRemove = null;
        for (OrderProduct op : cart) {
            if (op.getProduct().getName().equalsIgnoreCase(name)) {
                toRemove = op;
                break;
            }
        }

        if (toRemove != null) {
            cart.remove(toRemove);
            System.out.println("✅ Produkten togs bort från kundvagnen.");
        } else {
            System.out.println("❌ Produkten finns inte i kundvagnen.");
        }
    }

    private void updateQuantity() {
        System.out.print("Ange produktnamn: ");
        String name = scanner.nextLine();

        for (OrderProduct op : cart) {
            if (op.getProduct().getName().equalsIgnoreCase(name)) {
                System.out.print("Nytt antal: ");
                int newQuantity = Integer.parseInt(scanner.nextLine());

                if (newQuantity <= 0 || newQuantity > op.getProduct().getStock()) {
                    System.out.println("❌ Ogiltigt antal.");
                    return;
                }

                cart.remove(op);
                cart.add(new OrderProduct(op.getProduct(), newQuantity));
                System.out.println("✅ Antalet uppdaterat.");
                return;
            }
        }

        System.out.println("❌ Produkten finns inte i kundvagnen.");
    }

    private void checkout() throws SQLException {
        User user = SessionManager.getInstance().getLoggedInUser();
        if (!(user instanceof Customers.Customer customer)) {
            System.out.println("❌ Du måste vara inloggad som kund för att genomföra köp.");
            return;
        }

        if (cart.isEmpty()) {
            System.out.println("❌ Kundvagnen är tom.");
            return;
        }

        Order order = new Order(customer.getId(), cart);
        int orderId = orderRepository.saveOrder(order);

        if (orderId > 0) {
            System.out.println("🛒 Order genomförd! Order-ID: " + orderId);
            System.out.printf("💰 Totalt pris: %.2f kr\n", order.getTotalPrice());
            cart.clear();
        } else {
            System.out.println("❌ Något gick fel vid beställningen.");
        }
    }

}
