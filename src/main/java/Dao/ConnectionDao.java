package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDao {
	private static final String URL="jdbc:mysql://localhost:3306/university";
	private static final String USER="root";
	private static final String PASSWORD="root";
	private static final String DRIVER="com.mysql.cj.jdbc.Driver";
	
	static {
		try {
			Class.forName(DRIVER);
			//To load our Driver class on calling geConnection() , as this is static block will be automatically load
		} catch (ClassNotFoundException e) {
			System.out.println("Class couldn't be loaded");
		}
	}
	
	public static Connection getConnection() throws SQLException {
		Connection c=DriverManager.getConnection(URL, USER, PASSWORD);
		return c;
	}
}
