package com.ecommerce;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.ecommerce.model.Customer;

public class CustomerTest
{
	@Test
	public void testDefaultConstructor()
	{
		Customer customer = new Customer();
		assertNotNull(customer);
	}

	@Test
	public void testRegistrationConstructor()
	{
		Customer customer = new Customer("Shakir", "abc@gmail.com", "9876543219");
		assertEquals("Shakir", customer.getCustomerName());
		assertEquals("abc@gmail.com", customer.getEmail());
		assertEquals("9876543219", customer.getMobile());
	}

	@Test
	public void testFullConstructor()
	{
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		Customer customer = new Customer(1, "Sultan", "sultan123@gmail.com", "987654", createdAt, updatedAt);
		assertEquals(1, customer.getCustomerId());
		assertEquals("Sultan", customer.getCustomerName());
		assertEquals("sultan123@gmail.com", customer.getEmail());
		assertEquals("987654", customer.getMobile());
		assertEquals(createdAt, customer.getCreatedAt());
		assertEquals(updatedAt, customer.getUpdatedAt());
	}

	@Test
	public void testGetterSetter()
	{
		Customer customer = new Customer();

		Timestamp createdAt = new Timestamp(System.currentTimeMillis());

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

		customer.setCustomerId(1);
		customer.setCustomerName("Shakir");
		customer.setEmail("shakir@gmail.com");
		customer.setMobile("9876543210");
		customer.setCreatedAt(createdAt);
		customer.setUpdatedAt(updatedAt);

		assertEquals(1, customer.getCustomerId());

		assertEquals("Shakir", customer.getCustomerName());

		assertEquals("shakir@gmail.com", customer.getEmail());

		assertEquals("9876543210", customer.getMobile());

		assertEquals(createdAt, customer.getCreatedAt());

		assertEquals(updatedAt, customer.getUpdatedAt());
	}

	@Test
	public void testToString()
	{
		Customer customer = new Customer("Shakir", "shakir@gmail.com", "9876543210");

		String result = customer.toString();

		assertNotNull(result);

		assertTrue(result.contains("Shakir"));
	}
}
