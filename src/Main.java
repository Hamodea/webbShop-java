import Customers.CustomerController;
import Orders.OrderController;
import Products.ProductController;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(AsciiBanner.getArt());

        Scanner scanner = new Scanner(System.in);
        CustomerController customerController = new CustomerController();
        ProductController productController = new ProductController();
        OrderController orderController = new OrderController();

        MainMenu menu = new MainMenu(customerController, scanner, productController, orderController);
        menu.show();
    }
}
