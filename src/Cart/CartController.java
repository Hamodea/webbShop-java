package Cart;

import Auth.SessionManager;
import Customers.Customer;
import Products.Products;

import java.util.ArrayList;
import java.util.Scanner;

public class CartController {
    private Scanner scanner = new Scanner(System.in);
    private final ArrayList<Products> cart = new ArrayList<>(); // Dummy cart list

    public CartController(Scanner scanner) {
        this.scanner =scanner;
    }

    public void showCartMenu() {
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
                default -> System.out.println("Ogiltigt val.");
            }
        }
    }

    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Kundvagnen är tom.");
            return;
        }
        System.out.println("\nInnehåll i kundvagnen:");
        for (Products p : cart) {
            System.out.printf("- %s (%d st)\n", p.getName(), p.getStock());
        }
    }

    private void addProduct() {
        System.out.println("(Exempelfunktion: lägg till en produkt)");
    }

    private void removeProduct() {
        System.out.println("(Exempelfunktion: ta bort en produkt)");
    }

    private void updateQuantity() {
        System.out.println("(Exempelfunktion: ändra antal)");
    }

    private void checkout() {
        Customer customer = SessionManager.getLoggedInCustomer();
        if (customer == null) {
            System.out.println("Du måste vara inloggad för att genomföra köp.");
            return;
        }
        System.out.println("(Exempelfunktion: omvandla till order för " + customer.getName() + ")");
    }
}
