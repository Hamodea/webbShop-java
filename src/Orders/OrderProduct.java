package Orders;

import Products.Products;

public class OrderProduct {
    private int orderProductId;
    private int orderId;
    private Products product;
    private int quantity;
    private double unitPrice;

    public OrderProduct(int orderProductId, int orderId, Products product, int quantity, double unitPrice) {
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderProduct(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    // Getters
    public int getOrderProductId() {
        return orderProductId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Products getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotal() {
        return quantity * unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
