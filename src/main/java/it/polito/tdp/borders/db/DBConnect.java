package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnect 
{
	private static final String jdbcURL = "jdbc:mariadb://localhost/countries";
	private static HikariDataSource ds = null;

	static 
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcURL);
		config.setUsername("root");
		config.setPassword("root");
		
		//configurazione mysql
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("preprStmtChacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}
	
	public static Connection getConnection() 
	{
		try 
		{
			return ds.getConnection();
		} 
		catch (SQLException sqle) 
		{
			System.err.println("Errore connessione al DB");
			throw new RuntimeException(sqle);
		}
	}
}
