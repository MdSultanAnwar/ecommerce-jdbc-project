package com.ecommerce;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;//

import com.ecommerce.dao.CartDAO;
import com.ecommerce.model.Cart;

public class CartDAOTest
{

	CartDAO cartDAO = new CartDAO();

	@Test
	public void testAddNewProductToCart() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;
		int productId = 1001;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, productId, 2);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertFalse(cartList.isEmpty());
		assertEquals(productId, cartList.get(0).getProductId());
		assertEquals(2, cartList.get(0).getQuantity());
	} 

	@Test
	public void testAddExistingProductUpdateQuantity() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;
		int productId = 11;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, productId, 2);
		cartDAO.addToCart(customerId, productId, 3);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertEquals(1, cartList.size());
		assertEquals(5, cartList.get(0).getQuantity());
	}

	@Test
	public void testViewCartWithProducts() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, 1001, 2);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertNotNull(cartList);
		assertFalse(cartList.isEmpty());
	}

	@Test
	public void testViewEmptyCart() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;

		cartDAO.clearCart(customerId);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertNotNull(cartList);
		assertTrue(cartList.isEmpty());
	}

	@Test
	public void testRemoveExistingProduct() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;
		int productId = 1001;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, productId, 2);

		cartDAO.removeFromCart(customerId, productId);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertTrue(cartList.isEmpty());
	}

	@Test
	public void testRemoveNonExistingProduct() throws SQLException
	{

		int customerId = 1;

		assertDoesNotThrow(() -> {
			cartDAO.removeFromCart(customerId, 9999);
		});
	}

	@Test
	public void testAddExistingProductQuantityIncrease() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;
		int productId = 1001;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, productId, 5);

		cartDAO.addToCart(customerId, productId, 10);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertEquals(15, cartList.get(0).getQuantity());
	}

	@Test
	public void testViewMultipleCartItems() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, 1001, 2);
		cartDAO.addToCart(customerId, 6, 3);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertEquals(2, cartList.size());
	}

	@Test
	public void testRemoveOneProductKeepOther() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, 1001, 2);
		cartDAO.addToCart(customerId, 6, 3);

		cartDAO.removeFromCart(customerId, 1001);

		List<Cart> cartList = cartDAO.viewCart(customerId);

		assertEquals(1, cartList.size());
		assertEquals(6, cartList.get(0).getProductId());
	}

	@Test
	public void testClearCartWithItems() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;

		cartDAO.clearCart(customerId);

		cartDAO.addToCart(customerId, 1001, 2);
		cartDAO.addToCart(customerId, 6, 3);

		List<Cart> before = cartDAO.viewCart(customerId);

		assertEquals(2, before.size());

		cartDAO.clearCart(customerId);

		List<Cart> after = cartDAO.viewCart(customerId);

		assertTrue(after.isEmpty());
	}

	@Test
	public void testClearEmptyCart() throws SQLException, ClassNotFoundException
	{

		int customerId = 1;

		cartDAO.clearCart(customerId);

		assertDoesNotThrow(() -> {
			cartDAO.clearCart(customerId);
		});

		assertTrue(cartDAO.viewCart(customerId).isEmpty());
	}
}