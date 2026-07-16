package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.service.OrderService;

public class OrderServiceTest
{
	@Test
	public void testViewOrders() throws ClassNotFoundException, SQLException
	{
		OrderService service = new OrderService();

		List<Order> orders = service.viewOrders(1);

		assertEquals(false, orders.isEmpty());
	}

	@Test
	public void testViewOrderItems() throws ClassNotFoundException, SQLException
	{
		OrderService service = new OrderService();

		List<OrderItem> items = service.viewOrderItems(1);

		assertEquals(false, items.isEmpty());
	}
}