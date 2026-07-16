package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.model.Cart;
import com.ecommerce.util.DBConnection;

public class CartDAO
{

	// Add product to cart
	public void addToCart(int customerId, int productId, int quantity) throws SQLException, ClassNotFoundException
	{

		String checkSql = "SELECT cart_id, quantity FROM cart WHERE customer_id=? AND product_id=?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement checkPs = conn.prepareStatement(checkSql))
		{

			checkPs.setInt(1, customerId);
			checkPs.setInt(2, productId);

			ResultSet rs = checkPs.executeQuery();

			if (rs.next())
			{

				// Product already exists in cart - update quantity
				int cartId = rs.getInt("cart_id");
				int oldQuantity = rs.getInt("quantity");

				String updateSql = "UPDATE cart SET quantity=? WHERE cart_id=?";

				try (PreparedStatement updatePs = conn.prepareStatement(updateSql))
				{

					updatePs.setInt(1, oldQuantity + quantity);
					updatePs.setInt(2, cartId);

					updatePs.executeUpdate();
				}

			} else
			{

				// Product not in cart - insert new item
				String insertSql = "INSERT INTO cart(customer_id, product_id, quantity) VALUES(?,?,?)";

				try (PreparedStatement insertPs = conn.prepareStatement(insertSql))
				{

					insertPs.setInt(1, customerId);
					insertPs.setInt(2, productId);
					insertPs.setInt(3, quantity);

					insertPs.executeUpdate();
				}
			}
		}
	}

	// View customer's cart
	public List<Cart> viewCart(int customerId) throws SQLException, ClassNotFoundException
	{

		List<Cart> cartList = new ArrayList<>();

		String sql = "SELECT c.cart_id, c.customer_id, c.product_id, c.quantity, " + "p.product_name, p.price "
				+ "FROM cart c " + "JOIN products p ON c.product_id = p.product_id " + "WHERE c.customer_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{

			ps.setInt(1, customerId);

			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{

				Cart cart = new Cart();

				cart.setCartId(rs.getInt("cart_id"));
				cart.setCustomerId(rs.getInt("customer_id"));
				cart.setProductId(rs.getInt("product_id"));
				cart.setQuantity(rs.getInt("quantity"));

				// Product details
				cart.setProductName(rs.getString("product_name"));
				cart.setPrice(rs.getDouble("price"));

				cartList.add(cart);
			}
		}

		return cartList;
	}

	// Remove one product from cart
	public void removeFromCart(int customerId, int productId) throws SQLException, ClassNotFoundException
	{

		String sql = "DELETE FROM cart WHERE customer_id=? AND product_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{

			ps.setInt(1, customerId);
			ps.setInt(2, productId);

			ps.executeUpdate();
		}
	}

	// Clear all items from cart
	public void clearCart(int customerId) throws SQLException, ClassNotFoundException
	{

		String sql = "DELETE FROM cart WHERE customer_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{

			ps.setInt(1, customerId);

			ps.executeUpdate();
		}
	}
}