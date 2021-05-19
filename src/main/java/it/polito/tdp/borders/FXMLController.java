
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController 
{	
    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private TextField txtAnno; 

    @FXML 
    private ComboBox<Country> boxNazione; 

    @FXML 
    private TextArea txtResult; 
    
	private Model model;


    @FXML
    void doCalcolaConfini(ActionEvent event) 
    {
    	this.txtResult.clear();
    	String annoS = this.txtAnno.getText();
    	
    	int anno;
		try 
		{
			anno = Integer.parseInt(annoS);
		} 
		catch (NumberFormatException nfe) 
		{
			this.txtResult.appendText("Errore di formattazione dell'anno\n");
			return;
		}
		
		this.model.creaGrafo(anno);
		
		this.boxNazione.getItems().addAll(this.model.getCountries());
		
		for(CountryAndNumber c : this.model.getCountryAndNumbers())
		{
			this.txtResult.appendText(c.toString() + "\n");
		}
    }

    @FXML
    void doSimula(ActionEvent event) 
    {
    	
    	this.txtResult.clear();
    	Country partenza = boxNazione.getValue();
    	if(partenza == null) 
    	{
    		this.txtResult.setText("SELEZIONA STATO!\n");
    		return;
    	}
    	
    	this.txtResult.appendText("SIMULAZIONE A PARTIRE DA: " + partenza + "\n\n");
    	
    	this.model.Simula(partenza);
    	
    	this.txtResult.appendText("Numero di passi simulati: " + this.model.getT() + "\n\n");
    	this.txtResult.appendText("Nazioni con migranti stanziali:\n");
    	
    	for(CountryAndNumber cn : this.model.getStanziali())
    	{
    		this.txtResult.appendText(cn.toString() + "\n");
    	}
    }

    @FXML 
    void initialize() 
    {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
    }
    
    public void setModel(Model model) 
    {
    	this.model = model;
    }
}
