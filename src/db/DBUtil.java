package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DBUtil {
	
	public static DBUtil singleton;
	
	private String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
			"databaseName=SABDB;integratedSecurity=true;"; 
	
	private RowSetFactory rowSetFactory;
	
	private DBUtil() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
			rowSetFactory = RowSetProvider.newFactory();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DBUtil getInstance() {
		if (singleton == null) {
			singleton = new DBUtil();
		}
		
		return singleton;
	}
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CachedRowSet executeQuery(String query) throws SQLException {
		return executeQuery(query, false);
	}
	
	public CachedRowSet executeQuery(String query, boolean insert) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionUrl);
		Statement statement = connection.createStatement();
		
		ResultSet resultSet = null;
		CachedRowSet result = rowSetFactory.createCachedRowSet();

		
		if (insert) {
			statement.executeUpdate(query);
		} else {
			resultSet = statement.executeQuery(query);
			result.populate(resultSet);
	
			resultSet.close();
		}
	
		statement.close();
		connection.close();
		
		return result;
	}

}
