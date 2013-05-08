package API;

import java.util.Date;

public interface IScanneurBarreCode 
{
	/**
	 * Scan des dates avec le telephone
	 */
	public void runScanningDate();
	
	/**
	 * Scan l'image prise avec le telephone android
	 * 
	 * @param multiBc : false = 1 seul ; true = plusieurs
	 */
	public void runScan(boolean multiBc);
	
	/**
	 * Obtenir le code EAN du produit
	 * 
	 * @return : retourne le code EAN
	 */
	public String getCodeEAN();
	
	/**
	 * Obtenir la date scann�e
	 * 
	 * @return
	 */
	public Date getScanningDate();
	
	/**
	 * Conversion de la chaine de caract�re date en vrai date
	 * 
	 * @param dateStr : year-mois-jour
	 * @return
	 */
	public Date getDate(String dateStr);
}
