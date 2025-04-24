package Customers;

import static utils.AnsiColors.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {


    private final CustomerService customerService = new CustomerService();
    private final Scanner scanner;

    public CustomerController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void customerMenu() {
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

    private void showAllCustomers() {
        try {
            ArrayList<Customer> customers = customerService.getAllcustomers();
            if (customers.isEmpty()) {
                System.out.println(RED + "❌ Inga kunder hittades." + RESET);
            } else {
                for (Customer c : customers) {
                    System.out.printf("🆔 %d | 👤 %s | 📧 %s%n",
                            c.getId(), c.getDisplayName(), c.getEmail());
                }
            }
        } catch (SQLException e) {
            System.out.println(RED + "❌ Fel vid hämtning av kunder." + RESET);
            System.err.println("[SQL ERROR] " + e.getMessage());
        }
    }
    // find a customer by ID
    private void searchById() {
        try {
            System.out.print("Ange kund-ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Customer c = customerService.getUserId(id);
            if (c != null) {
                System.out.printf(GREEN + "✅ Kund hittad: 🆔 %d | 👤 %s | 📧 %s%n" + RESET,
                        c.getId(), c.getDisplayName(), c.getEmail());
            } else {
                System.out.println(RED + "❌ Ingen kund hittades med ID " + id + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "❌ Ange ett giltigt nummer." + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "❌ Fel vid sökning efter kund." + RESET);
            System.err.println("[SQL ERROR] " + e.getMessage());
        }
    }
    // Create a new  User
    public void createNewUser() {
        try {
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

            Customer customer = customerService.addCustomer(name, email, password);
            if (customer != null) {
                System.out.println(GREEN + "✅ Ny kund skapad: " + customer.getDisplayName() + RESET);
            } else {
                System.out.println(RED + "❌ Något gick fel vid skapandet." + RESET);
            }
        } catch (SQLException e) {
            System.out.println(RED + "❌ Det gick inte att skapa kunden." + RESET);
            System.err.println("[SQL ERROR] " + e.getMessage());
        }
    }

    // update User Eamil
    private void updateUserEmail() {
        try {
            System.out.println("Ange Kund-Id");
            int userId = Integer.parseInt(scanner.nextLine());

            System.out.println("Ange Ny e-post");
            String newEmail = scanner.nextLine();

            boolean success = customerService.updateEmail(userId, newEmail);
            if (success){
                System.out.println(GREEN + "✅ E-post uppdaterad för kund med ID " + userId + RESET);
            }else {
                System.out.println(RED + "❌ Uppdatering misslyckades." + RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "❌ Ange ett giltigt ID." + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "❌ Fel vid uppdatering av e-post." + RESET);
            System.err.println("[SQL ERROR] " + e.getMessage());
        }
    }

    // Delete a User
    private void deleteUser() {
        try {
            System.out.print("Ange ID för kund att ta bort: ");
            int id = Integer.parseInt(scanner.nextLine());

            boolean success = customerService.deleteUser(id);
            if (success) {
                System.out.println(GREEN + "🗑️ Kund med ID " + id + " borttagen." + RESET);
            } else {
                System.out.println(RED + "❌ Kunde inte ta bort kunden." + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "❌ Ange ett giltigt ID." + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "❌ Fel vid borttagning av kund." + RESET);
            System.err.println("[SQL ERROR] " + e.getMessage());
        }
    }
}
