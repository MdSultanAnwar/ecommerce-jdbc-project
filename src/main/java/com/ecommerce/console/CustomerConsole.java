package com.ecommerce.console;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.exception.InvalidQuantityException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
//import com.ecommerce.service.OrderService;

public class CustomerConsole
{
	private static final Scanner sc = new Scanner(System.in);

	private static final CustomerDAO customerDAO = new CustomerDAO();
	private static final CartDAO cartDAO = new CartDAO();
	private static final ProductDAO productDAO = new ProductDAO();
	// private static final OrderService orderService = new OrderService();

	private static Customer loggedInCustomer;

	public static Customer getLoggedInCustomer()
	{
		return loggedInCustomer;
	}

	/**
	 * Simple login-by-email since the customers table has no password column. If
	 * the email isn't found, the customer is registered on the spot.
	 * 
	 * @throws ClassNotFoundException
	 */
	public void login() throws SQLException, ClassNotFoundException
	{
		System.out.print("Enter your email: ");
		String email = sc.nextLine().trim();

		Customer existing = customerDAO.findCustomerByEmail(email);
		if (existing != null)
		{
			loggedInCustomer = existing;
			System.out.println("Welcome back, " + existing.getCustomerName() + "!");
			return;
		}

		System.out.println("No account found for that email. Let's register you.");
		System.out.print("Enter your name: ");
		String name = sc.nextLine().trim();
		System.out.print("Enter your mobile: ");
		String mobile = sc.nextLine().trim();

		Customer newCustomer = new Customer(name, email, mobile);

		customerDAO.registerCustomer(newCustomer);

		// Customer ko DB se wapas fetch kar lo email ke basis par
		loggedInCustomer = customerDAO.findCustomerByEmail(email);

		System.out.println();
		System.out.println("=========================================");
		System.out.println(" Login Successful");
		System.out.println(" Welcome " + loggedInCustomer.getCustomerName());
		System.out.println("=========================================");
	}

	public void addToCart()
			throws SQLException, ClassNotFoundException, ProductNotFoundException, InvalidQuantityException
	{
		System.out.print("Enter exact product name to add: ");
		String name = sc.nextLine().trim();

		Product product = productDAO.findProductByName(name);
		if (product == null)
		{
			throw new ProductNotFoundException("Product not found..");
		}

		System.out.print("Enter quantity: ");
		int qty;
		try
		{
			qty = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e)
		{
			System.out.println("Invalid quantity.");
			return;
		}
		if (qty <= 0)
		{
			throw new InvalidQuantityException("Quantity must be positive");
		}

		cartDAO.addToCart(loggedInCustomer.getCustomerId(), product.getProductId(), qty);
		System.out.println(qty + " x '" + product.getProductName() + "' added to cart.");
	}

	public void viewCart() throws SQLException, ClassNotFoundException
	{
		List<Cart> items = cartDAO.viewCart(loggedInCustomer.getCustomerId());
		if (items.isEmpty())
		{
			System.out.println("Your cart is empty.");
			return;
		}

		double grandTotal = 0;

		for (Cart cart : items)
		{
			System.out.println(cart);

			grandTotal = grandTotal + cart.getLineTotal();
		}

		System.out.println("----------------------------------");
		System.out.println("Grand Total : Rs. " + grandTotal);
		System.out.println("----------------------------------");
	}

	public void removeFromCart() throws SQLException, ClassNotFoundException
	{
		System.out.print("Enter exact product name to remove from cart: ");
		String name = sc.nextLine().trim();

		Product product = productDAO.findProductByName(name);
		if (product == null)
		{
			System.out.println("No product found named '" + name + "'.");
			return;
		}

		cartDAO.removeFromCart(loggedInCustomer.getCustomerId(), product.getProductId());
		System.out.println("'" + product.getProductName() + "' removed from cart (if it was there).");
	}

	public void removeAllFromCart() throws ClassNotFoundException, SQLException
	{
		cartDAO.clearCart(loggedInCustomer.getCustomerId());
		System.out.println("All products removed from the cart.!!");
	}

//	public void placeOrder() throws SQLException, ClassNotFoundException
//	{
//		int orderId = orderService.placeOrder(loggedInCustomer.getCustomerId());
//		System.out.println();
//		System.out.println("=========================================");
//		System.out.println(" ORDER PLACED SUCCESSFULLY");
//		System.out.println(" Order ID : " + orderId);
//		System.out.println("=========================================");
//	}

}
