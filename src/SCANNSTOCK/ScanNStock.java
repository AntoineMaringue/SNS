package SCANNSTOCK;

import API.ShoppingApi;
import fr.sciencesu.sns.hibernate.jpa.Produit;
import fr.sciencesu.sns.hibernate.test.BDD;

public class ScanNStock implements IScanNStock
{
	
	private ShoppingApi _shop;

	private static String _EAN;

	private Produit produit = null;	
    private boolean connexion;
	
	public ScanNStock()
	{
		//_shop = new ShoppingApi();
                
                //Test.connection();
	}
	
	public ScanNStock(String isbn)
	{
		_shop = new ShoppingApi();
		
		_EAN = isbn;
		
                connection();
	}
        
        public void connection()
        {
            BDD.connection();
            connexion = true;
            
        }
        
        public void deconnexion()
        {
            BDD.deconnection();
            connexion = false;
            
        }
        
        public boolean isConnected()
        {
            return connexion;
        }

	@Override
	public boolean getInfosProduct() 
	{
		boolean b = false;
		//Recuperation des informations du produit
		b = _shop.runSearchProduct(_EAN);
		
		//Recuperation du produit instancie
		produit = _shop.getProduit();
                
		return b;
	}

	@Override
	public boolean InsertToBase(String idstock) 
	{
		boolean b = true;                
                
		
		//Insertion des valeurs dans la base
		b = BDD.Create("Produit", produit);
                
                BDD.UpdateProduit("Produit",produit.getNom(), "produits_stock_stocks_id", idstock);
                        //basePersoScanNStock.insertInto(_tableName, produit);
		
		//D�connection de la base
		deconnexion();
                //_basePersoScanNStock.deconnection();
		
		return b;
	}
        
        public static String get_EAN() 
        {
		return _EAN;
	}
        
        
}
