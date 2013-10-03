/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SERVEUR;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Antoine
 */
public class Serveur {

    public static void main(String args[]) {
        Serveur server = new Serveur();
        while (true) {
            server.run();
        }
    }
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    Serveur() {
    }

    void run() {
        try {
            //Initialisation de la socket serveur
            providerSocket = new ServerSocket(5000, 10);
            //2. Attente de connexion d'un client
            System.out.println("Attente de connexion d'un client SNS");
            connection = providerSocket.accept();
            System.out.println("Connexion de l'hôte " + connection.getInetAddress().getHostName());
            //3. recupération des infos d'entrées / sorties
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection avec le serveur établit");
            //4. Communication avec les traitements envoyés par le client
            do {
                try {
                    message = (String) in.readObject();
                    if (!message.equals("_")) {
                        System.out.println("client>" + message);
                    }
                    if (message.equals("bye")) {
                        sendMessage("bye");
                    } else if (message.equals("_")) {
                        sendNothing("");
                    } else if (message.contains("Validation")) {
                        //String idmdp = message.split(";")[1];
                        //sendMessage("traitement" + idmdp);
                        String asso = message.split(",")[1];
                        Traitement t = new Traitement();
                        message = t.getIdStock(asso);
                        sendMessage(message);
                    }                   
                    else if (message.contains("associations")) {
                        ArrayList<String> lst = new ArrayList<>();
                        Traitement t = new Traitement();
                        lst.addAll(t.getListSites());
                        for (String string : lst) {
                            message += string + ";";
                        }
                        sendMessage("ASSS" + message);
                    } else if (message.contains("isbn")) {
                        String isbn = message.split(",")[1].split(";")[0]; 
                        String idstock = message.split(",")[1].split(";")[1];                        
                        Traitement t = new Traitement(isbn,idstock);
                        System.out.println("Recherche des informations du produit");
                        boolean searchInfos = t.searchInfos();
                        if (searchInfos) {
                            //Création du produit
                            //Message renvoyé au téléphone CREATION AUTO ou PASSAGE FORM SITE
                            message = "Infos sur le produit trouvées \n ";
                            System.out.println("Création de l'entitée produit");
                            //Insertion dans la base
                            //Message renvoyé au téléphone Si CREATION AUTO --> INSERTION OK | KO
                            System.out.println("Insertion dans la base");
                            boolean insertProduct = t.insertToBdd();
                            message += (insertProduct) ? "Insertion dans la base \n" : "";

                        } else {
                            message += "Le produit scanné n'a pas été trouvé par l'api google,\n "
                                    + "merci d'insérer le produit via l'interface web !\n ";
                        }
                        sendMessage(message);
                    } else {
                        sendMessage(message + "OK");
                    }
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (IOException ioException) {
        } finally {
            //4: Closing connection
            try {
                in.close();
                out.close();
                providerSocket.close();
            } catch (IOException ioException) {
            }
        }
    }

    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
        }
    }

    private void sendNothing(String string) {
        try {
            out.writeObject(string);
            out.flush();
            //System.out.println("server>" + msg);
        } catch (IOException ioException) {
        }
    }
}
