package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	private final String serverName = "MINHTUNGUYEN\\MINHTU_SERVER";
	private final String dbName = "RestaurantManagement";
	private final String portNumber = "1433";
	//private final String instance = "MINHTU_SERVER";
	private final String userID = "sa";
	private final String password = "3052";
	
	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + ":" +
				portNumber + ";databaseName=" + dbName;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, userID, password);
	}
}
