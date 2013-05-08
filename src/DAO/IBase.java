package DAO;

import java.util.ArrayList;

import SCANNSTOCK.Produit;

/**
 * 
 * @author Antoine
 *
 */
public interface IBase 
{

	/**
     * Connection  base de donnees
     */
    public boolean connection();
    
    /**
     * 
     * @param id
     * @param mdp
     * @return
     */
    public boolean connection(String id , String mdp);
	
	    /**
     *Insertion de donn�es dans une table
     * @param table la table dans laquelle on souhaite inserer les donn�es
     * @param produit 
     * @return
     */
    public boolean insertInto(String table, Produit produit);

    /**
     * Deconnection d'une base
     */
    public void deconnection();
    
    /**
     *Selection et affichage d'une vue ou du contenu d'une table
     * @param name
     */
    public void selectVisual(String name);
    
    /**
     *Affichage de la description des �l�ments composant une vue ou une table
     * @param name
     */
    public void selectDesc(String name);
}
