package SERVEUR;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client 
{

	//public Client(Socket s, Serveur sns) 
	//{
	//	// TODO Auto-generated constructor stub
	//}
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{

        InetAddress adds = InetAddress.getByName("localhost");//nom de ID du serveur
        Socket comm = null;
        int port = 6669;
        
        DataInputStream dis = null;
        ObjectOutputStream oos = null;

        try {
            System.out.println("Demande de connexion ...");
            comm = new Socket(adds, port);
            System.out.println("Connexion etablie");
        } catch (IOException io) {
            System.exit(1);
        }
        try {
        	
        
            //on l'envoie au serveur
            DataOutputStream dos=new DataOutputStream(comm.getOutputStream());
            String isbn = "013456\n";
            dos.writeChars(isbn);

            //Envoie au serveur
           // oos = new ObjectOutputStream(comm.getOutputStream());
           // oos.writeObject(isbn);
            
            //Recois du serveur                
            dis = new DataInputStream(comm.getInputStream());
            System.out.println("-->" + dis.readDouble());
                  
        } 
        catch (IOException io) {
            System.exit(1);
        }
        }

}
