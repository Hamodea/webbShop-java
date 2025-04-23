import Auth.MainMenu;
import Cart.CartController;
import Customers.CustomerController;
import Customers.CustomerLogin;
import Orders.OrderController;
import Products.ProductController;
import Reviews.ReviewController;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(AsciiBanner.getArt());

        Scanner scanner = new Scanner(System.in);

        CustomerController customerController = new CustomerController(scanner);
        ProductController productController = new ProductController(scanner);
        OrderController orderController = new OrderController(scanner);
        ReviewController reviewController = new ReviewController(scanner);
        CartController cartController = new CartController(scanner);
        CustomerLogin customerLogin = new CustomerLogin(scanner);

        MainMenu menu = new MainMenu(
                customerController,
                scanner,
                productController,
                customerLogin,
                orderController,
                cartController,
                reviewController
        );


        menu.show();
    }
}
