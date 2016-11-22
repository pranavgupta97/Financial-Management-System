package com.fileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DatabaseInfo {

	public static final String url = "jdbc:mysql://cse.unl.edu/pgupta";
	public static final String username = "pgupta";
	public static final String password = "x2A-bn";
	private static Logger log=Logger.getLogger(DatabaseInfo.class.getName());
	static public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
//			System.out.println("InstantiationException: ");
//			e.printStackTrace();
//			throw new RuntimeException(e);
			log.error("InstantiationException: ",e);
		} catch (IllegalAccessException e) {
//			System.out.println("IllegalAccessException: ");
//			e.printStackTrace();
//			throw new RuntimeException(e);
			log.error("IllegalAccessException: ",e);
		} catch (ClassNotFoundException e) {
//			System.out.println("ClassNotFoundException: ");
//			e.printStackTrace();
//			throw new RuntimeException(e);
			log.error("ClassNotFoundException: ",e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			log.error("SQL Exception",e);
		}
		
		return conn;
	}
}
