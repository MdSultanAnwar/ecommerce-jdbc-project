package com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import com.ecommerce.model.Product;

public class ProductTest
{

	@Test
	public void testDefaultConstructorAndSettersGetters()
	{
		Product p = new Product();

		Timestamp created = new Timestamp(System.currentTimeMillis());
		Timestamp updated = new Timestamp(System.currentTimeMillis());

		p.setProductId(1);
		p.setProductName("Laptop");
		p.setDescription("Dell Laptop");
		p.setPrice(50000);
		p.setStockQuantity(10);
		p.setCreatedAt(created);
		p.setUpdatedAt(updated);

		assertEquals(1, p.getProductId());
		assertEquals("Laptop", p.getProductName());
		assertEquals("Dell Laptop", p.getDescription());
		assertEquals(50000, p.getPrice(), 0.01);
		assertEquals(10, p.getStockQuantity());
		assertEquals(created, p.getCreatedAt());
		assertEquals(updated, p.getUpdatedAt());
	}

	@Test
	public void testParameterizedConstructor()
	{
		Product p = new Product("Mobile", "Samsung", 25000, 5);

		assertEquals("Mobile", p.getProductName());
		assertEquals("Samsung", p.getDescription());
		assertEquals(25000, p.getPrice(), 0.01);
		assertEquals(5, p.getStockQuantity());
	}

	@Test
	public void testFullConstructor()
	{
		Timestamp created = new Timestamp(System.currentTimeMillis());
		Timestamp updated = new Timestamp(System.currentTimeMillis());

		Product p = new Product(1, "Laptop", "Gaming Laptop", 75000, 8, created, updated);

		assertEquals(1, p.getProductId());
		assertEquals("Laptop", p.getProductName());
		assertEquals("Gaming Laptop", p.getDescription());
		assertEquals(75000, p.getPrice(), 0.01);
		assertEquals(8, p.getStockQuantity());
		assertEquals(created, p.getCreatedAt());
		assertEquals(updated, p.getUpdatedAt());
	}

	@Test
	public void testToString()
	{
		Product p = new Product();

		p.setProductId(1);
		p.setProductName("Laptop");
		p.setDescription("Dell");
		p.setPrice(50000);
		p.setStockQuantity(10);

		String result = p.toString();

		assertNotNull(result);
		assertTrue(result.contains("Laptop"));
	}
}