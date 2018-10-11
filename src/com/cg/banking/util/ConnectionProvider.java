package com.cg.banking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider { //gon use this class to establish a connection
	
	//instead of using vendor classes, use interface ka reference to allow loose coupling.
	private static Connection conn;
	public static Connection getDBConnection(){
		try{
			Properties dbProperties = new Properties();
			dbProperties.load(new FileInputStream(new File(".//resources//banking.properties")));
			String driver = dbProperties.getProperty("driver");
			String url = dbProperties.getProperty("url");
			String user = dbProperties.getProperty("user");
			String password = dbProperties.getProperty("password");
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
