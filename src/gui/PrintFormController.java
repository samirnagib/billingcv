package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class PrintFormController implements Initializable {

	@FXML
	private Button btPrint;
	
	@FXML
	private Button btFechar;
	
	@FXML
	private Button btClearFilter;
	
	@FXML
	private ComboBox<String> cbCompetencia;
	
	@FXML
	private ComboBox<String> cbResponsavel;
	
	@FXML
	private Label lbFiltroAplicado;
	
	@FXML
	private void btPrintOnAction() {
		System.out.println("btPrint");
	}
	
	@FXML
	private void brFecharOnAction() {
		System.out.println("btFechar");
	}
	
	@FXML
	private void btClearFiltersOnAction() {
		System.out.println("btClearFilters");
	}
	
	@FXML
	private void cbCompetenciaOnAction() {
		System.out.println("cbCompetencia");
	}
	
	@FXML
	private void cbResponsavelOnAction() {
		System.out.println("cbResponsavel");
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
