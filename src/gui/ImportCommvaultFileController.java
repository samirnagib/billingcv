package gui;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import com.sun.javafx.binding.StringFormatter;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyDoubleProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
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
import model.entities.ClientType;
import model.entities.InputBill;
import model.entities.InputBillCSV;
import model.entities.InputBillResult;
import model.entities.Owner;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.OwnerServices;

public class ImportCommvaultFileController implements Initializable {

	@FXML
	private VBox vBoxTV;
	
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
	private TableView<InputBillResult> tvConta;
	
	@FXML
	private TableColumn<InputBillResult, String> tvcCompetencia;

	@FXML
	private TableColumn<InputBillResult, String> tvcBillingTag;

	@FXML
	private TableColumn<InputBillResult, String> tvcClient;

	@FXML
	private TableColumn<InputBillResult, String> tvcAgent;

	@FXML
	private TableColumn<InputBillResult, String> tvcInstance;

	@FXML
	private TableColumn<InputBillResult, String> tvcBackupset;

	@FXML
	private TableColumn<InputBillResult, String> tvcSubclient;

	@FXML
	private TableColumn<InputBillResult, String> tvcStoragePolicy;

	@FXML
	private TableColumn<InputBillResult, String> tvcCopy;

	@FXML
	private TableColumn<InputBillResult, Double> tvcfebackupsize;

	@FXML
	private TableColumn<InputBillResult, Double> tvcfearchivesize;

	@FXML
	private TableColumn<InputBillResult, Double> tvcprimaryappsize;

	@FXML
	private TableColumn<InputBillResult, Double> tvcprotectedappsize;

	@FXML
	private TableColumn<InputBillResult, Double> tvcMediaSize;
	

	@FXML
	private TableColumn<InputBillResult, Double> tvcCustoTotal;
	
	
	private File file;
	
	//Variaveis de Banco de dados
	private String ib_ano_mes;
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
	private String pathSave;
	
	//Parametros para ComboBox
	private String Meses[] = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ" };
	
	private ObservableList<String> obsList;
	private ObservableList<InputBillResult> obsIBR;
	
	//Informar a quantidade de servidores novos encontrados;
	
	
	List<InputBillCSV> ibcsv = new ArrayList<>();
	List<InputBill> ib = new ArrayList<>();
	List<InputBillResult> list = new ArrayList<>();
	List<BillTags> newBTagList  = new ArrayList<>();
	List<Client> newClientList  = new ArrayList<>();
	
	
	// conexão com banco de dados
	
