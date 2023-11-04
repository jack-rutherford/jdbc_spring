package edu.hope.cs.csci392.imdb;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	private static final String DRIVER_CLASS = "";
	private static String connectionString = "jdbc:sqlserver://sql.cs.hope.edu\\CSSQL:1433;user=you;password=secret";
	static {
		//  Use class.forName to ensure that the classes implementing the JTDS JDBC driver are loaded
		try {
			Class.forName(DRIVER_CLASS);
			driverFound = true;
		} catch (ClassNotFoundException e) {
			driverFound = false;
		}
	}
	
	static boolean driverFound;
	
	/**
	 * Creates a java.sql.Connection object that is attached to a database.
	 * 
	 * @return
	 */
	public static Connection getConnection () {
		if (!driverFound) {
			throw new RuntimeException ("Could not find the JDBC driver (" + DRIVER_CLASS + ").  Please ensure you have the appropriate JAR file on your class path");
		}
		
		try {
			return DriverManager.getConnection(connectionString);
		}
		catch (Exception e) {
			return null;
		}
	}
}
