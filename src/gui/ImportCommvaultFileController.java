package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ImportCommvaultFileController implements Initializable {

	@FXML
	private ComboBox<String> cbMeses;
	
	@FXML
	private TextField txtAno;
	
	@FXML
	private Label lbPath;
	
	@FXML
	private Label lbMessages;
	
	@FXML
	private Button btAbrir;
	
	@FXML
	private Button btImportar;
	
	
	@FXML
	private void btAbrirOnAction () {
		System.out.println("btAbrirOnAction");
	}
	
	@FXML
	private void btImportarOnAction () {
		System.out.println("btImportarOnAction");
	}
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
