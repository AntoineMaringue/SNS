package SCANNSTOCK;

public class Produit 
{

	private String prix;
	private String description;
	private String marque;
	private String dateModification;
	private String dateCreation;
	
	private String _provenance;
	
	public Produit()
	{
		
	}
	
	public Produit(String prix, String description)
	{
		this.prix = prix;
		this.description = description;
	}
	
	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getDateModification() {
		return dateModification;
	}

	public void setDateModification(String dateModification) {
		this.dateModification = dateModification;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public String toString()
	{
		return "Marque : " + marque + "\n"
				+ "Description : " + description + "\n"
				+ "Date : " + dateModification + "\n"
				+ "Prix : " + prix + "\n";
		
	}
}
