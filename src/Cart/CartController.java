package Cart;

import Auth.SessionManager;
import Customers.Customer;
import Products.ProductService;
import Products.Products;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CartController {
    private final Scanner scanner;
    private final ArrayList<CartItem> cart = new ArrayList<>();
    private final ProductService productService = new ProductService();

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
                case "0" -> {
                    return;
                }
                default -> System.out.println("❌ Ogiltigt val.");
            }
        }
    }

    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Kundvagnen är tom.");
            return;
        }

        System.out.println("\n🛒 Innehåll i kundvagnen:");
        for (CartItem item : cart) {
            Products p = item.getProduct();
            System.out.printf("- %s | Antal: %d | Pris/st: %.2f kr | Totalt: %.2f kr\n",
                    p.getName(), item.getQuantity(), p.getPrice(), item.getTotalPrice());
        }
    }

    private void addProduct() throws SQLException {
        System.out.print("Ange produktnamn: ");
        String name = scanner.nextLine();

        Products product = productService.findProductByName(name);
        if (product == null) {
            System.out.println("❌ Produkt hittades inte.");
            return;
        }

        System.out.print("Ange antal: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (quantity <= 0 || quantity > product.getStock()) {
            System.out.println("❌ Ogiltigt antal eller mer än lagersaldo.");
            return;
        }

        for (CartItem item : cart) {
            if (item.getProduct().getProduct_id() == product.getProduct_id()) {
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("✅ Antalet har uppdaterats.");
                return;
            }
        }

        cart.add(new CartItem(product, quantity));
        System.out.println("✅ Produkten har lagts till i kundvagnen.");
    }

    private void removeProduct() {
        System.out.print("Ange produktnamn att ta bort: ");
        String name = scanner.nextLine();

        cart.removeIf(item -> item.getProduct().getName().equalsIgnoreCase(name));
        System.out.println("🗑️ Produkten har tagits bort (om den fanns).");
    }

    private void updateQuantity() {
        System.out.print("Ange produktnamn för att ändra antal: ");
        String name = scanner.nextLine();

        for (CartItem item : cart) {
            if (item.getProduct().getName().equalsIgnoreCase(name)) {
                System.out.print("Ange nytt antal: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                if (quantity <= 0 || quantity > item.getProduct().getStock()) {
                    System.out.println("❌ Ogiltigt antal.");
                    return;
                }

                item.setQuantity(quantity);
                System.out.println("✔️ Antal uppdaterat.");
                return;
            }
        }

        System.out.println("❌ Produkten finns inte i kundvagnen.");
    }

    private void checkout() {
        Customer customer = SessionManager.getLoggedInCustomer();
        if (customer == null) {
            System.out.println("🔒 Du måste logga in för att kunna genomföra köp.");
            return;
        }

        if (cart.isEmpty()) {
            System.out.println("🛒 Din kundvagn är tom.");
            return;
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }

        System.out.printf("💰 Totalt belopp att betala: %.2f kr\n", total);
        System.out.println("✅ Order skapad! (simulerad)");
        cart.clear();
    }
}
