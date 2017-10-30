package com.yuan.www.uti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	public static Connection getConnection(){
		Connection connection=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test_mysql", "root", "123456");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
