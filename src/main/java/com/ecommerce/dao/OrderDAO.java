package com.ecommerce.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.util.DBConnection;

public class OrderDAO
{

	private ProductDAO productDAO = new ProductDAO();
	private CartDAO cartDAO = new CartDAO();

	public int placeOrder(int customerId) throws SQLException, ClassNotFoundException
	{

		Connection conn = null;
		int orderId = 0;

		try
		{

			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);

			List<Cart> cartItems = cartDAO.viewCart(customerId);

			if (cartItems.isEmpty())
			{
				throw new RuntimeException("Cart is Empty");
			}

			double totalAmount = 0;

			for (Cart item : cartItems)
			{

				int stock = productDAO.getStockForUpdate(conn, item.getProductId());

				if (stock < item.getQuantity())
				{
					throw new RuntimeException("Insufficient Stock : " + item.getProductName());
				}

				totalAmount += item.getLineTotal();
			}

			String orderSql = "INSERT INTO orders(customer_id,total_amount,order_status) VALUES(?,?,?)";

			PreparedStatement orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);

			orderPs.setInt(1, customerId);
			orderPs.setDouble(2, totalAmount);
			orderPs.setString(3, "ORDERED");

			orderPs.executeUpdate();

			ResultSet rs = orderPs.getGeneratedKeys();

			if (rs.next())
			{
				orderId = rs.getInt(1);
			}

			String itemSql = "INSERT INTO order_items(order_id,product_id,quantity,price) VALUES(?,?,?,?)";

			PreparedStatement itemPs = conn.prepareStatement(itemSql);

			for (Cart item : cartItems)
			{

				itemPs.setInt(1, orderId);
				itemPs.setInt(2, item.getProductId());
				itemPs.setInt(3, item.getQuantity());
				itemPs.setDouble(4, item.getPrice());

				itemPs.addBatch();

				productDAO.reduceStock(conn, item.getProductId(), item.getQuantity());
			}

			itemPs.executeBatch();

			cartDAO.clearCart(customerId);

			conn.commit();

			System.out.println("Order Placed Successfully");

		} catch (Exception e)
		{

			if (conn != null)
			{
				conn.rollback();
			}

			throw new RuntimeException(e.getMessage());

		} finally
		{

			if (conn != null)
			{
				conn.setAutoCommit(true);
				conn.close();
			}
		}

		return orderId;
	}

	public List<Order> viewOrders(int customerId) throws SQLException, ClassNotFoundException
	{

		List<Order> orders = new ArrayList<>();

		String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_id DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{

			ps.setInt(1, customerId);

			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{

				Order order = new Order();

				order.setOrderId(rs.getInt("order_id"));
				order.setCustomerId(rs.getInt("customer_id"));
				order.setTotalAmount(rs.getDouble("total_amount"));
				order.setOrderDate(rs.getTimestamp("order_date"));
				order.setOrderStatus(rs.getString("order_status"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				order.setUpdatedAt(rs.getTimestamp("updated_at"));

				orders.add(order);
			}
		}

		return orders;
	}

	public List<OrderItem> viewOrderItems(int orderId) throws SQLException, ClassNotFoundException
	{

		List<OrderItem> items = new ArrayList<>();

		String sql = "SELECT oi.*, p.product_name " + "FROM order_items oi "
				+ "JOIN products p ON oi.product_id = p.product_id " + "WHERE oi.order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{

			ps.setInt(1, orderId);

			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{

				OrderItem item = new OrderItem();

				item.setOrderItemId(rs.getInt("order_item_id"));
				item.setOrderId(rs.getInt("order_id"));
				item.setProductId(rs.getInt("product_id"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getDouble("price"));
				item.setProductName(rs.getString("product_name"));

				items.add(item);
			}
		}

		return items;
	}

	public boolean cancelOrder(int orderId) throws SQLException, ClassNotFoundException
	{

		String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{

			ps.setString(1, "CANCELLED");
			ps.setInt(2, orderId);

			return ps.executeUpdate() > 0;
		}
	}
}