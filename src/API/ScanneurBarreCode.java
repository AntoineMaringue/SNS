package API;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

//import com.asprise.util.ocr.OCR;


public class ScanneurBarreCode implements IScanneurBarreCode
{
	
	//private final String pathLibOcr = "\\TestApiGoogleSearch\\AspriseOCR.dll";
	private final String filePathImg = "C:\\Users\\Antoine\\Downloads\\Asprise-OCR-Java-Windows_XP_64bit-4.0\\sample-images\\ocr.gif";
	private String _codeEan;
	private Vector<?> _multiBarreCode;
	private Calendar _scanningDate;
	
	/**
	 * Constructeur de la classe barre code
	 */
	public ScanneurBarreCode()
	{
		_codeEan = "";
		_scanningDate = Calendar.getInstance();
		_multiBarreCode = new Vector<>();
	}
	
	public void runScanningDate()
	{
		/*
		BufferedImage image = null;
		OCR ocr = null;	
		try 
		{
			ocr = new OCR();
			image = ImageIO.read(new File(filePathImg));	
			String strDate = ocr.recognizeCharacters(image);			
			_scanningDate.setTime(getDate(strDate));
			System.out.println("Date: \n"+ _scanningDate.getTime());	
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}*/
	}
	
	public Date getDate(String s)
	{ 
	       Date d = null; 
	       String nouveau = s.substring(6, 10)+"-"+s.substring(3, 5)+"-"+s.substring(0, 2); //annee, mois et jour 
	       try 
	       { 
	    	   d = new Date(nouveau); 
	       } 
	       catch (Exception ex) 
	       { 
	           JOptionPane.showMessageDialog(null,"La date n'est pas correcte" 
	                   , "AVERTISSEMENT", JOptionPane.WARNING_MESSAGE); 
	       } 
	       return d; 
	    }

	public void runScan(boolean multiBc)
	{
		/*
		BufferedImage image = null;
		OCR ocr = null;
		//OCR.setLibraryPath(pathLibOcr);
		
		try 
		{
			ocr = new OCR();
			image = ImageIO.read(new File(filePathImg));	
			
			//image = image.getSubimage(0, 0, 200, 100);
			
			if(!multiBc)
			{
				_codeEan = ocr.recognizeBarcode(image);			
			 
				System.out.println("RESULTS: \n"+ _codeEan);	
			}
			else
			{
				_multiBarreCode = ocr.recognizeBarcodes(image);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();*/
	}
	
	public String getCodeEAN()
	{
		return _codeEan;
	}
	
	public Date getScanningDate()
	{
		return _scanningDate.getTime();
	}
	
}
