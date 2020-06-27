package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	String ruolo = this.boxRuolo.getValue();
    	if (ruolo == null) {
    		this.txtResult.setText("Devi prima selezionare un ruolo dal menu' a tendina per poter creare il grafo!\n");
    		return;
    	}
    	for (Adiacenza a: this.model.getAdiacenze()) {
    		this.txtResult.appendText(a.toString());
    	}
    	this.btnCalcolaPercorso.setDisable(false);
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	this.txtResult.clear();
    	if (this.txtArtista.getText() == "") {
    		this.txtResult.appendText("Devi prima inserire l'id di un artista!");
    		return;
    	}
    	int idArtista = -1;
    	try {
    		idArtista = Integer.parseInt(this.txtArtista.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Devi inserire un id numerico!\n");
    		return;
    	}
    	if (!this.model.isContenuto(idArtista)) {
    		this.txtResult.appendText("L'id da te inserito non corrisponde ad alcun artista nel grafo! Riprova!\n");
    		return;
    	}
    	this.model.cercaPercorso(idArtista);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String ruolo = this.boxRuolo.getValue();
    	if (ruolo == null) {
    		this.txtResult.setText("Devi prima selezionare un ruolo dal menu' a tendina per poter creare il grafo!\n");
    		return;
    	}
    	this.model.creaGrafo(ruolo);
    	this.txtResult.appendText("Grafo creato con "+this.model.getNumeroArchi()+" archi e "+this.model.getVertici().size()+" vertici!\n");
    	this.btnArtistiConnessi.setDisable(false);
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxRuolo.getItems().addAll(this.model.getAllRoles());
		this.btnArtistiConnessi.setDisable(true);
		this.btnCalcolaPercorso.setDisable(true);
	}
}

