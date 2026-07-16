package com.ecommerce.console;

import java.sql.SQLException;
import java.util.List;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.service.OrderService;
import com.ecommerce.console.CustomerConsole;

public class OrderConsole
{
	private static final OrderService orderService = new OrderService();
	private static Customer loggedInCustomer;

	public void placeOrder() throws SQLException, ClassNotFoundException
	{
		int orderId = orderService.placeOrder(CustomerConsole.getLoggedInCustomer().getCustomerId());
		System.out.println();
		System.out.println("=========================================");
		System.out.println(" ORDER PLACED SUCCESSFULLY");
		System.out.println(" Order ID : " + orderId);
		System.out.println("=========================================");
	}

	public void viewOrderHistory() throws SQLException, ClassNotFoundException
	{
		List<Order> orders = orderService.viewOrders(CustomerConsole.getLoggedInCustomer().getCustomerId());

		if (orders.isEmpty())
		{
			System.out.println("No previous orders.");
			return;
		}

		for (Order o : orders)
		{
			System.out.println(o);

			List<OrderItem> items = orderService.viewOrderItems(o.getOrderId());

			for (OrderItem item : items)
			{
				System.out.println(item);
			}
		}
		for (Order o : orders)
		{
			System.out.println(o);
			List<OrderItem> items = orderService.viewOrderItems(o.getOrderId());
			for (OrderItem item : items)
			{
				System.out.println(item);
			}
		}
	}
}
