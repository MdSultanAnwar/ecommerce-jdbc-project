package com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.ecommerce.model.OrderItem;

public class OrderItemTest {

    @Test
    void testDefaultConstructor() {
        OrderItem item = new OrderItem();

        assertNotNull(item);
    }

    @Test
    void testParameterizedConstructor() {
        OrderItem item = new OrderItem(1, 2, 3, 100.0);

        assertEquals(1, item.getOrderId());
        assertEquals(2, item.getProductId());
        assertEquals(3, item.getQuantity());
        assertEquals(100.0, item.getPrice());
    }

    @Test
    void testGettersAndSetters() {

        OrderItem item = new OrderItem();

        Timestamp now = new Timestamp(System.currentTimeMillis());

        item.setOrderItemId(10);
        item.setOrderId(20);
        item.setProductId(30);
        item.setQuantity(5);
        item.setPrice(250.0);
        item.setCreatedAt(now);
        item.setUpdatedAt(now);
        item.setProductName("Laptop");

        assertEquals(10, item.getOrderItemId());
        assertEquals(20, item.getOrderId());
        assertEquals(30, item.getProductId());
        assertEquals(5, item.getQuantity());
        assertEquals(250.0, item.getPrice());
        assertEquals(now, item.getCreatedAt());
        assertEquals(now, item.getUpdatedAt());
        assertEquals("Laptop", item.getProductName());
    }

    @Test
    void testGetLineTotal() {

        OrderItem item = new OrderItem();

        item.setPrice(200);
        item.setQuantity(4);

        assertEquals(800.0, item.getLineTotal());
    }

    @Test
    void testGetLineTotalWhenPriceIsNull() {

        OrderItem item = new OrderItem();

        item.setQuantity(5);

        assertEquals(0.0, item.getLineTotal());
    }

    @Test
    void testToString() {

        OrderItem item = new OrderItem();

        item.setProductName("Mouse");
        item.setQuantity(2);
        item.setPrice(500);

        String result = item.toString();

        assertTrue(result.contains("Mouse"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("500"));
        assertTrue(result.contains("1000.0"));
    }
}