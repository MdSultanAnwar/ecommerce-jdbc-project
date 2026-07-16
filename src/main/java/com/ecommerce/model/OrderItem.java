package com.ecommerce.model;

import java.sql.Timestamp;

public class OrderItem {

	private int orderItemId;
	private int orderId;
	private int productId;
	private int quantity;
	private Double price;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Not a DB column - populated via JOIN with products for display purposes
	// (viewOrderItems)
	private String productName;

	public OrderItem() {
	}

	public OrderItem(int orderId, int productId, int quantity, double price) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public double getLineTotal() {
		return price == null ? 0.0 : price*quantity;
	}

	@Override
	public String toString() {
		return String.format("  - %-20s x%-3d @ Rs.%-8s = Rs.%s", productName, quantity, price, getLineTotal());
	}
}
