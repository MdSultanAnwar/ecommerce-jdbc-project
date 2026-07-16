package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.ecommerce.exception.InvalidQuantityException;
import com.ecommerce.exception.NotLoggedInException;
import com.ecommerce.exception.ProductNotFoundException;

public class ExceptionTest
{
	@Test
	public void testInvalidQuantityException()
	{
		InvalidQuantityException ex = new InvalidQuantityException("Invalid Quantity");

		assertEquals("Invalid Quantity", ex.getMessage());
	}

	@Test
	public void testProductNotFoundException()
	{
		ProductNotFoundException ex = new ProductNotFoundException("Product Not Found");

		assertEquals("Product Not Found", ex.getMessage());
	}

	@Test
	public void testNotLoggedInException()
	{
		NotLoggedInException ex = new NotLoggedInException();

		assertNull(ex.getMessage());
	}
}