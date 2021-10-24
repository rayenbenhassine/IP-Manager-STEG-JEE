package com.java.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBD 
{
	private Connection connection_var;
	public ConnexionBD()
	{
		charger_driver();
		try
		{
			connection_var = DriverManager.getConnection("jdbc:mysql://localhost/gestionip","root",""); //connecting to the database
			System.out.println("Connection Successful");
			
		} 
		catch (SQLException e) 
		{
			System.out.println("Some problem in connection");
			e.printStackTrace();
		}
	}
	
	private void charger_driver()
	{
	    try
	    {
	        Class.forName("com.mysql.jdbc.Driver");
	    }
	    catch(ClassNotFoundException e)
	    {
	        System.err.println(e);
	    }
	}
	
	public Connection getConnexion()
	{
		return this.connection_var;
	}

	public static void executeQuery(String sql)
	{
		try 
		{
			ConnexionBD con = new ConnexionBD();
			Statement statement = con.getConnexion().createStatement();
			statement.execute(sql);
			con.getConnexion().close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeSelectQuery(String sql)
	{
		try 
		{
			ConnexionBD con = new ConnexionBD();
			Statement statement = con.getConnexion().createStatement();
			ResultSet result = statement.executeQuery(sql);
			return result;

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
