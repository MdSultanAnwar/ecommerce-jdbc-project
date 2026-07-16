package com.ecommerce.console;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

public class ProductConsole
{

	private static final Scanner sc = new Scanner(System.in);
	private static final ProductDAO productDAO = new ProductDAO();

	public void addProduct() throws ClassNotFoundException
	{
		System.out.print("Enter product name: ");
		String name = sc.nextLine().trim();

		System.out.print("Enter description: ");
		String description = sc.nextLine().trim();

		double price;
		int stock;
		try
		{
			System.out.print("Enter price: ");
			price = Double.parseDouble(sc.nextLine().trim());

			System.out.print("Enter stock quantity: ");
			stock = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e)
		{
			System.out.println("Invalid price or quantity. Product not added.");
			return;
		}

		Product product = new Product(name, description, price, stock);
		boolean added = productDAO.addProduct(product);

		if (added)
		{
			System.out.println("Product added successfully!");
		} else
		{
			System.out.println("Failed to add product.");
		}
	}

	public void deleteProduct() throws ClassNotFoundException
	{
		System.out.print("Enter Product ID to delete: ");
		int productId;
		try
		{
			productId = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e)
		{
			System.out.println("Invalid Product ID.");
			return;
		}

		boolean deleted = productDAO.deleteProduct(productId);

		if (deleted)
		{
			System.out.println("Product deleted successfully!");
		} else
		{
			System.out.println("Product not found or could not be deleted.");
		}
	}

	public void viewProducts() throws SQLException, ClassNotFoundException
	{
		List<Product> products = productDAO.viewProducts();
		if (products.size() == 0)
		{
			System.out.println("No Products");
		} else
		{
			for (Product product : products)
			{
				System.out.println(product);
			}
		}
	}

	public void searchProduct() throws SQLException, ClassNotFoundException
	{
		System.out.print("Enter product name : ");
		String keyword = sc.nextLine().trim();
		List<Product> results = productDAO.searchProduct(keyword);
		if (results.isEmpty())
		{
			System.out.println("No matching products found.");
			return;
		}
		for (Product product : results)
		{
			System.out.println(product);
		}
	}

}
