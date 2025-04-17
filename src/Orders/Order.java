package Orders;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private final int customerId;
    private final LocalDateTime orderDate;
    private List<OrderProduct> items;

    // Konstruktor för att läsa från databasen
    public Order(int orderId, int customerId, LocalDateTime orderDate, List<OrderProduct> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.items = items;
    }

    // Konstruktor för att skapa ny order
    public Order(int customerId, List<OrderProduct> items) {
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderProduct> getItems() {
        return items;
    }

    public void setItems(List<OrderProduct> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderProduct item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
