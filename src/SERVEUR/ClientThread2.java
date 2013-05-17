package SERVEUR;
import java.net.*;
import java.io.*;


class ClientThread2 implements Runnable
{
	
  private Thread _t; // contiendra le thread du client
  private Socket _s; // recevra le socket liant au client
  private PrintWriter _out; // pour gestion du flux de sortie
  private BufferedReader _in; // pour gestion du flux d'entree
  private ServeurBis _servSns; // pour utilisation des methodes de la classe principale
  private int _numClient=0; // contiendra le numero de client gere par ce thread

  
  public ClientThread2(Socket s, ServeurBis sns)
  {
	  
    _servSns=sns; 
    _s=s;
    
    try
    {
    	
      // fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
      _out = new PrintWriter(_s.getOutputStream());
      
      // fabrication d'une variable permettant l'utilisation du flux d'entr�e avec des string
      _in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
      
      // ajoute le flux de sortie dans la liste et r�cup�ration de son numero
      _numClient = sns.addClient(_out);
    }
    catch (IOException e){ }

    _t = new Thread(this); // instanciation du thread
    _t.start(); // demarrage du thread, la fonction run() est ici lanc�e
  }


  public void run()
  {
	  //R�cup�ration des informations envoy�e par le t�l�phone android
	  //BufferedReader br = null;
	  String isbn = "";
	  //Envoi des donn�es sur le (status) des traitements effectu� par le serveur
	  DataOutputStream dos = null;
	  
	  	// on indique dans la console la connection d'un nouveau client
	    System.out.println("Un nouveau client s'est connecte, no "+_numClient);
	    try
	    {
	    	//r�cup�ration du code envoy� par le smartphone
	    	//String isbn = _in.readLine();
	    	//br = new BufferedReader(new InputStreamReader(_s.getInputStream()));
	    	
	    	isbn = _in.readLine();//br.readLine();
	    	if(!isbn.isEmpty() || !isbn.equals("q"))
	    	{
	    		dos=new DataOutputStream(_s.getOutputStream());
	            dos.writeBoolean(_servSns.traitement(isbn));            
	    	}
	    	else if(isbn.equals("q"))
	    	{
	    		//System.exit(1);
	    	}
	      
	    }
	    catch (Exception e){ }
	    finally // finally se produira le plus souvent lors de la deconnexion du client
	    {
	      try
	      {
	      	// on indique � la console la deconnexion du client
	        System.out.println("Le client no "+_numClient+" s'est deconnecte");
	        _servSns.delClient(_numClient); // on supprime le client de la liste
	        _s.close(); // fermeture du socket si il ne l'a pas d�j� �t� (� cause de l'exception lev�e plus haut)
	      }
	      catch (IOException e){ }
	    }
  	}
}