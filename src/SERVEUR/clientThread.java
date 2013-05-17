/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SERVEUR;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Antoine
 */
 class clientThread extends Thread 
 {

    private String clientName = null;
    private BufferedReader br = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private String isbn;

    public clientThread()
    {
        this.threads = new clientThread[1];
    }
    
    public clientThread(Socket clientSocket, clientThread[] threads) 
    {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }


     @Override
    public void run() 
    {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            /*
             * Création d'un input et d'un output par client
             * is = ce que l'on reçois du client
             * os ce que l'on enverra au client
             */
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            br = new BufferedReader(isr);
            
            os = new PrintStream(clientSocket.getOutputStream());
            
                        
            /**
             * Boucle jusqu'a envoi du ISBN per le téléphone
             */
            while (true) 
            {
                os.println("Envoyer le ISBN du code à barre :\n");
                isbn = br.readLine().trim();
                if (isbn != null) 
                {
                    break;
                }
                else {
                    os.println("ISBN non valide, renvoyé en un autre.");
                }
            }

            /* ISBN OK */
            os.println("ISBN reçue par le serveur : " + isbn
                    + "\n");
           
            /* Démarrage du traitement */
            while (true) 
            {                  
                System.out.println("Entrée dans le traitement");
                /* If the message is private sent it to the given client. 
                if (line.startsWith("@")) {
                    String[] words = line.split("\\s", 2);
                    if (words.length > 1 && words[1] != null) {
                        words[1] = words[1].trim();
                        if (!words[1].isEmpty()) {
                            synchronized (this) {
                                for (int i = 0; i < maxClientsCount; i++) {
                                    if (threads[i] != null && threads[i] != this
                                            && threads[i].clientName != null
                                            && threads[i].clientName.equals(words[0])) {
                                        threads[i].os.println("<" + name + "> " + words[1]);
                                        
                                        this.os.println(">" + name + "> " + words[1]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    */
                    synchronized (this) 
                    {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].isbn != null) 
                            {
                                //Traitement du ISBN envoyé par le serveur
                                System.out.println("Traitement du ISBN envoyé par le serveur");
                                //Recherche des informations du produit
                                    //Message renvoyé au téléphone TROUVE ou NON
                                System.out.println("Recherche des informations du produit");
                                //Création du produit
                                    //Message renvoyé au téléphone CREATION AUTO ou PASSAGE FORM SITE
                                System.out.println("Création de l'entitée produit");
                                //Insertion dans la base
                                    //Message renvoyé au téléphone Si CREATION AUTO --> INSERTION OK | KO
                                System.out.println("Insertion dans la base");
                                
                                //Message renvoyé au téléphone                               
                                threads[i].os.println("*"+isbn+"*");                                
                                //this.os.println("(" + isbn + ")");
                            }
                        }
                    }
                    
                    //Si le isbn contient la chaine /quit on arrete le traitement
                    if (isbn.contains("/quit")) 
                    {
                        break;
                    }
                //}
            }
            
            /**
             * Fin de connexion du client
             */
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) 
                {
                    if (threads[i] != null && threads[i] == this
                            && threads[i].isbn != null) 
                    {
                        threads[i].os.println("*** Client déconnecté !!! ***");
                        System.out.println("Client déconnecté ...");
                    }
                }
            }
            os.println("*** Fin Traitement");

            /*
             * Clean up.
             */
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
            /*
             * Fermeture des flux et du socket
             */
            
           
            br.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}