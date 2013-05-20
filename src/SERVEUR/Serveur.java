/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testserver;

import SERVEUR.InitServeur;

/**
 *
 * @author Antoine
 */
public class Serveur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        InitServeur is = new InitServeur();
        is.connexion();     
        
        
        
        /*
        ServerSocket echoServer = null;
        String line;
        DataInputStream is;
        PrintStream os;
        Socket clientSocket = null;

        
        try {
            echoServer = new ServerSocket(2222);
        } catch (IOException e) {
            System.out.println(e);
        }

        
        System.out.println("The server started. To stop it press <CTRL><C>.");
        try {
            clientSocket = echoServer.accept();
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());

           
            while (true) 
            {
                line = is.readLine();
                if(line != null)
                {
                    Traitement t = new Traitement(line);
                    t.run();
                    
                    System.out.println("From Client :" + t.getS());
                }
                os.println("From server: " + line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }*/
    }
}
