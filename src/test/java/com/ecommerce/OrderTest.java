package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.ecommerce.model.Order;

public class OrderTest
{

	@Test
	public void testDefaultConstructorAndGettersSetters()
	{
		Order order = new Order();

		Timestamp time = new Timestamp(System.currentTimeMillis());

		order.setOrderId(1);
		order.setCustomerId(101);
		order.setTotalAmount(2500.50);
		order.setOrderDate(time);
		order.setOrderStatus("Placed");
		order.setCreatedAt(time);
		order.setUpdatedAt(time);

		assertEquals(1, order.getOrderId());
		assertEquals(101, order.getCustomerId());
		assertEquals(2500.50, order.getTotalAmount());
		assertEquals(time, order.getOrderDate());
		assertEquals("Placed", order.getOrderStatus());
		assertEquals(time, order.getCreatedAt());
		assertEquals(time, order.getUpdatedAt());
	}

	@Test
	public void testParameterizedConstructor()
	{
		Order order = new Order(101, 2500.50, "Placed");

		assertEquals(101, order.getCustomerId());
		assertEquals(2500.50, order.getTotalAmount());
		assertEquals("Placed", order.getOrderStatus());
	}

	@Test
	public void testToString()
	{
		Order order = new Order();

		Timestamp time = new Timestamp(System.currentTimeMillis());

		order.setOrderId(1);
		order.setOrderDate(time);
		order.setOrderStatus("Placed");
		order.setTotalAmount(2500.50);

		String result = order.toString();

		assertTrue(result.contains("Order #1"));
		assertTrue(result.contains("Placed"));
		assertTrue(result.contains("2500.5"));
	}
}