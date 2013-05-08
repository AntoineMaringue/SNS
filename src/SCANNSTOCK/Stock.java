package SCANNSTOCK;

import java.util.ArrayList;
import java.util.List;

public class Stock 
{
	
	private Integer _idStock;
	private Integer _idSiteGeo;
	private String _superficieStock;
	private List<Produit> _lstProduits;
	
	public Stock()
	{
		_lstProduits = new ArrayList<Produit>();
	}
	
	public void addProduit(Produit produit)
	{
		_lstProduits.add(produit);
	}
	
	public Integer get_idStock() {
		return _idStock;
	}

	public void set_idStock(Integer _idStock) {
		this._idStock = _idStock;
	}

	public Integer get_idSiteGeo() {
		return _idSiteGeo;
	}

	public void set_idSiteGeo(Integer _idSiteGeo) {
		this._idSiteGeo = _idSiteGeo;
	}

	public String get_superficieStock() {
		return _superficieStock;
	}

	public void set_superficieStock(String _superficieStock) {
		this._superficieStock = _superficieStock;
	}

	public List<Produit> get_lstProduits() {
		return _lstProduits;
	}

	public void set_lstProduits(List<Produit> _lstProduits) {
		this._lstProduits = _lstProduits;
	}

	public Integer get_qteProduits() {
		return _lstProduits.size();
	}

}
