package gui;

import java.awt.Event;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	
	private File file;
	
	//Variaveis de Banco de dados
	private Integer idInputBill;
	private String ib_ano_mes;
	private Integer id_billTag;
	private Integer id_client;
	private String cv_agent;
	private String cv_instance;
	private String cv_backupset;
	private String cv_subclient;
	private String cv_storagepolicy;
	private String cv_copyname;
	private double cv_mediasize;
	private double ib_taxcalculated;

	private Integer idbillTag;
	private String billtagName;
	private double billPriceTB;

	private Integer idClient;
	private String clientName;
	private String clientHostname;
	private Integer idType;
	private Integer idOwner;
 
	
	//variavel auxiliar para formação da competencia
	private String cptMES;
	private String cptANO;
	private String path;
	
	//Parametros para ComboBox
	private String Meses[] = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ" };
	
	private ObservableList<String> obsList;
	
	
	@FXML
	private void btAbrirOnAction() {
		Stage stage = new Stage ();
		FileChooser fileChooser = new FileChooser();
		Utils.configureFileChooserImportFiles(fileChooser, "Importar arquivo de CLientes");
		file = fileChooser.showOpenDialog(stage);
		lbPath.setText(file.getAbsolutePath());
	}
	
	@FXML
	private void btImportarOnAction () {
		System.out.println("btImportarOnAction");
		if (txtAno.getText()==null || txtAno.getText().trim().equals("")) {
			Alerts.showAlert("Mensagem de Erro", null, "O Campo Ano não pode estar vazio", AlertType.ERROR);
			txtAno.requestFocus();
		}
		cptANO = txtAno.getText();
		ib_ano_mes = cptMES + " " + cptANO;
		lbMessages.setText(ib_ano_mes);
	
	
	
	}
	
	@FXML
	private void cbMesesOnAction () {
		cptMES = cbMeses.getSelectionModel().getSelectedItem();
		System.out.println(cptMES);
		lbMessages.setText(cptMES);
		txtAno.requestFocus();
		
	}
	
	@FXML
	private void txtAnoOnEnter(KeyEvent e) {
		if(e.getCode().equals(KeyCode.ENTER)) {
	        System.out.println("enter");
	        btAbrir.requestFocus();
		}
	}
		
	@FXML
	private void btAbrirOnEnter(KeyEvent e) {
		if(e.getCode().equals(KeyCode.ENTER)) {
	        btAbrir.fire();
		}
	}
	
	private void initializenodes() {
		Constraints.setTextFieldInteger(txtAno);
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsList = FXCollections.observableArrayList(Meses);
		cbMeses.setItems(obsList);
		
	}

}
