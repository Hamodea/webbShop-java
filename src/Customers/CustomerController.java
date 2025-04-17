package Customers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";
    private static final String BLUE = "\u001B[34m";

    private final CustomerServies customerServies = new CustomerServies();
    private final Scanner scanner = new Scanner(System.in);

    public void customerMenu() throws SQLException {
        while (true) {
            printMenu();
            String select = scanner.nextLine().trim();

            switch (select) {
                case "1" -> showAllCustomers();
                case "2" -> searchById();
                case "3" -> createNewUser();
                case "4" -> updateUserEmail();
                case "5" -> deleteUser();
                case "0" -> {
                    System.out.println(RED + "🔙 Återgår till huvudmeny..." + RESET);
                    return;
                }
                default -> System.out.println(RED + "❗ Ogiltigt val. Försök igen." + RESET);
            }
        }
    }

    private void printMenu() {
        System.out.println(BLUE + """
                **************************************
                ║            KUNDERMENY              ║
                **************************************
                ║ 1: Hämta alla kunder               ║
                ║ 2: Hämta kund efter ID             ║
                ║ 3: Lägg till ny kund               ║
                ║ 4: Uppdatera e-post                ║
                ║ 5: Ta bort kund                    ║
                ║ 0: Tillbaka till huvudmeny         ║
                **************************************""" + RESET);
        System.out.print(YELLOW + "Ditt val: " + RESET);
    }

    private void showAllCustomers() throws SQLException {
        ArrayList<Customer> customers = customerServies.getAllcustomers();
        if (customers.isEmpty()) {
            System.out.println(RED + "❌ Inga kunder hittades." + RESET);
        } else {
            for (Customer c : customers) {
                System.out.printf("🆔 %d | 👤 %s | 📧 %s%n",
                        c.getCustomer_id(), c.getName(), c.getEmail());
            }
        }
    }

    private void searchById() throws SQLException {
        System.out.print("Ange kund-ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Customer c = customerServies.getUserId(id);
        if (c != null) {
            System.out.printf(GREEN + "✅ Kund hittad: 🆔 %d | 👤 %s | 📧 %s%n" + RESET,
                    c.getCustomer_id(), c.getName(), c.getEmail());
        } else {
            System.out.println(RED + "❌ Ingen kund hittades med ID " + id + RESET);
        }
    }

    private void createNewUser() throws SQLException {
        System.out.print("👤 Namn: ");
        String name = scanner.nextLine();

        System.out.print("📧 E-post: ");
        String email = scanner.nextLine();

        System.out.print("🔐 Lösenord: ");
        String password = scanner.nextLine();

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            System.out.println(RED + "❗ Alla fält måste fyllas i." + RESET);
            return;
        }

        Customer customer = customerServies.addCustomer(name, email, password);
        if (customer != null) {
            System.out.println(GREEN + "✅ Ny kund skapad: " + customer.getName() + RESET);
        } else {
            System.out.println(RED + "❌ Något gick fel vid skapandet." + RESET);
        }
    }

    private void updateUserEmail() throws SQLException {
        System.out.print("Ange kund-ID: ");
        int userId = Integer.parseInt(scanner.nextLine());

        System.out.print("Ny e-post: ");
        String email = scanner.nextLine();

        boolean success = customerServies.updateEmail(userId, email);
        if (success) {
            System.out.println(GREEN + "✅ E-post uppdaterad för kund med ID " + userId + RESET);
        } else {
            System.out.println(RED + "❌ Uppdatering misslyckades." + RESET);
        }
    }

    private void deleteUser() throws SQLException {
        System.out.print("Ange ID för kund att ta bort: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean success = customerServies.deleteUser(id);
        if (success) {
            System.out.println(GREEN + "🗑️ Kund med ID " + id + " borttagen." + RESET);
        } else {
            System.out.println(RED + "❌ Kunde inte ta bort kunden." + RESET);
        }
    }
}
