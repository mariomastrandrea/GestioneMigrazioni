package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO 
{	
	public List<Country> loadAllCountries(Map<Integer,Country> countriesMap) 
	{	
		final String sqlQuery = "SELECT ccode, StateAbb, StateNme " +
								"FROM country " +
								"ORDER BY StateAbb ";
				
		List<Country> resultList = new LinkedList<Country>();

		try 
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet queryResult = statement.executeQuery();
						
			while(queryResult.next()) 
			{
				Country c;
				int countryCode = queryResult.getInt("ccode");
				
				if(!countriesMap.containsKey(countryCode))
				{
					c = new Country(queryResult.getInt("ccode"),
									queryResult.getString("StateAbb"), 
									queryResult.getString("StateNme"));
							
					countriesMap.put(c.getcCode(), c);
				} 
				else 
					c = countriesMap.get(countryCode);
				
				resultList.add(c);
			}
			
			DBConnect.close(queryResult, statement, connection);
		
			return resultList;
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in loadAllCountries()", sqle);
		}
	}
	
	public List<Country> getCountriesFromYear(int anno, Map<Integer, Country> countriesMap) 
	{
		final String sql = "SELECT * FROM country " + 
							"WHERE CCode in (SELECT state1no " + 
											"FROM contiguity " + 
											"WHERE year<=? and conttype=1)";
		
		List<Country> resultList = new LinkedList<Country>();

		try 
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, anno);
			ResultSet queryResult = statement.executeQuery();
			
			while(queryResult.next()) 
			{
				int countryCode = queryResult.getInt("ccode");
				Country c;
				
				if(!countriesMap.containsKey(countryCode))
				{
					c = new Country(queryResult.getInt("ccode"),
									queryResult.getString("StateAbb"), 
									queryResult.getString("StateNme")) ;
					
					countriesMap.put(c.getcCode(), c);
				} 
				else 
					c = countriesMap.get(countryCode);
				
				resultList.add(c);
			}
			
			DBConnect.close(queryResult, statement, connection);
			return resultList;
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in getCountriesFromYear()", sqle);
		}
	}
	
	public List<Adiacenza> getCoppieAdiacenti(int anno) 
	{
		final String sql = "SELECT state1no, state2no " + 
							"FROM contiguity " + 
							"WHERE year <= ? AND conttype = 1 AND state1no < state2no";
		
		List<Adiacenza> result = new ArrayList<>();
		
		try 
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, anno);
			ResultSet queryResult = statement.executeQuery();
			
			while(queryResult.next()) 
			{
				Adiacenza a = new Adiacenza(queryResult.getInt("state1no"), queryResult.getInt("state2no"));
				result.add(a);
			}
			
			DBConnect.close(queryResult, statement, connection);
			return result;
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in getCoppieAdiacenti()", sqle);
		}
	}	
	
}
