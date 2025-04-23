package Cart;

import Auth.SessionManager;
import Customers.Customer;
import Orders.Order;
import Orders.OrderProduct;
import Orders.OrderRepository;
import Products.ProductRepository;
import Products.Products;
import Auth.User;

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
            System.out.println("\n===== KUNDVAGN =====");
            System.out.println("1. Visa kundvagn");
            System.out.println("2. Lägg till produkt i kundvagn");
            System.out.println("3. Ta bort produkt");
            System.out.println("4. Ändra antal");
            System.out.println("5. Genomför köp");
            System.out.println("0. Tillbaka till huvudmeny");
            System.out.print("Ditt val: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> viewCart();
                case "2" -> addProduct();
                case "3" -> removeProduct();
                case "4" -> updateQuantity();
                case "5" -> checkout();
                case "0" -> { return; }
                default -> System.out.println("❌ Ogiltigt val.");
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

                // Workaround if setQuantity doesn't exist
                cart.remove(op);
                cart.add(new OrderProduct(op.getProduct(), newQuantity));
                System.out.println("✅ Antalet uppdaterat.");
                return;
            }
        }

        System.out.println("❌ Produkten finns inte i kundvagnen.");
    }

    private void checkout() throws SQLException {
        User user = SessionManager.getLoggedInUser();
        if (user == null || !(user instanceof Customers.Customer customer)) {
            System.out.println("❌ Du måste vara inloggad som kund för att genomföra köp.");
            return;
        }

        if (cart.isEmpty()) {
            System.out.println("❌ Kundvagnen är tom.");
            return;
        }

        Order order = new Order(customer.getCustomer_id(), cart);
        boolean success = orderRepository.placeOrder(order);

        if (success) {
            System.out.println("🛒 Order genomförd! Totalt pris: " + order.getTotalPrice() + " kr");
            cart.clear();
        } else {
            System.out.println("❌ Något gick fel vid beställningen.");
        }
    }
}
