package SCANNSTOCK;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;

import API.IParseur;
import API.ParseurJSON;
import DAO.Base;
import DAO.IBase;

public class Main
{
	
	public static void main(String[] args) 
	{
		//ScanNStock sns = new ScanNStock();
		//sns.launch();
		
		/*IParseur p = new ParseurJSON();
		String s = "";
		try{
			InputStream ips=new FileInputStream("C:\\Users\\Antoine\\Desktop\\t.txt"); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				//System.out.println(ligne);
				s+=ligne;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		
		for(Entry<String, String> en : p.getInformations(s).entrySet()) 
  	    {
		  	String cle = en.getKey();
		  	String valeur = en.getValue();

		  	System.out.println("KEY = " + cle );
		  	System.out.println("VALUE = " + valeur );
		  	
		  	//Envoi des donnees dans la base de donnees afin de les stocker
		  	
		  	
	    }	*/
		IBase base = Base.getInstance();
		base.connection();
	}
}
