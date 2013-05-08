package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Set;

import SCANNSTOCK.Produit;

public class ShoppingApi 
{
	
	private final static String googleUri="https://www.googleapis.com/shopping/search/v1/public/products?key=";
	private final static String keyGoogle = "AIzaSyAc7_OzUpgYJ2tg0_XLRY2FtTpbGpL1yIU";
	private final static String countryName = "FR";
	
	private String informations;
	private Produit _produit;
	
	public ShoppingApi()
	{
		informations = "";
	}	
	
	public boolean runSearchProduct(String ean)
	{
		IParseur parseur = null;
		Set<Entry<String, String>> informationsProduit = null;
		
		parseur = new ParseurJSON();
		informationsProduit = parseur.getInformations(requestHttpForGoogle(ean,"JSON")).entrySet();
		
		if(informationsProduit == null)
		{
			errorProduit();
			return false;
		}
		else
		{
			createProduit(informationsProduit);
			return true;
		}
		
	    	
	}
	
	private void createProduit(Set<Entry<String, String>> informationsProduit) 
	{
		//Affichage des donnees parsee
  	    for(Entry<String, String> en : informationsProduit) 
  	    {
		  	String cle = en.getKey();
		  	String valeur = en.getValue();

		  	System.out.println("KEY = " + cle );
		  	System.out.println("VALUE = " + valeur );
		  	
		  	//Construction du produit
		  	if(_produit == null)
		  	{
		  		_produit = new Produit();
		  	}
		  	if(cle.equals("brand"))
		  	{
		  		_produit.setMarque(valeur);
		  	}
		  	else if(cle.equals("modificationTime"))
		  	{
		  		_produit.setDateModification(valeur);
		  	}
		  	else if(cle.equals("creationTime"))
		  	{
		  		_produit.setDateCreation(valeur);
		  	}
		  	else if(cle.equals("description"))
		  	{
		  		_produit.setDescription(valeur);
		  	}
  	    }
	}

	private void errorProduit() 
	{
		//RETOURNER UNE ERREUR SI LE PRODUIT RECHERCHE NE RETOURNE AUCUN RESULTAT
		
	}

	public Produit getProduit()
	{
		return _produit;
	}
	
	public String getInformations()
	{
		return informations;
	}
	
	private String requestHttpForGoogle(String EAN,String format)
	{
		HttpURLConnection connection = null;  
		BufferedReader serverResponse = null;  
		
		String replyServer = "";
		
		String url = googleUri
				+keyGoogle
				+"&country="+countryName
				+"&q="+EAN
				+"&maxResults=1"
				+"&restrictBy=gin="+EAN
				+"&alt="+format;
  
		try  
		{  
			
			//OPEN CONNECTION  
			connection = ( HttpURLConnection ) 
					new URL( url ).openConnection();  
			  
			//RESPONSE STREAM  
			serverResponse = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );  
			  
			//READ THE RESPOSNE  
			String line;  
			while ( (line = serverResponse.readLine() ) != null )   
			{  
				replyServer += line+ "\n";  
			}  
			System.out.println(replyServer);
		}  
		catch (MalformedURLException mue)  
		{  
			mue.printStackTrace();  
		}  
		
		catch (IOException ioe)  
		{  
			ioe.printStackTrace();  
		}  
		finally  
		{  
			if ( connection != null )  
				connection.disconnect();  
			  
			if ( serverResponse != null )  
			{  
				try 
				{ 
					serverResponse.close(); 
				}
				catch (Exception ex) {}  
			}  
		}  	
		return replyServer;
	}

}
