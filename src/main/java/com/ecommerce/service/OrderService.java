package com.ecommerce.service;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

/**
 * Service-layer wrapper around OrderDAO. Kept thin on purpose - all transaction
 * logic (stock check, insert, batch insert, rollback) lives in
 * OrderDAO.placeOrder, this class just exposes the same operations to Main /
 * other callers.
 */
public class OrderService
{

	private final OrderDAO orderDAO = new OrderDAO();

	public int placeOrder(int customerId) throws SQLException, ClassNotFoundException
	{
		return orderDAO.placeOrder(customerId);
	}

	public List<Order> viewOrders(int customerId) throws SQLException, ClassNotFoundException
	{
		return orderDAO.viewOrders(customerId);
	}

	public List<OrderItem> viewOrderItems(int orderId) throws SQLException, ClassNotFoundException
	{
		return orderDAO.viewOrderItems(orderId);
	}
}