	BillTagsDao btDao = DaoFactory.createBillTagsDao();
	ClientDao clDao = DaoFactory.createClientDao();
	InputBillDao ibDao = DaoFactory.createInputBillDao();
	ClientTypeDao ctDao  = DaoFactory.createClientTypeDao();
	OwnerDao oDao  = DaoFactory.createOwnerDao();

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
		pathSave = file.getParent() + System.getProperty("file.separator");
		
	}
	
	@SuppressWarnings("unused")
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

						 if ( line > 1 ) {
							 String Competencia = ib_ano_mes;
							 String BillingTag = field[0].replace("\"", "");
							 String Client = field[1].replace("\"", "");
							 String Agent = field[2].replace("\"", "");
							 String Instance = field[3].replace("\"", "");
							 String Backupset = field[4].replace("\"", "");
							 String Subclient = field[5].replace("\"", "");
							 String StoragePolicy = field[6].replace("\"", "");
							 String Copy = field[7].replace("\"", "");
							 double FrontEndBackupSize = Double.parseDouble(field[8].replace("\"", ""));
							 double FrontEndArchiveSize = Double.parseDouble(field[9].replace("\"", ""));
							 double PrimaryAppSize = Double.parseDouble(field[10].replace("\"", ""));
							 double ProtectedAppSize = Double.parseDouble(field[11].replace("\"", ""));
							 double MediaSize = Double.parseDouble(field[12].replace("\"", ""));

							 BillTags tabelaP = new BillTags();
							 Client server = new Client();
							 ClientType cType = new ClientType();
							 Owner resp = new Owner();
							
							 tabelaP = btDao.findByName(BillingTag);
							 server = clDao.findByName(Client);
							 cType = ctDao.findById(server.getIdType());
							 resp = oDao.findById(server.getIdOwner());
							 
							 if (tabelaP == null) {
								 System.out.println("tabela null");
								 System.out.println("Campo 0: " + BillingTag);
								 BillTags novaFaixa = new BillTags(null,BillingTag,1.0);
								 btDao.insert(novaFaixa);
								 tabelaP = btDao.findByName(BillingTag);
								 newBTagList.add(tabelaP);
							 }
							 if (server == null ) {
								System.out.println("server null");
							 	System.out.println("Campo 1: " + Client);
							 	Integer id = 1;
							 	ClientType ct = new ClientType();
							 	Owner ow = new Owner();
							 	ct = ctDao.findById(id);
							 	ow = oDao.findById(id);
							 	Client novoCliente = new Client(null,Client,Client,id,id,ct,ow);
							 	clDao.insert(novoCliente);
							 	server = clDao.findByName(Client);
							 	newClientList.add(server);
						 	}

							 
							 ib_taxcalculated = (FrontEndBackupSize + FrontEndArchiveSize + PrimaryAppSize + ProtectedAppSize + MediaSize)* tabelaP.getBillPriceTB();
							 
							 InputBill conta = new InputBill(null, Competencia, tabelaP.getIdbillTag(), server.getIdClient(), Agent, Instance, Backupset, Subclient, StoragePolicy, Copy, FrontEndBackupSize, FrontEndArchiveSize, PrimaryAppSize, ProtectedAppSize, MediaSize, ib_taxcalculated, tabelaP, server, cType, resp);

							 ibcsv.add(new InputBillCSV(Competencia, BillingTag, Client, Agent, Instance, Backupset, Subclient, StoragePolicy, Copy, FrontEndBackupSize, FrontEndArchiveSize, PrimaryAppSize, ProtectedAppSize, MediaSize));
							 ib.add(conta);
							 line++;
						 }
						 lineCSV = br.readLine();
						 line++;
							 
				} //fim do while
				 
				 for(InputBill item : ib) {

					 
					 System.out.println("Competencia: "+ item.getIb_ano_mes());
					 System.out.println("Bill Tag...: "+ item.getId_billTag() + " " + item.getBilltag().getBilltagName().toUpperCase());
					 System.out.println("Client.....: "+ item.getId_client() + " " + item.getClient().getClientName());
					 System.out.println("Agente.....: "+ item.getCv_agent());
					 System.out.println("Instancia..: "+ item.getCv_instance());
					 System.out.println("Bkp Set....: "+ item.getCv_backupset());
					 System.out.println("SubClient..: "+ item.getCv_subclient());
					 System.out.println("St. Policy.: "+ item.getCv_storagepolicy());
					 System.out.println("Copy.......: "+ item.getCv_copyname());
					 System.out.println("FE Backup..: "+ item.getCv_febackupsize());
					 System.out.println("FE Archive.: "+ item.getCv_fearchivesize());
					 System.out.println("Primary App: "+ item.getCv_primaryappsize());
					 System.out.println("Protected..: "+ item.getCv_protectedappsize());
					 System.out.println("Media Size.: "+ item.getCv_mediasize());
					 System.out.println("TxCalculada: "+ String.format("%.2f",item.getIb_taxcalculated()));
					 System.out.println("----------------------");
					 
					 InputBillResult ibr = new InputBillResult(item.getIb_ano_mes(), item.getBilltag().getBilltagName().toUpperCase(), item.getClient().getClientName(), item.getCv_agent(), item.getCv_instance(), item.getCv_backupset(), item.getCv_subclient(), item.getCv_storagepolicy(), item.getCv_copyname(), item.getCv_febackupsize(), item.getCv_fearchivesize(), item.getCv_primaryappsize(), item.getCv_protectedappsize(), item.getCv_mediasize(), item.getIb_taxcalculated());
					 list.add(ibr);
				 }
				 updateTableView();
				 
				 int numNvBT = newBTagList.size();
				 int numNvCl = newClientList.size();
				 
				 if ( numNvBT > 0 ) {
					 Alerts.showAlert("AVISO DE NOVA FAIXA DE COBRANÇA", null, "Foram encontrados " + numNvBT + " nova(s) faixas de cobrança", AlertType.INFORMATION);
					 Alerts.showAlert("AVISO DE NOVA FAIXA DE COBRANÇA", "O relatório com as novas faixas estão no caminho abaixo:", pathSave + "NovasFaixas.txt", AlertType.INFORMATION);
					 try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathSave + "NovasFaixas.txt"))) {
						 for (BillTags item : newBTagList ) {
							 bw.write("Codigo : " + item.getIdbillTag() +" - Nome da Faixa: " + item.getBilltagName());
							 bw.newLine();
						 }
						 System.out.println( pathSave + "NovasFaixas.txt - CRIADO!");
						 
					 } catch (IOException e) {
							System.out.println("Error writing file: " + e.getMessage());
						}
				 }
				 
				 if ( numNvCl > 0 ) {
					 Alerts.showAlert("AVISO DE NOVO CLIENTE", null, "Foram encontrados " + numNvCl + " novo(s) clientes", AlertType.INFORMATION);
					 Alerts.showAlert("AVISO DE NOVO CLIENTE", "O relatório com os novos clientes estão no caminho abaixo:", pathSave + "NovosClientes.txt", AlertType.INFORMATION);
					 try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathSave + "NovosClientes.txt"))) {
						 for (Client item : newClientList ) {
							 bw.write("Codigo : " + item.getIdClient() +" - Nome do Cliente: " + item.getClientName());
							 bw.newLine();
						 }
						 System.out.println( pathSave + "NovosClientes.txt - CRIADO!");
						 
					 } catch (IOException e) {
							System.out.println("Error writing file: " + e.getMessage());
						}
				 }
				 
				 
			}catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
			}finally {
				lbMessages.setText("Importação concluida." );
			} //final do bloco try
		} //final do bloco if
	
	
	
	}
	
	private void updateTableView() {
		obsIBR = FXCollections.observableArrayList(list);
		tvConta.setItems(obsIBR);
		
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
		tvcCompetencia.setCellValueFactory(new PropertyValueFactory<>("Competencia"));
		tvcBillingTag.setCellValueFactory(new PropertyValueFactory<>("BillingTag"));
		tvcClient.setCellValueFactory(new PropertyValueFactory<>("Client"));
		tvcAgent.setCellValueFactory(new PropertyValueFactory<>("Agent"));
		tvcInstance.setCellValueFactory(new PropertyValueFactory<>("Instance"));
		tvcBackupset.setCellValueFactory(new PropertyValueFactory<>("Backupset"));
		tvcSubclient.setCellValueFactory(new PropertyValueFactory<>("Subclient"));
		tvcStoragePolicy.setCellValueFactory(new PropertyValueFactory<>("StoragePolicy"));
		tvcCopy.setCellValueFactory(new PropertyValueFactory<>("Copy"));
		tvcfebackupsize.setCellValueFactory(new PropertyValueFactory<>("febackupsize"));
		Utils.formatTableColumnDouble(tvcfebackupsize,4);
		tvcfearchivesize.setCellValueFactory(new PropertyValueFactory<>("fearchivesize"));
		Utils.formatTableColumnDouble(tvcfearchivesize,4);
		tvcprimaryappsize.setCellValueFactory(new PropertyValueFactory<>("primaryappsize"));
		Utils.formatTableColumnDouble(tvcprimaryappsize,4);
		tvcprotectedappsize.setCellValueFactory(new PropertyValueFactory<>("protectedappsize"));
		Utils.formatTableColumnDouble(tvcprotectedappsize,4);
		tvcMediaSize.setCellValueFactory(new PropertyValueFactory<>("MediaSize"));
		Utils.formatTableColumnDouble(tvcMediaSize,4);
		tvcCustoTotal.setCellValueFactory(new PropertyValueFactory<>("CustoTotal"));
		Utils.formatTableColumnDouble(tvcCustoTotal,2);
		
		
		
//		Stage stage = (Stage) LoginController.getMainScene().getWindow();
//		ReadOnlyDoubleProperty hstage = stage.heightProperty();
//		vBoxTV.prefHeightProperty().bind(hstage-10);
//		tvConta.prefHeightProperty().bind(vBoxTV.heightProperty());
//		tvConta.prefWidthProperty().bind(vBoxTV.widthProperty());
		
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsList = FXCollections.observableArrayList(Meses);
		cbMeses.setItems(obsList);
		initializenodes();
		
	}

}
