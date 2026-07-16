package com.ecommerce.console;

import java.sql.SQLException;
import java.util.Scanner;

import com.ecommerce.exception.InvalidQuantityException;
import com.ecommerce.exception.NotLoggedInException;
import com.ecommerce.exception.ProductNotFoundException;

public class MainMenuConsole
{

	// Scanner object for user input
	private static final Scanner sc = new Scanner(System.in);
	private static ProductConsole productConsole = new ProductConsole();
	private static CustomerConsole customerConsole = new CustomerConsole();
	private static OrderConsole orderConsole = new OrderConsole();

	public void start() throws ClassNotFoundException, ProductNotFoundException, InvalidQuantityException
	{
		System.out.println();

		System.out.println("     WELCOME TO MINI E-COMMERCE SYSTEM");

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
				System.out.println();
				System.out.println("=========================================");
				System.out.println("      Thank You For Visiting");
				System.out.println("     (MINI E-COMMERCE SYSTEM)");
				System.out.println("=========================================");
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
		System.out.println("=========================================");
		System.out.println("                MAIN MENU");
		System.out.println("=========================================");
		System.out.println("1. Admin");
		System.out.println("2. Customer");
		System.out.println("3. Exit");
		System.out.println("=========================================");
		System.out.print("Enter Choice : ");
	}

	// =========================================================
	// ADMIN PANEL
	// =========================================================

	private static void adminMenu() throws ClassNotFoundException
	{
		boolean back = false;
		while (!back)
		{
			System.out.println();
			System.out.println("-------------------------------------------");
			System.out.println("=========================================");
			System.out.println("              ADMIN PANEL");
			System.out.println("=========================================");
			System.out.println("1. Add Products");
			System.out.println("2. View Products");
			System.out.println("3. Delete Products");
			System.out.println("0. Back to Main Menu");
			System.out.println("=========================================");
			System.out.print("Choose an option: ");
			String choice = sc.nextLine().trim();

			try
			{
				switch (choice) {
				case "1":
					productConsole.addProduct();
					break;
				case "2":
					productConsole.viewProducts();
					break;
				case "3":
					productConsole.deleteProduct();
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

	private static void customerMenu() throws ClassNotFoundException, ProductNotFoundException, InvalidQuantityException
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
					productConsole.viewProducts();
					break;
				case "2":
					productConsole.searchProduct();
					break;
				case "3":
					customerConsole.login();
					break;
				case "4":
					requireLogin();
					customerConsole.addToCart();
					break;
				case "5":
					requireLogin();
					customerConsole.viewCart();
					break;
				case "6":
					requireLogin();
					customerConsole.removeFromCart();
					break;
				case "7":
					requireLogin();
					customerConsole.removeAllFromCart();
					break;
				case "8":
					requireLogin();
					orderConsole.placeOrder();
					break;
				case "9":
					requireLogin();
					orderConsole.viewOrderHistory();
					break;
				case "10":
					// loggedInCustomer = null;
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
		System.out.println("=========================================");
		System.out.println("           CUSTOMER PANEL");
		System.out.println("=========================================");

		if (CustomerConsole.getLoggedInCustomer() == null)
		{
			System.out.println("Logged In : Guest");
		} else
		{
			System.out.println("Logged In : " + CustomerConsole.getLoggedInCustomer().getCustomerName());
		}

		System.out.println("-----------------------------------------");

		System.out.println("1. View Products");
		System.out.println("2. Search Products");
		System.out.println("3. Login / Register");
		System.out.println("4. Add To Cart");
		System.out.println("5. View Cart");
		System.out.println("6. Remove From Cart");
		System.out.println("7. Clear Cart");
		System.out.println("8. Place Order");
		System.out.println("9. Order History");
		System.out.println("10. Logout");
		System.out.println("0. Back");

		System.out.println("=========================================");
		System.out.print("Enter Choice : ");
	}

	private static void requireLogin() throws NotLoggedInException
	{
		if (CustomerConsole.getLoggedInCustomer() == null)
		{
			throw new NotLoggedInException();
		}
	}
}
