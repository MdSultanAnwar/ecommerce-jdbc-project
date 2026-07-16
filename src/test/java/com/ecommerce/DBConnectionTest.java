package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.ecommerce.util.DBConnection;

public class DBConnectionTest
{
	@Test
	public void testConnection() throws ClassNotFoundException, SQLException
	{
		Connection connection = DBConnection.getConnection();
		assertEquals(false, connection.isClosed());
		connection.close();
	}

}
