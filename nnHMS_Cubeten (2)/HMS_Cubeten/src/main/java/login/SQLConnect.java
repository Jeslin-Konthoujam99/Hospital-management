package login;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnect {
	Connection con = null;

	public Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/hmss?useSSL=false", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("not connected");
		}
		return con;
	}

}
