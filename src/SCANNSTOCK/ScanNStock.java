package SCANNSTOCK;

import API.ShoppingApi;
import DAO.Base;
import DAO.IBase;

public class ScanNStock implements IScanNStock
{
	
	private IBase _basePersoScanNStock;
	private ShoppingApi _shop;
	private static String _EAN;
	
	public static String get_EAN() {
		return _EAN;
	}

	private Produit produit;
	private String _tableName;
	
	public ScanNStock()
	{
		_shop = new ShoppingApi();		
		_basePersoScanNStock = Base.getInstance();
		_basePersoScanNStock.connection();
	}
	
	public ScanNStock(String isbn)
	{
		_shop = new ShoppingApi();	
		_basePersoScanNStock = Base.getInstance();
		_EAN = isbn;
		_basePersoScanNStock.connection();
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
	public boolean InsertToBase() 
	{
		boolean b = false;
		
		//Insertion des valeurs dans la base
		b = _basePersoScanNStock.insertInto(_tableName, produit);
		
		//D�connection de la base
		_basePersoScanNStock.deconnection();
		
		return b;
	}

	public boolean launch() 
	{
		Boolean validateProduct = false, validateInBase = false;
		validateProduct = getInfosProduct();
		validateInBase = InsertToBase();
		if(validateProduct && validateInBase)
			return true;
		else
			return false;
	}
}
