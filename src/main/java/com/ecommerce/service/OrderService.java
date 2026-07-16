package com.ecommerce.service;

import java.sql.SQLException;
import java.util.List;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

public class OrderService
{
	// Creating object of OrderDAO
	OrderDAO orderDAO = new OrderDAO();

	// Place a new order
	public int placeOrder(int customerId) throws SQLException, ClassNotFoundException
	{
		return orderDAO.placeOrder(customerId);
	}

	// View all orders of a customer
	public List<Order> viewOrders(int customerId) throws SQLException, ClassNotFoundException
	{
		return orderDAO.viewOrders(customerId);
	}

	// View items of a particular order
	public List<OrderItem> viewOrderItems(int orderId) throws SQLException, ClassNotFoundException
	{
		return orderDAO.viewOrderItems(orderId);
	}
}