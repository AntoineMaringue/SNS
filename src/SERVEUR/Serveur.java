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
                //1. creating a server socket
                providerSocket = new ServerSocket(5000, 10);
                //2. Wait for connection
                System.out.println("Waiting for connection");
                connection = providerSocket.accept();
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());
                //3. get Input and Output streams
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                sendMessage("Connection successful");
                //4. The two parts communicate via the input and output streams
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
                            String idmdp = message.split(";")[1];
                            sendMessage("traitement" + idmdp);
                        } else if (message.contains("associations")) {
                            ArrayList<String> lst = new ArrayList<>();
                            //lst.add("association1");
                            //lst.add("association2");
                            //lst.add("association3");
                            Traitement t = new Traitement();
                            lst.addAll(t.getListSites());
                            for (String string : lst) {
                                message += string + ";";
                            }
                            sendMessage("ASSS" + message);
                        } 
                        else if (message.contains("isbn")) 
                        {
                            String isbn = message.split(",")[1];
                            Traitement t = new Traitement(isbn);
                            System.out.println("Recherche des informations du produit");
                            boolean searchInfos = t.searchInfos();

                            message = "Infos sur le produit trouvée : " + searchInfos + "";
                            if (searchInfos) {
                                    //Création du produit
                                    //Message renvoyé au téléphone CREATION AUTO ou PASSAGE FORM SITE
                            System.out.println("Création de l'entitée produit");
                                    //Insertion dans la base
                                    //Message renvoyé au téléphone Si CREATION AUTO --> INSERTION OK | KO
                            System.out.println("Insertion dans la base");
                            boolean insertProduct = t.insertToBdd();

                            message += ("Insertion dans la base : " + insertProduct + "");

                            } 
                            else 
                            {
                                message += "Le produit scanné n'a pas été trouvé par l'api google, "
                                            + "merci d'insérer le produit via l'interface web ! ";
                            }
                                sendMessage(message);
                        }
                        else {
                            sendMessage(message + "OK");
                        }
                    } catch (ClassNotFoundException classnot) {
                        System.err.println("Data received in unknown format");
                    }
                } while (!message.equals("bye"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                //4: Closing connection
                try {
                    in.close();
                    out.close();
                    providerSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        void sendMessage(String msg) {
            try {
                out.writeObject(msg);
                out.flush();
                System.out.println("server>" + msg);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
            private void sendNothing(String string) {
            try {
                out.writeObject(string);
                out.flush();
                //System.out.println("server>" + msg);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }       
        }
    }
