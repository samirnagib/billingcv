package gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	
	
	
	@FXML
	private void btAbrirOnAction () {
		System.out.println("btAbrirOnAction");
		Stage stage = new Stage ();
		FileChooser fileChooser = new FileChooser();
		Utils.configureFileChooserImportFiles(fileChooser, "Importar arquivo de CLientes");
		file = fileChooser.showOpenDialog(stage);
		lbPath.setText(file.getAbsolutePath());
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
