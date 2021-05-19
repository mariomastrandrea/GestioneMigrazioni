package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnect 
{
	private static final String jdbcURL = "jdbc:mariadb://localhost/countries";
	private static final HikariDataSource dataSource;
	private static final String username = "root";
	private static final String password = "root";
	
	static 
	{
		HikariConfig configuration = new HikariConfig();
		configuration.setJdbcUrl(jdbcURL);
		configuration.setUsername(username);
		configuration.setPassword(password);
		
		// mysql configuration
		configuration.addDataSourceProperty("cachePrepStmts", "true");
		configuration.addDataSourceProperty("preprStmtChacheSize", "250");
		configuration.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		dataSource = new HikariDataSource(configuration);
	}
	
	public static Connection getConnection() 
	{
		try 
		{
			return dataSource.getConnection();
		} 
		catch (SQLException sqle) 
		{
			System.err.println("Error DB Connection at " + jdbcURL);
			throw new RuntimeException("Error DB Connection at " + jdbcURL, sqle);
		}
	}
	
	public static void close(AutoCloseable... resources)
	{
		for(var resource : resources)
		{
			try
			{
				resource.close();
			}
			catch(Exception e)
			{
				throw new RuntimeException("Error closing resource: " + resource, e);
			}
		}
	}
}
