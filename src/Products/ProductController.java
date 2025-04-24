package Products;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import static utils.AnsiColors.*;

public class ProductController {

    ProductService productService = new ProductService();
    Scanner scanner;

    public ProductController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void productMenu() {
        while (true) {
            printMenu();
            String select = scanner.nextLine();

            try {
                switch (select) {
                    case "1" -> showAllProducts();
                    case "2" -> searchByName();
                    case "3" -> searchByCategory();
                    case "4" -> updatePrice();
                    case "5" -> updateStock();
                    case "6" -> addNewProduct();
                    case "7" -> filterByMaxPrice();
                    case "0" -> {
                        System.out.println("🔙 Återgår till huvudmeny.");
                        return;
                    }
                    default -> System.out.println(RED + "❗ Ogiltigt menyval." + RESET);
                }
            } catch (SQLException e) {
                System.out.println(RED + "❌ Databasfel: " + e.getMessage() + RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Ogiltigt nummerformat." + RESET);
            }
        }
    }

    private void printMenu() {
        System.out.println(BLUE + """
            **************************************
            ║          📦PRODUKTMENY             ║
            **************************************
            ║ 1: Visa alla produkter             ║
            ║ 2: Sök produkt efter namn          ║
            ║ 3: Sök produkt efter kategori      ║
            ║ 4: Uppdatera pris                  ║
            ║ 5: Uppdatera lagersaldo            ║
            ║ 6: Lägg till ny produkt            ║
            ║ 7: Filtrera produkter (maxpris)    ║
            ║ 0: Tillbaka till huvudmeny         ║
            **************************************
            """ + RESET);
        System.out.print(YELLOW + "Ditt val: " + RESET);
    }

    private void showAllProducts() throws SQLException {
        ArrayList<Products> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println(RED + "❌ Inga produkter hittades." + RESET);
            return;
        }
        for (Products p : products) {
            System.out.printf("🆔 %d | 📦 %s | 💰 %.2f kr | Lager: %d st\n",
                    p.getProduct_id(), p.getName(), p.getPrice(), p.getStock());
        }
    }

    private void searchByName() throws SQLException {
        System.out.print("Ange produktnamn att söka: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            System.out.println(RED + "❌ Du måste ange ett produktnamn." + RESET);
            return;
        }
        Products product = productService.findProductByName(name);
        if (product != null) {
            System.out.println("✅ Produkt hittad:");
            System.out.println("Namn: " + product.getName());
            System.out.println("Pris: " + product.getPrice());
            System.out.println("Beskrivning: " + product.getDescription());
        } else {
            System.out.println("❌ Produkten hittades inte.");
        }
    }

    private void searchByCategory() throws SQLException {
        System.out.print("Ange kategorinamn: ");
        String category = scanner.nextLine().trim();
        if (category.isBlank()) {
            System.out.println(RED + "❌ Du måste ange en kategori." + RESET);
            return;
        }
        ArrayList<Products> products = productService.findProductByCategory(category);
        if (products.isEmpty()) {
            System.out.println("❌ Inga produkter hittades i kategorin.");
        } else {
            for (Products p : products) {
                System.out.printf("- %s (%.2f kr)\n", p.getName(), p.getPrice());
            }
        }
    }

    private void updatePrice() throws SQLException {
        System.out.print("Ange produkt-ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Products product = productService.getProduct(id);
        if (product == null) {
            System.out.println("❌ Produkten hittades inte.");
            return;
        }
        System.out.println("Produkt: " + product.getName());
        System.out.println("Nuvarande pris: " + product.getPrice());
        System.out.print("Ange nytt pris: ");
        double newPrice = Double.parseDouble(scanner.nextLine());

        if (newPrice < 0) {
            System.out.println("❌ Pris kan inte vara negativt.");
            return;
        }

        boolean updated = productService.updatePrice(id, newPrice);
        System.out.println(updated ? "✔ Pris uppdaterat till " + newPrice : "❌ Uppdateringen misslyckades.");
    }

    private void updateStock() throws SQLException {
        System.out.print("Ange produkt-ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Products product = productService.getProduct(id);
        if (product == null) {
            System.out.println("❌ Produkten hittades inte.");
            return;
        }
        System.out.println("Produkt: " + product.getName());
        System.out.println("Nuvarande lagersaldo: " + product.getStock());
        System.out.print("Ange nytt lagersaldo: ");
        int newStock = Integer.parseInt(scanner.nextLine());

        if (newStock < 0) {
            System.out.println("❌ Lagersaldo kan inte vara negativt.");
            return;
        }

        boolean updated = productService.updateStock(id, newStock);
        System.out.println(updated ? "✔ Lagersaldo uppdaterat till " + newStock : "❌ Uppdateringen misslyckades.");
    }

    private void addNewProduct() throws SQLException {
        System.out.print("Tillverkare-ID: ");
        int manufacturerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Produktnamn: ");
        String name = scanner.nextLine().trim();

        System.out.print("Beskrivning: ");
        String description = scanner.nextLine().trim();

        System.out.print("Pris: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Lagersaldo: ");
        int stock = Integer.parseInt(scanner.nextLine());

        if (name.isBlank() || description.isBlank()) {
            System.out.println("❌ Namn och beskrivning får inte vara tomma.");
            return;
        }
        if (price < 0 || stock < 0) {
            System.out.println("❌ Pris och lagersaldo måste vara positiva.");
            return;
        }

        Products product = productService.addProduct(manufacturerId, name, description, price, stock);

        if (product != null) {
            System.out.println("✔ Produkt tillagd!");
            System.out.printf("🆔 %d | 📦 %s | 💰 %.2f kr | Lager: %d st\n",
                    product.getProduct_id(), product.getName(), product.getPrice(), product.getStock());
        } else {
            System.out.println("❌ Kunde inte lägga till produkten.");
        }
    }

    private void filterByMaxPrice() throws SQLException {
        System.out.print("Ange maximalt pris: ");
        double maxPrice = Double.parseDouble(scanner.nextLine());

        ArrayList<Products> products = productService.getAllProducts();

        ArrayList<Products> filtered = products.stream()
                .filter(p -> p.getPrice() <= maxPrice)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        if (filtered.isEmpty()) {
            System.out.println("❌ Inga produkter hittades under angivet pris.");
        } else {
            filtered.forEach(p ->
                    System.out.printf("🆔 %d | 📦 %s | 💰 %.2f kr | Lager: %d st%n",
                            p.getProduct_id(), p.getName(), p.getPrice(), p.getStock())
            );
        }
    }
}
