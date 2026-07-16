package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.model.Product;
import com.ecommerce.util.DBConnection;

public class ProductDAO
{ 

	// Add Product
	public boolean addProduct(Product product) throws ClassNotFoundException
	{

		String sql = "INSERT INTO products(product_name, description, price, stock_quantity) VALUES (?, ?, ?, ?)";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		{

			ps.setString(1, product.getProductName());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getPrice());
			ps.setInt(4, product.getStockQuantity());

			return ps.executeUpdate() > 0;

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	// Get Product By Id
	public Product getProductById(int productId) throws ClassNotFoundException
	{

		String sql = "SELECT * FROM products WHERE product_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		{

			ps.setInt(1, productId);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{

				Product product = new Product();

				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				product.setCreatedAt(rs.getTimestamp("created_at"));
				product.setUpdatedAt(rs.getTimestamp("updated_at"));

				return product;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// Get All Products
	public List<Product> getAllProducts() throws ClassNotFoundException
	{

		List<Product> productList = new ArrayList<>();

		String sql = "SELECT * FROM products";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery())
		{

			while (rs.next())
			{

				Product product = new Product();

				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				product.setCreatedAt(rs.getTimestamp("created_at"));
				product.setUpdatedAt(rs.getTimestamp("updated_at"));

				productList.add(product);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return productList;
	}

	// Update Product
	public boolean updateProduct(Product product) throws ClassNotFoundException
	{

		String sql = "UPDATE products SET product_name=?, description=?, price=?, stock_quantity=? WHERE product_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		{

			ps.setString(1, product.getProductName());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getPrice());
			ps.setInt(4, product.getStockQuantity());
			ps.setInt(5, product.getProductId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	// Delete Product
	public boolean deleteProduct(int productId) throws ClassNotFoundException
	{

		String sql = "DELETE FROM products WHERE product_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		{

			ps.setInt(1, productId);

			return ps.executeUpdate() > 0;

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public int getStockForUpdate(Connection conn, int productId) throws SQLException
	{

		String sql = "SELECT stock_quantity FROM products WHERE product_id = ? FOR UPDATE";

		try (PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, productId);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{
				return rs.getInt("stock_quantity");
			}
		}

		return 0;
	}

	public void reduceStock(Connection conn, int productId, int quantity) throws SQLException
	{

		String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, quantity);
			ps.setInt(2, productId);

			ps.executeUpdate();
		}

	}

	public List<Product> viewProducts() throws ClassNotFoundException
	{

		return getAllProducts();
	}

	public List<Product> searchProduct(String keyword) throws ClassNotFoundException
	{

		List<Product> productList = new ArrayList<>();

		String sql = "SELECT * FROM products WHERE product_name LIKE ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		{

			ps.setString(1, "%" + keyword + "%");

			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{

				Product product = new Product();

				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				product.setCreatedAt(rs.getTimestamp("created_at"));
				product.setUpdatedAt(rs.getTimestamp("updated_at"));

				productList.add(product);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return productList;
	}

	public Product findProductByName(String productName) throws ClassNotFoundException
	{

		String sql = "SELECT * FROM products WHERE product_name = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		{

			ps.setString(1, productName);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{

				Product product = new Product();

				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				product.setCreatedAt(rs.getTimestamp("created_at"));
				product.setUpdatedAt(rs.getTimestamp("updated_at"));

				return product;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}