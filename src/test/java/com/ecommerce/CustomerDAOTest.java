package com.ecommerce;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
 
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.model.Customer;

public class CustomerDAOTest
{
	@Test
	public void testRegisterCustomer() throws ClassNotFoundException, SQLException
	{
		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer = new Customer("Shakir", "abc@gmail.com", "706333222");
		customerDAO.registerCustomer(customer);
	}

	@Test
	public void testFindCustomerById() throws ClassNotFoundException, SQLException
	{
		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer = customerDAO.findCustomerById(1);
		assertEquals("Shakir", customer.getCustomerName());
	}

	@Test
	public void testFindCustomerByEmail() throws ClassNotFoundException, SQLException
	{
		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer = customerDAO.findCustomerByEmail("abc@gmail.com");
		assertEquals("abc@gmail.com", customer.getEmail());

	}
}
