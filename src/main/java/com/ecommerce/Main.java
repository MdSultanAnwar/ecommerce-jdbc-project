package com.ecommerce;

import com.ecommerce.console.MainMenuConsole;
import com.ecommerce.exception.InvalidQuantityException;
import com.ecommerce.exception.ProductNotFoundException;

public class Main
{

	public static void main(String[] args)
			throws ClassNotFoundException, ProductNotFoundException, InvalidQuantityException

	{
		MainMenuConsole system = new MainMenuConsole();
		system.start();
	}

}
