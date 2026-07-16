package com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.ecommerce.model.Cart;

public class CartTest
{

	@Test
	public void testDefaultConstructor()
	{

		Cart cart = new Cart();

		assertNotNull(cart);

		assertEquals(0, cart.getCartId());
		assertEquals(0, cart.getCustomerId());
		assertEquals(0, cart.getProductId());
		assertEquals(0, cart.getQuantity());

	}

	@Test
	public void testParameterizedConstructor()
	{

		Cart cart = new Cart(1, 1001, 2);

		assertEquals(1, cart.getCustomerId());
		assertEquals(1001, cart.getProductId());
		assertEquals(2, cart.getQuantity());

	}

	@Test
	public void testSettersAndGetters()
	{

		Cart cart = new Cart();

		Timestamp time = new Timestamp(System.currentTimeMillis());

		cart.setCartId(10);
		cart.setCustomerId(1);
		cart.setProductId(1001);
		cart.setQuantity(5);
		cart.setCreatedAt(time);
		cart.setUpdatedAt(time);
		cart.setProductName("Laptop");
		cart.setPrice(55000);

		assertEquals(10, cart.getCartId());
		assertEquals(1, cart.getCustomerId());
		assertEquals(1001, cart.getProductId());
		assertEquals(5, cart.getQuantity());
		assertEquals(time, cart.getCreatedAt());
		assertEquals(time, cart.getUpdatedAt());
		assertEquals("Laptop", cart.getProductName());
		assertEquals(55000, cart.getPrice());

	}

	@Test
	public void testLineTotal()
	{

		Cart cart = new Cart();

		cart.setQuantity(3);
		cart.setPrice(1000);

		double total = cart.getLineTotal();

		assertEquals(3000, total);

	}

	@Test
	public void testLineTotalWithZeroQuantity()
	{

		Cart cart = new Cart();

		cart.setQuantity(0);
		cart.setPrice(500);

		assertEquals(0, cart.getLineTotal());

	}

	@Test
	public void testToString()
	{

		Cart cart = new Cart();

		cart.setCartId(1);
		cart.setProductName("Laptop");
		cart.setQuantity(2);
		cart.setPrice(55000);

		String result = cart.toString();

		assertTrue(result.contains("Laptop"));
		assertTrue(result.contains("qty: 2"));
		assertTrue(result.contains("55000"));
		assertTrue(result.contains("110000"));

	}
}