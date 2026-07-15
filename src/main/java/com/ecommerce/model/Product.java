package com.ecommerce.model;

import java.sql.Timestamp;

public class Product {
	private String productName;
	private int productId;
	private String description;
	private int price;
	private int stockQuantity;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Product() {
	}

	public Product(String productName, String description, int price, int stockQuantity) {
		super();
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	
	public Product(int productId, String productName, String description, int price, int stockQuantity,
			Timestamp createdAt, Timestamp updatedAt) {
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
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

}
