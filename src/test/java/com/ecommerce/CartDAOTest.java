package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.model.Cart;

public class CartDAOTest
{
	private CartDAO cartDAO;

	@BeforeEach
	public void setup() throws Exception
	{
		cartDAO = new CartDAO();

		// Clean test data
		cartDAO.clearCart(1);
	}

	@Test
	public void testAddNewProduct() throws Exception
	{
		cartDAO.addToCart(1, 1, 2);

		List<Cart> list = cartDAO.viewCart(1);

		assertEquals(1, list.size());
		assertEquals(1, list.get(0).getProductId());
		assertEquals(2, list.get(0).getQuantity());
	}

	@Test
	public void testAddExistingProduct() throws Exception
	{
		cartDAO.addToCart(1, 1, 2);
		cartDAO.addToCart(1, 1, 3);

		List<Cart> list = cartDAO.viewCart(1);

		assertEquals(5, list.get(0).getQuantity());
	}

	@Test
	public void testViewEmptyCart() throws Exception
	{
		List<Cart> list = cartDAO.viewCart(1);

		assertTrue(list.isEmpty());
	}

	@Test
	public void testRemoveProduct() throws Exception
	{
		cartDAO.addToCart(1, 1, 2);

		cartDAO.removeFromCart(1, 1);

		List<Cart> list = cartDAO.viewCart(1);

		assertTrue(list.isEmpty());
	}

	@Test
	public void testClearCartWithItems() throws Exception
	{
		cartDAO.addToCart(1, 1, 2);
		cartDAO.addToCart(1, 3, 3);

		cartDAO.clearCart(1);

		assertTrue(cartDAO.viewCart(1).isEmpty());
	}

	@Test
	public void testClearEmptyCart() throws Exception
	{
		cartDAO.clearCart(999);

		assertTrue(cartDAO.viewCart(999).isEmpty());
	}

	@Test
	public void testMultipleCartProducts() throws Exception
	{
		cartDAO.addToCart(1, 1, 2);
		cartDAO.addToCart(1, 3, 3);

		List<Cart> list = cartDAO.viewCart(1);

		assertEquals(2, list.size());
	}

	@Test
	public void testQuantityIncreaseMultipleTimes() throws Exception
	{
		cartDAO.addToCart(1, 1, 1);
		cartDAO.addToCart(1, 1, 2);
		cartDAO.addToCart(1, 1, 4);

		Cart cart = cartDAO.viewCart(1).get(0);

		assertEquals(7, cart.getQuantity());
	}
}