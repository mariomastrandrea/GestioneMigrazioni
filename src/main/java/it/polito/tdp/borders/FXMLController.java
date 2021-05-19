package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FXMLController 
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField annoTextField;

    @FXML
    private Button calcolaConfiniButton;

    @FXML
    private ComboBox<Country> nazioneComboBox;

    @FXML
    private Button avviaSimulazioneButton;

    @FXML
    private TextArea resultTextArea;

	private Model model;

	
    @FXML
    void handleAnnoTyping(KeyEvent event) 
    {
    	String annoInput = this.annoTextField.getText();
    	
    	if(annoInput == null || annoInput.isBlank())
	    	this.showViewAfterSelection(false);
    	else    	
    		this.showViewAfterSelection(true);
    }

    private void showViewAfterSelection(boolean show)
    {
    	boolean disable = !show;
    	
    	this.calcolaConfiniButton.setDisable(disable);
    	this.nazioneComboBox.setDisable(disable);
    }
    
    @FXML
    void handleCalcolaConfini(ActionEvent event) 
    {
    	String annoInput = this.annoTextField.getText();
    	
    	int anno;
		try 
		{
			anno = Integer.parseInt(annoInput);
		} 
		catch (NumberFormatException nfe) 
		{
			this.resultTextArea.appendText("Errore: \"" + annoInput + "\" non è un anno valido\n");
			return;
		}
		
		this.model.creaGrafo(anno);
		
		this.nazioneComboBox.getItems().clear();
		this.nazioneComboBox.getItems().addAll(this.model.getCountries());
		
		String output = this.printCountryAndNumbers(this.model.getCountryAndNumbers(), "stati confinanti");
		this.resultTextArea.setText(output);
    }
    
    private String printCountryAndNumbers(Collection<CountryAndNumber> countries, String numLabel)
    {
    	StringBuilder sb = new StringBuilder();
		
		for(CountryAndNumber c : countries)
		{
			sb.append(c.toString()).append(" ").append(numLabel).append("\n");
		}
		
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1); //delete trailing '\n'
		
		return sb.toString();
    }

    @FXML
    void handleNazioneSelected(ActionEvent event) 
    {
    	Country selectedCountry = this.nazioneComboBox.getValue();
    	
    	if(selectedCountry == null)
    		this.avviaSimulazioneButton.setDisable(true);
    	else
    		this.avviaSimulazioneButton.setDisable(false);
    }

    @FXML
    void handleStartSimulation(ActionEvent event) 
    {
    	Country partenza = this.nazioneComboBox.getValue();
    	
    	if(partenza == null) 
    	{
    		this.resultTextArea.setText("Errore: selezionare una nazione");
    		return;
    	}
    	
    	this.model.Simula(partenza);
    	
    	int T = this.model.getT();
    	Collection<CountryAndNumber> nazioniStanziali = this.model.getStanziali();
    	
    	String output = this.printSimulationResult(partenza, T, nazioniStanziali);
    	this.resultTextArea.setText(output);
    }
    
    private String printSimulationResult(Country partenza, 
    								int T, Collection<CountryAndNumber> nazioniStanziali)
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Simulazione a partire da: ").append(partenza.toString()).append("\n\n");
    	sb.append("Numero di passi simulati: ").append(T).append("\n\n");
    	sb.append("Nazioni con migranti stanziali:\n");
    	sb.append(this.printCountryAndNumbers(nazioniStanziali, "migranti stanziali"));
    	
    	return sb.toString();
    }

    @FXML
    void initialize() 
    {
        assert annoTextField != null : "fx:id=\"annoTextField\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
        assert calcolaConfiniButton != null : "fx:id=\"calcolaConfiniButton\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
        assert nazioneComboBox != null : "fx:id=\"nazioneComboBox\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
        assert avviaSimulazioneButton != null : "fx:id=\"avviaSimulazioneButton\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
        assert resultTextArea != null : "fx:id=\"resultTextArea\" was not injected: check your FXML file 'Scene_migrazioni.fxml'.";
    }
    
    public void setModel(Model model) 
    {
    	this.model = model;
    }
}

