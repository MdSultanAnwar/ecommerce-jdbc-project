package com.ecommerce.model;

import java.sql.Timestamp;
public class Customer
{
	private int customerId;
	private String customerName;
	private String email;
	private String mobile;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Customer()
	{
	}

	public Customer(String customerName, String email, String mobile)
	{
		super();
		this.customerName = customerName;
		this.email = email;
		this.mobile = mobile;
	}

	public Customer(int customerId, String customerName, String email, String mobile, Timestamp createdAt,
			Timestamp updatedAt)
	{
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
		this.mobile = mobile;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public Timestamp getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt)
	{
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt)
	{
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString()
	{
		return "Customer{" + "customerId=" + customerId + ", customerName='" + customerName + '\'' + ",  email='" + email
				+ '\'' + ", mobile='" + mobile + '\'' + '}';
	}
}
