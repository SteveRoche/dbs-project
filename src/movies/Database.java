package movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;

public class Database {
	private static Database instance = null;
	private static Connection connection;
	private final String connStr = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String username;
	private static String password;

	private Database() {
		try {
			DriverManager.registerDriver(new OracleDriver());
			connection = DriverManager.getConnection(connStr, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void configure(String username, String password) {
		Database.username = username;
		Database.password = password;
	}

	public static synchronized Connection conn() {
		if (instance == null) {
			instance = new Database();
		}
		return connection;
	}
}