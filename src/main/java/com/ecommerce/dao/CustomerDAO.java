package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.model.Customer;
import com.ecommerce.util.DBConnection;

public class CustomerDAO
{
	public void registerCustomer(Customer customer) throws ClassNotFoundException, SQLException
	{
		Connection connection = DBConnection.getConnection();

		String sql = "INSERT INTO customers(customer_name,email,mobile) VALUES(?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, customer.getCustomerName());
		ps.setString(2, customer.getEmail());
		ps.setString(3, customer.getMobile());

		int result = ps.executeUpdate();

		if (result > 0)
		{
			System.out.println("Customer Registered Successfully..!!");
		}

		ps.close();
		connection.close();

	}

	// Find Customer By ID
	public Customer findCustomerById(int customerId) throws SQLException, ClassNotFoundException
	{

		Connection connection = DBConnection.getConnection();

		String sql = "SELECT * FROM customers WHERE customer_id=?";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setInt(1, customerId);

		ResultSet rs = ps.executeQuery();

		Customer customer = null;

		if (rs.next())
		{

			customer = new Customer();
			customer.setCustomerId(rs.getInt("customer_id"));
			customer.setCustomerName(rs.getString("customer_name"));
			customer.setEmail(rs.getString("email"));
			customer.setMobile(rs.getString("mobile"));
			customer.setCreatedAt(rs.getTimestamp("created_at"));
			customer.setUpdatedAt(rs.getTimestamp("updated_at"));
		}

		rs.close();
		ps.close();
		connection.close();

		return customer;
	}

	// Find Customer By Email
	public Customer findCustomerByEmail(String email) throws ClassNotFoundException, SQLException
	{
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM customers WHERE email=?";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();

		Customer customer = null;

		if (rs.next())
		{
			customer = new Customer();

			customer.setCustomerId(rs.getInt("customer_id"));
			customer.setCustomerName(rs.getString("customer_name"));
			customer.setEmail(rs.getString("email"));
			customer.setMobile(rs.getString("mobile"));
			customer.setCreatedAt(rs.getTimestamp("created_at"));
			customer.setUpdatedAt(rs.getTimestamp("updated_at"));

		}

		rs.close();
		ps.close();
		connection.close();

		return customer; 
	}
}
