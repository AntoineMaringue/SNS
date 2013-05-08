package DAO;
import API.ScanneurBarreCode;
import SCANNSTOCK.Produit;
import SCANNSTOCK.ScanNStock;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public final class Base implements IBase
{

	Connection con;
	PreparedStatement stmt;
    ResultSet rs;
    
    String _Ip, _Port, _Base;
    
	private static IBase instance;
	
	private Base()
	{
		_Ip = "localhost";
		_Port = "80";
		_Base = "test";
	}
	
	public static IBase getInstance()
	{
		synchronized (Base.class) 
		{
			if(instance == null)
			{
				instance = new Base();
			}
		}
		
		return instance;
	}
	
	@Override
	public boolean connection() 
	{
		try
		{
			String url = "jdbc:mysql://"+ _Ip +"/"+_Base+"";
	        //Class.forName("com.mysql.jdbc.Driver");
	        Class.forName ("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection(url,"root","");
	        

	        //stmt=con.prepareStatement("select * from site_geo ");
	        //String query="select * from site_geo";
	        //Statement st = (Statement) con.createStatement();
	        //ResultSet rs = st.executeQuery(query); 
	        //rs.next();
	        //System.out.println(rs.getObject("nom_sg"));
	    }

	    catch (Exception e) 
	    {
	        System.out.println(e);
	    }
		return true;
	}

	

	@Override
	public void deconnection() 
	{
		try 
		{
			if(!con.isClosed())
			{
				con.close();
			}
		} catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		
	}

	@Override
	public void selectVisual(String name) 
	{
		
		
	}

	@Override
	public void selectDesc(String name) 
	{
		
		
	}

	@Override
	public boolean connection(String id, String mdp) 
	{
		
		return true;
	}

	@Override
	public boolean insertInto(String table, Produit produit) 
	{
		String query = "";
		try 
		{
			if(!con.isClosed())
			{
				query = "INSERT INTO"+ table +"(nom_pdt, prix_pdt, id_cab)"
				+"VALUES "+"("+produit.getDescription()+","+produit.getPrix()+" ,"+ScanNStock.get_EAN()+" )";		
			
				Statement st = (Statement) con.createStatement();
				ResultSet rs = st.executeQuery(query); 
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

}
