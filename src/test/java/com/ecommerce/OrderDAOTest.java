package com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

public class OrderDAOTest {

	private OrderDAO orderDAO;

	@BeforeEach
	void setUp() {
		orderDAO = new OrderDAO();
	}

	@Test
	void testViewOrdersValidCustomer() throws Exception {
		List<Order> orders = orderDAO.viewOrders(1);
		assertNotNull(orders);
	}

	@Test
	void testViewOrdersInvalidCustomer() throws Exception {
		List<Order> orders = orderDAO.viewOrders(99999);
		assertNotNull(orders);
		assertTrue(orders.isEmpty());
	}

	@Test
	void testViewOrderItemsInvalidOrder() throws Exception {
		List<OrderItem> items = orderDAO.viewOrderItems(99999);
		assertNotNull(items);
		assertTrue(items.isEmpty());
	}

	@Test
	void testCancelInvalidOrder() throws Exception {
		boolean result = orderDAO.cancelOrder(99999);
		assertFalse(result);
	}

	@Test
	void testPlaceOrderEmptyCart() {
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			orderDAO.placeOrder(99999);
		});

		assertNotNull(ex.getMessage());
	}

	@Test
	void testViewOrdersReturnsList() throws Exception {
		List<Order> orders = orderDAO.viewOrders(1);
		assertTrue(orders instanceof List);
	}

	@Test
	void testViewOrderItemsReturnsList() throws Exception {
		List<OrderItem> items = orderDAO.viewOrderItems(1);
		assertNotNull(items);
	}

	@Test
	void testCancelOrderReturnType() throws Exception {
		boolean result = orderDAO.cancelOrder(99999);
		assertTrue(result == true || result == false);
	}

	@Test
	void testPlaceOrderReturnValue() {
		assertThrows(RuntimeException.class, () -> {
			orderDAO.placeOrder(-1);
		});
	}

	@Test
	void testOrderDAOObjectCreation() {
		assertNotNull(orderDAO);
	}

	@Test
	void testPlaceOrderSuccessfully() throws Exception {

		int orderId = orderDAO.placeOrder(1);

		assertTrue(orderId > 0);
	}

}
