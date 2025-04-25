package Orders;

import Products.Products;
import Products.ProductRepository;


import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {

    OrderRepository orderRepository = new OrderRepository();
    ProductRepository productRepository = new ProductRepository();

    public ArrayList<Order> getOrderHistory(int customerId)throws SQLException{
        return orderRepository.getOrderHistoryByCustomerId(customerId);
    }

    public int placeOrder(Order order) throws SQLException {
        return orderRepository.saveOrder(order);
    }

    public Products getProductById(int productId) throws SQLException {
        return productRepository.getProductById(productId);
    }

}
