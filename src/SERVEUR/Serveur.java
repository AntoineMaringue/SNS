package SERVEUR;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Vector;

import SCANNSTOCK.ScanNStock;

public class Serveur 
{
	
	private Vector<Object> _tabClients = new Vector<Object>(); // contiendra tous les flux de sortie vers les clients
	private int _nbClients=0; // nombre total de clients connectes
    
	public static void main(String[] args) 
	{
		connection(args);
    }
	

	  //** Methode : detruit le client no i **
	  synchronized public void delClient(int i)
	  {
	    _nbClients--; // un client en moins ! snif
	    if (_tabClients.elementAt(i) != null) // l'element existe ...
	    {
	      _tabClients.removeElementAt(i); // ... on le supprime
	    }
	  }

	  //** Methode : ajoute un nouveau client dans la liste **
	  synchronized public int addClient(PrintWriter out)
	  {
	    _nbClients++; // un client en plus ! ouaaaih
	    _tabClients.addElement(out); // on ajoute le nouveau flux de sortie au tableau
	    return _tabClients.size()-1; // on retourne le numero du client ajoutes (size-1)
	  }

	  //** Methode : retourne le nombre de clients connect�s **
	  synchronized public int getNbClients()
	  {
	    return _nbClients; // retourne le nombre de clients connect�s
	  }
	
	public static void connection(String[] args)
	{
		Integer port = null; // le port du serveur
		Serveur srvSns = null; // instance de la classe principale pour permettre plusieurs instances
		ServerSocket ss = null; // ouverture du serveur sur un port ad�quate
	    try
	    {
	    	srvSns = new Serveur(); 
	    	if(args.length<=0) port = new Integer("6669"); // si pas d'argument : port 8638 par d�faut
	    	else port = new Integer(args[0]); // sinon il s'agit du num�ro de port pass� en argument


	    	ss = new ServerSocket(port.intValue()); // ouverture d'un socket serveur sur port
	    	System.out.println("Attente de client ...");
	    	while (true) // attente en boucle de connexion (bloquant sur ss.accept)
	    	{
	    		new ClientThread(ss.accept(),srvSns); // un client se connecte, un nouveau thread client est lanc�
	    	}
	    }
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }
	}


	synchronized public boolean traitement(String isbn) 
	{
		
		ScanNStock sns = new ScanNStock(isbn);
		return sns.launch();

	}
}
