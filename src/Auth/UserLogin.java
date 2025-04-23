package Auth;

import Admin.Admin;
import Admin.AdminService;
import Customers.Customer;
import Customers.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {
    private final Scanner scanner;
    private final CustomerService customerService = new CustomerService();
    private final AdminService adminService = new AdminService();

    public UserLogin(Scanner scanner) {
        this.scanner = scanner;
    }

    public User login() throws SQLException {
        System.out.println("🔐 Logga in");
        System.out.print("📧 E-post (för kunder) eller användarnamn (för admin): ");
        String identifier = scanner.nextLine();
        System.out.print("🔒 Lösenord: ");
        String password = scanner.nextLine();

        // Försök logga in som kund
        for (Customer c : customerService.getAllcustomers()) {
            if (c.getEmail().equalsIgnoreCase(identifier) && c.getPassword().equals(password)) {
                System.out.println("✅ Inloggning lyckades! Välkommen, " + c.getDisplayName());
                return c;
            }
        }

        // Försök logga in som admin
        for (Admin a : adminService.getAllAdmins()) {
            if (a.getUsername().equalsIgnoreCase(identifier) && a.getPassword().equals(password)) {
                System.out.println("✅ Inloggning som admin lyckades! Välkommen, " + a.getDisplayName());
                return a;
            }
        }

        System.out.println("❌ Fel användarnamn/e-post eller lösenord.");
        return null;
    }
}
