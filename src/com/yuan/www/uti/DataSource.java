package com.yuan.www.uti;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class DataSource {
	// 初始值
	private int original = 5;
	// 最小值
	private int min = 3;
	// 最大值
	private int max = 20;
	// 不够用的时候，增加值
	private int add = 5;
	private Vector<Connection> restPool;
	private Vector<Connection> usePool;
	private ThreadLocal<Connection> local = new ThreadLocal<>();
	private static DataSource dataSource = null;

	private DataSource() {
		restPool = new Vector<>(original);
		usePool = new Vector<>();
		addRestPool(original);

	}

	public static synchronized DataSource getInstance() {
		if (dataSource == null) {
			dataSource = new DataSource();
		}
		return dataSource;
	}

	public synchronized Connection getConnection() {
		Connection connection = pushConnection();

		local.set(connection);

		usePool.add(connection);

		return connection;

	}

	public Connection getCurrentConnecton() {
		// 默认线程里面取
		
		
		
		Connection conn = local.get();
		if (!isValid(conn)) {
			conn = getConnection();
		}
		return conn;
	}

	private boolean isValid(Connection conn) {
		try {
			if (conn == null || conn.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void addRestPool(int size) {
		for (int i = 0; i < size; i++) {
			restPool.add(JDBCUtil.getConnection());
			
			if (restPool.size() > max) {
				break;
			}
		}
	}

	private Connection pushConnection() {
		System.out.println(Thread.currentThread().getName() + ":"
				+ restPool.size());
		Connection connection = null;

		if (restPool.size() > 0) {
			connection = restPool.remove(0);
		} else {
			addRestPool(add);
			connection = pushConnection();
		}

		return connection;
	}

	public void close(Connection connection) {
		
		usePool.remove(connection);

		if (restPool.size() < max) {
			restPool.add(connection);
		}else{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public int getOriginal() {
		return original;
	}

	public void setOriginal(int original) {
		this.original = original;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

}
