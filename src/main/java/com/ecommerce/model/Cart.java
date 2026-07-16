package com.ecommerce.model;


import java.sql.Timestamp;

public class Cart {

    private int cartId;
    private int customerId;
    private int productId;
    private int quantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Not DB columns - populated via JOIN with products for display purposes (viewCart)
    private String productName;
    private double price;

    public Cart() {
    }

    public Cart(int customerId, int productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLineTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("[cartId=%d] %-20s | qty: %d | Rs.%s each | subtotal: Rs.%s",
                cartId, productName, quantity, price, getLineTotal());
    }
}
