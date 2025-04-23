import Auth.UserLogin;
import Cart.CartController;
import Customers.CustomerController;
import Orders.OrderController;
import Products.ProductController;
import Reviews.ReviewController;
import Utils.AsciiBanner;
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
        UserLogin userLogin= new UserLogin(scanner);

        MainMenu menu = new MainMenu(
                customerController,
                scanner,
                productController,
                userLogin,
                orderController,
                cartController,
                reviewController
        );


        menu.show();
    }
}
