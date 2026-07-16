package com.ecommerce;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.exception.InvalidQuantityException;
import com.ecommerce.exception.NotLoggedInException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.service.OrderService;

public class App
{

	// Scanner object for user input
	private static final Scanner sc = new Scanner(System.in);

	// DAO objects
	private static final ProductDAO productDAO = new ProductDAO();
	private static final CustomerDAO customerDAO = new CustomerDAO();
	private static final CartDAO cartDAO = new CartDAO();

	// Service object
	private static final OrderService orderService = new OrderService();

	// Stores the currently logged-in customer
	private static Customer loggedInCustomer = null;

	public static void main(String[] args)
			throws ClassNotFoundException, ProductNotFoundException, InvalidQuantityException
	{
		System.out.println("=== Welcome to the Mini E-Commerce System ===");

		boolean exitApp = false;
		// Top-level menu: Admin / Customer / Exit
		while (!exitApp)
		{
			printMainMenu();
			String mainChoice = sc.nextLine().trim();

			switch (mainChoice) {
			case "1":
				adminMenu();
				break;
			case "2":
				customerMenu();
				break;
			case "3":
				exitApp = true;
				System.out.println("See You Again!!");
				break;
			default:
				System.out.println("Invalid option, try again.");
			}
		}

		sc.close();
	}

	private static void printMainMenu()
	{
		System.out.println();
		System.out.println("=============================");
		System.out.println("1. Admin");
		System.out.println("2. Customer");
		System.out.println("3. Exit");
		System.out.print("Choose an option: ");
	}

	// =========================================================
	// ADMIN PANEL (new)
	// =========================================================

	private static void adminMenu() throws ClassNotFoundException
	{
		boolean back = false;
		while (!back)
		{
			System.out.println();
			System.out.println("-------------------------------------------");
			System.out.println("ADMIN PANEL");
			System.out.println("1. Add Products");
			System.out.println("2. View Products");
			System.out.println("3. Delete Products");
			System.out.println("0. Back to Main Menu");
			System.out.print("Choose an option: ");
			String choice = sc.nextLine().trim();

			try
			{
				switch (choice) {
				case "1":
					addProduct();
					break;
				case "2":
					viewProducts();
					break;
				case "3":
					deleteProduct();
					break;
				case "0":
					back = true;
					break;
				default:
					System.out.println("Invalid option, try again.");
				}
			} catch (SQLException e)
			{
				System.out.println("Database error: " + e.getMessage());
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void addProduct() throws ClassNotFoundException
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

	private static void deleteProduct() throws ClassNotFoundException
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

	// =========================================================
	// CUSTOMER PANEL (your existing code - logic unchanged)
	// =========================================================

	private static void customerMenu()
			throws ClassNotFoundException, ProductNotFoundException, InvalidQuantityException
	{
		boolean running = true;
		// Run until user goes back to main menu
		while (running)
		{
			printMenu();
			String choice = sc.nextLine().trim();

			try
			{
				switch (choice) {
				case "1":
					viewProducts();
					break;
				case "2":
					searchProduct();
					break;
				case "3":
					login();
					break;
				case "4":
					requireLogin();
					addToCart();
					break;
				case "5":
					requireLogin();
					viewCart();
					break;
				case "6":
					requireLogin();
					removeFromCart();
					break;
				case "7":
					requireLogin();
					removeAllFromCart();
					break;
				case "8":
					requireLogin();
					placeOrder();
					break;
				case "9":
					requireLogin();
					viewOrderHistory();
					break;
				case "10":
					loggedInCustomer = null;
					System.out.println("Logged out.");
					break;
				case "0":
					running = false;
					System.out.println("Returning to Main Menu...");
					break;
				default:
					System.out.println("Invalid option, try again.");
				}
			} catch (NotLoggedInException e)
			{
				System.out.println("You need to login first(Choose option 3 for that).");
			} catch (ProductNotFoundException e)
			{
				System.out.println(e.getMessage());
			}

			catch (InvalidQuantityException e)
			{
				System.out.println(e.getMessage());
			}

			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				System.out.println("Database error: " + e.getMessage());
			}
		}
	}

	private static void printMenu()
	{
		System.out.println();
		System.out.println("-------------------------------------------");
		System.out.println("Logged in as: "
				+ (loggedInCustomer == null ? "guest (not logged in)" : loggedInCustomer.getCustomerName()));
		System.out.println("1. View Products      (no login needed)");
		System.out.println("2. Search Products    (no login needed)");
		System.out.println("3. Login / Register");
		System.out.println("4. Add To Cart        (login required)");
		System.out.println("5. View Cart          (login required)");
		System.out.println("6. Remove From Cart   (login required)");
		System.out.println("7. Clear Cart         (login required)");
		System.out.println("8. Place Order        (login required)");
		System.out.println("9. Order History      (login required)");
		System.out.println("10. Logout");
		System.out.println("0. Back to Main Menu");
		System.out.print("Choose an option: ");
	}

	private static void requireLogin() throws NotLoggedInException
	{
		if (loggedInCustomer == null)
		{
			throw new NotLoggedInException();
		}
	}

	private static void viewProducts() throws SQLException, ClassNotFoundException
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

	private static void searchProduct() throws SQLException, ClassNotFoundException
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

	/**
	 * Simple login-by-email since the customers table has no password column. If
	 * the email isn't found, the customer is registered on the spot.
	 * 
	 * @throws ClassNotFoundException
	 */
	private static void login() throws SQLException, ClassNotFoundException
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

		System.out.println("Registered and logged in as " + name);
	}

	private static void addToCart()
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

	private static void viewCart() throws SQLException, ClassNotFoundException
	{
		List<Cart> items = cartDAO.viewCart(loggedInCustomer.getCustomerId());
		if (items.isEmpty())
		{
			System.out.println("Your cart is empty.");
			return;
		}
		items.forEach(System.out::println);
	}

	private static void removeFromCart() throws SQLException, ClassNotFoundException
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

	private static void removeAllFromCart() throws ClassNotFoundException, SQLException
	{
		cartDAO.clearCart(loggedInCustomer.getCustomerId());
		System.out.println("All products removed from the cart.!!");
	}

	private static void placeOrder() throws SQLException, ClassNotFoundException
	{
		int orderId = orderService.placeOrder(loggedInCustomer.getCustomerId());
		System.out.println("Order placed successfully! Order ID: " + orderId);
	}

	private static void viewOrderHistory() throws SQLException, ClassNotFoundException
	{
		List<Order> orders = orderService.viewOrders(loggedInCustomer.getCustomerId());
		if (orders.isEmpty())
		{
			System.out.println("No previous orders.");
			return;
		}
		for (Order o : orders)
		{
			System.out.println(o);
			List<OrderItem> items = orderService.viewOrderItems(o.getOrderId());
			for (OrderItem item : items)
			{
				System.out.println(item);
			}
		}
	}

}
