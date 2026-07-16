package com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;
import com.ecommerce.util.DBConnection;

public class ProductDAOTest
{

	ProductDAO pd = new ProductDAO();

	@Test
	public void testAddProduct() throws ClassNotFoundException, SQLException
	{

		Product product = new Product();
		product.setProductName("Ipad");
		product.setDescription("Apple Tablet");
		product.setPrice(50000.0);
		product.setStockQuantity(10);

		boolean result = pd.addProduct(product);

		assertTrue(result);
	}

	@Test
	public void testGetProductById_ProductFound() throws Exception
	{

		int productId = 1; // Existing product ID

		Product product = pd.getProductById(productId);

		assertNotNull(product);
		assertEquals(productId, product.getProductId());
	}

	@Test
	public void testGetProductById_NotFound() throws Exception
	{

		int productId = -1; // ID that does not exist

		Product product = pd.getProductById(productId);

		assertNull(product);
	}

	@Test
	public void testGetAllProducts() throws Exception
	{

		List<Product> productList = pd.getAllProducts();

		assertNotNull(productList);
		assertFalse(productList.isEmpty());
	}

	@Test
	public void testUpdateProduct() throws Exception
	{

		Product product = new Product();
		product.setProductId(1);
		product.setProductName("Updated Laptop");
		product.setDescription("Updated Description");
		product.setPrice(75000.0);
		product.setStockQuantity(20);

		boolean result = pd.updateProduct(product);

		assertTrue(result);
	}

	@Test
	public void testUpdateProduct_NotFound() throws Exception
	{

		Product product = new Product();
		product.setProductId(99999);
		product.setProductName("Laptop");
		product.setDescription("Test");
		product.setPrice(50000.0);
		product.setStockQuantity(10);

		boolean result = pd.updateProduct(product);

		assertFalse(result);
	}

	@Test
	public void testDeleteProduct_InvalidId() throws Exception
	{
		boolean result = pd.deleteProduct(-1);

		assertFalse(result);
	}

	@Test
	public void testGetStockForUpdate() throws Exception
	{

		Connection conn = DBConnection.getConnection();

		int productId = 1;

		int stock = pd.getStockForUpdate(conn, productId);

		assertTrue(stock >= 0);
	}

	@Test
	public void testReduceStock() throws Exception
	{

		Connection conn = DBConnection.getConnection();

		int productId = 1; // Existing product ID
		int quantity = 2;

		// Stock before update
		int stockBefore = pd.getStockForUpdate(conn, productId);

		// Reduce stock
		pd.reduceStock(conn, productId, quantity);

		// Stock after update
		int stockAfter = pd.getStockForUpdate(conn, productId);

		assertEquals(stockBefore - quantity, stockAfter);
	}

	@Test
	public void testReduceStock_ProductNotFound() throws Exception
	{

		Connection conn = DBConnection.getConnection();

		int productId = 99999;
		int quantity = 2;

		int stockBefore = pd.getStockForUpdate(conn, productId);

		pd.reduceStock(conn, productId, quantity);

		int stockAfter = pd.getStockForUpdate(conn, productId);

		assertEquals(0, stockAfter);
	}

	@Test
	public void testViewProducts() throws Exception
	{

		List<Product> productList = pd.viewProducts();

		assertNotNull(productList);
		assertFalse(productList.isEmpty());
	}

	@Test
	public void testSearchProduct() throws Exception
	{

		String keyword = "Ipad"; // Existing product name

		List<Product> productList = pd.searchProduct(keyword);

		assertNotNull(productList);
		assertFalse(productList.isEmpty());

	}

	@Test
	public void testSearchProduct_NotFound() throws Exception
	{

		String keyword = "XYZ123"; // Product name that does not exist

		List<Product> productList = pd.searchProduct(keyword);

		assertNotNull(productList);
		assertTrue(productList.isEmpty());
	}

	@Test
	public void testFindProductByName() throws Exception
	{

		String productName = "Ipad";

		Product product = pd.findProductByName(productName);

		assertNotNull(product);
	}

	@Test
	public void testFindProductByName_NotFound() throws Exception
	{

		String productName = "XYZ123";

		Product product = pd.findProductByName(productName);

		assertNull(product);
	}

}