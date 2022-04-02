package gui;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.sun.javafx.binding.StringFormatter;

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
import javafx.util.converter.FormatStringConverter;
import model.dao.BillTagsDao;
import model.dao.ClientDao;
import model.dao.ClientTypeDao;
import model.dao.DaoFactory;
import model.dao.InputBillDao;
import model.dao.OwnerDao;
import model.entities.BillTags;
import model.entities.Client;
import model.entities.InputBillCSV;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.OwnerServices;

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
	private String cv_mediasize;
	private String ib_taxcalculated;

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
	
	//Informar a quantidade de servidores novos encontrados;
	
	List<String> newserver = new ArrayList<>();
	List<InputBillCSV> ibcsv = new ArrayList<>();
	
	// conexão com banco de dados
	
	BillTagsDao btDao = DaoFactory.createBillTagsDao();
	ClientDao clDao = DaoFactory.createClientDao();
	InputBillDao ibDao = DaoFactory.createInputBillDao();

	private ClientServices clientService;
	private BillTagsServices btServices;
	private OwnerServices ownerService;
	
	public void setServices(ClientServices clientService, BillTagsServices btServices, OwnerServices ownerService) {
		this.clientService = clientService;
		this.btServices = btServices;
		this.ownerService = ownerService;
	}
	
	
	
	
	
	@FXML
	private void btAbrirOnAction() {
		Stage stage = new Stage ();
		FileChooser fileChooser = new FileChooser();
		Utils.configureFileChooserImportFiles(fileChooser, "Importar arquivo de CLientes");
		file = fileChooser.showOpenDialog(stage);
		lbPath.setText(file.getAbsolutePath());
		path = file.getAbsolutePath();
	}
	
	@FXML
	private void btImportarOnAction (ActionEvent event) {
		// System.out.println("btImportarOnAction");
		if (txtAno.getText()==null || txtAno.getText().trim().equals("")) {
			Alerts.showAlert("Mensagem de Erro", null, "O Campo Ano não pode estar vazio", AlertType.ERROR);
			txtAno.requestFocus();
		}
		cptANO = txtAno.getText();
		ib_ano_mes = cptMES + " " + cptANO;
		lbMessages.setText(ib_ano_mes);
		if (path == null || path.trim().equals("")) {
			Alerts.showAlert("Mensagem de Erro", null, "Selecione um arquivo antes de prosseguir", AlertType.ERROR);
			btAbrir.requestFocus();
		} else {
			try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
				 String lineCSV = br.readLine();
				 int line = 1;
				 while (lineCSV != null ) {
					 
					 	
					 	Locale.setDefault(Locale.US);
						 String[] field = lineCSV.split(",");
//						 System.out.println(field);
						 String stline = field[1];
						 if ( line > 1 ) {
							 String Competencia = ib_ano_mes;
							 String BillingTag = field[0];
							 String Client = field[1];
							 String Agent = field[2];
							 String Instance = field[3];
							 String Backupset = field[4];
							 String Subclient = field[5];
							 String StoragePolicy = field[6];
							 String Copy = field[7];
							 //String media = field[8].replace("\"", "");
							 
							 double MediaSize = Double.parseDouble(field[8].replace("\"", ""));
							 
							 ibcsv.add(new InputBillCSV(Competencia, BillingTag, Client, Agent, Instance, Backupset, Subclient, StoragePolicy, Copy, MediaSize));
							 line++;
						 }
//						 else {
//							 System.out.println("else");
//							 
//							 
//						 }
						 lineCSV = br.readLine();
						 line++;
							 
				} //fim do while
				 
				 for(InputBillCSV item : ibcsv) {
					 BillTags bt = new BillTags();
					 BillTags btTemp = new BillTags(null,item.getBillingTag(),null);
					 System.out.println("item: " + item.getBillingTag());
					 String name = item.getBillingTag();
					 bt = btServices.findByName(name);
					 System.out.println("var: "+ name);
					 System.out.println("objeto: "+ bt);
				 }
				 
			}catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
			}finally {
				lbMessages.setText("Importação concluida." );
			} //final do bloco try
		} //final do bloco if
	
	
	
	}
	
	@FXML
	private void cbMesesOnAction () {
		cptMES = cbMeses.getSelectionModel().getSelectedItem();
		txtAno.requestFocus();
		
	}
	
	@FXML
	private void txtAnoOnEnter(KeyEvent e) {
		if(e.getCode().equals(KeyCode.ENTER)) {
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
		initializenodes();
		
	}

}
