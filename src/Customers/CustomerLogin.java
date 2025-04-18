package Customers;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerLogin {
    private final Scanner scanner;
    private final CustomerService customerService = new CustomerService();

    public CustomerLogin(Scanner scanner) {
        this.scanner = scanner;
    }

    public Customer login() throws SQLException {
        System.out.println("🔐 Logga in som kund");
        System.out.print("📧 E-post: ");
        String email = scanner.nextLine();
        System.out.print("🔒 Lösenord: ");
        String password = scanner.nextLine();

        // Hämta alla kunder och matcha inmatning
        for (Customer c : customerService.getAllcustomers()) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                System.out.println("✅ Inloggning lyckades! Välkommen, " + c.getName());
                return c;
            }
        }

        System.out.println("❌ Fel e-post eller lösenord.");
        return null;
    }
}
