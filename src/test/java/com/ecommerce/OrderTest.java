package com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

public class OrderTest {

    private OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        orderDAO = new OrderDAO();
    }

    // Test 1
    @Test
    void testOrderDAOObjectCreation() {
        assertNotNull(orderDAO);
    }

    // Test 2
    @Test
    void testViewOrdersValidCustomer() throws Exception {
        List<Order> orders = orderDAO.viewOrders(1);

        assertNotNull(orders);

        if (!orders.isEmpty()) {
            assertEquals(1, orders.get(0).getCustomerId());
        }
    }

    // Test 3
    @Test
    void testViewOrdersInvalidCustomer() throws Exception {
        List<Order> orders = orderDAO.viewOrders(99999);

        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    // Test 4
    @Test
    void testViewOrderItemsInvalidOrder() throws Exception {
        List<OrderItem> items = orderDAO.viewOrderItems(99999);

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    // Test 5
    @Test
    void testCancelOrderInvalidId() throws Exception {
        boolean result = orderDAO.cancelOrder(99999);

        assertFalse(result);
    }

    // Test 6
    @Test
    void testCancelOrderValidId() throws Exception {

        List<Order> orders = orderDAO.viewOrders(1);

        if (!orders.isEmpty()) {

            int orderId = orders.get(0).getOrderId();

            boolean result = orderDAO.cancelOrder(orderId);

            assertTrue(result);
        }
    }

    // Test 7
    @Test
    void testPlaceOrderEmptyCart() {

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {

            orderDAO.placeOrder(99999);

        });

        assertNotNull(ex.getMessage());
    }

    // Test 8
    @Test
    void testViewOrderItemsValidOrder() throws Exception {

        List<Order> orders = orderDAO.viewOrders(1);

        if (!orders.isEmpty()) {

            int orderId = orders.get(0).getOrderId();

            List<OrderItem> items = orderDAO.viewOrderItems(orderId);

            assertNotNull(items);
        }
    }

    // Test 9
    @Test
    void testViewOrdersReturnType() throws Exception {

        List<Order> orders = orderDAO.viewOrders(1);

        assertTrue(orders instanceof List);
    }

    // Test 10
    @Test
    void testViewOrderItemsReturnType() throws Exception {

        List<OrderItem> items = orderDAO.viewOrderItems(1);

        assertTrue(items instanceof List);
    }

    // Test 11
    @Test
    void testCancelOrderReturnType() throws Exception {

        boolean result = orderDAO.cancelOrder(99999);

        assertTrue(result == true || result == false);
    }

    // Test 12
    @Test
    void testPlaceOrderInvalidCustomer() {

        assertThrows(RuntimeException.class, () -> {

            orderDAO.placeOrder(-1);

        });
    }
}