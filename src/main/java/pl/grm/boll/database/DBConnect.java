package pl.grm.boll.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	ResultSet rs = null;
	Statement statement = null;
	private String dblogin = "BOL";
	private String dbpassword = "nPGU7t3v3TsJByNH";
	private String URL = "jdbc:mysql://91.230.204.135:3306/BattleOfLegends?user="
			+ dblogin + "&password=" + dbpassword;
	private Connection connection;
	private boolean connected = false;
	private String sel = "SELECT ", upd = "UPDATE ", wf = " FROM Users WHERE ";

	public DBConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void register(String login, char[] password) {
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			rs = statement.executeQuery(sel + "login" + wf + "login='" + login
					+ "';");
			if (rs.next()) {
				connected = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
				connected = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected() {
		return connected;
	}
}
