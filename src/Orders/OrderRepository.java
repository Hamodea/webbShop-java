package Orders;

import Products.Products;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderRepository {

    public static final String URL = "jdbc:sqlite:webbutiken1.db";

    public ArrayList<Order> getOrderHistoryByCustomerId(int customerId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = """
                    SELECT o.order_id, o.order_date,
                           p.product_id, p.name, op.quantity, op.unit_price
                    FROM orders o
                    JOIN orders_products op ON o.order_id = op.order_id
                    JOIN products p ON op.product_id = p.product_id
                    WHERE o.customer_id = ?
                    ORDER BY o.order_id;
                """;
        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            int lastOrderId = -1;
            Order currentOrder = null;

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                if (orderId != lastOrderId) {
                    // Ny order → skapa ny Order-instans
                    Timestamp timestamp = rs.getTimestamp("order_date");
                    LocalDateTime orderDate = timestamp.toLocalDateTime();

                    currentOrder = new Order(orderId, customerId, orderDate, new ArrayList<>());
                    orders.add(currentOrder);
                    lastOrderId = orderId;
                }

                // Skapa produkt
                Products product = new Products(
                        rs.getInt("product_id"),
                        0,
                        rs.getString("name"),
                        "",
                        rs.getDouble("unit_price"),
                        0
                );

                // Skapa OrderProduct och lägg till i aktuell order
                int quantity = rs.getInt("quantity");
                OrderProduct orderProduct = new OrderProduct(product, quantity);

                currentOrder.getItems().add(orderProduct);
            }
        }
        return orders;

    }

    public boolean placeOrder(Order order) throws SQLException {
        String insertOrderSql = "INSERT INTO orders (customer_id) VALUES (?)";
        String insertOrderProductSql = """
        INSERT INTO orders_products (order_id, product_id, quantity, unit_price)
        VALUES (?, ?, ?, ?)
    """;

        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.setAutoCommit(false); // Starta transaktion

            try (
                    PreparedStatement orderStmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement productStmt = conn.prepareStatement(insertOrderProductSql)
            ) {
                // 1. Spara order
                orderStmt.setInt(1, order.getCustomerId());
                orderStmt.executeUpdate();

                ResultSet rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // 2. Spara varje produkt i orders_products
                    for (OrderProduct item : order.getItems()) {
                        productStmt.setInt(1, orderId);
                        productStmt.setInt(2, item.getProduct().getProduct_id());
                        productStmt.setInt(3, item.getQuantity());
                        productStmt.setDouble(4, item.getUnitPrice());
                        productStmt.addBatch();
                    }

                    productStmt.executeBatch(); // Skicka allt till databasen
                    conn.commit();
                    System.out.println("✅ Ordern har sparats med ID: " + orderId);
                } else {
                    conn.rollback();
                    throw new SQLException("❌ Kunde inte generera order_id.");
                }

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
        return false;
    }
}
