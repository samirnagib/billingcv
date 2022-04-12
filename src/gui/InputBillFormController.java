package gui;


import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.entities.BillTags;
import model.entities.Client;
import model.entities.InputBill;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.InputBillServices;
import model.services.OwnerServices;

public class InputBillFormController implements Initializable {

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private InputBillServices ibServices;
	private ClientServices clServices;
	private ClientTypeServices ctServices;
	private OwnerServices owServices;
	private BillTagsServices btServices;
	private InputBill entity;
	
	
	private boolean isNew;
	
	
	
	@FXML
	private Label lbl_idInputBill;
	
	@FXML
	private Label lblAnoMes;
	
	@FXML
	private ComboBox<String> cbMes;
	
	@FXML
	private ComboBox<String> cbAno;
	
	@FXML
	private ComboBox<BillTags> cbBillTags;
	
	@FXML
	private ComboBox<Client> cbServidor;
	
	
	@FXML
	private TextField txtAgente;
	
	@FXML
	private TextField txtInstancia;
	
	@FXML
	private TextField txtBackupSet;
	
	@FXML
	private TextField txtSubClient;
	
	@FXML
	private TextField txtStoragePolicy;
	
	@FXML
	private TextField txtCopy;
	
	@FXML
	private TextField txtVRFaixa;
	
	@FXML
	private TextField txtFEBackup;
	
	@FXML
	private TextField txtFEArchive;
	
	@FXML
	private TextField txtPrimaryApp;
	
	@FXML
	private TextField txtProtectedApp;
	
	@FXML
	private TextField txtMediaSize;
	
	@FXML
	private TextField txtTotal;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	private double vrUnitario;
	private double valorTotal;
	
	private String vrCompetencia;
	
	
	private ObservableList<String> obsMes;
	private ObservableList<String> obsAno;
	private ObservableList<BillTags> obsBT;
	private ObservableList<Client> obsServer;
	
	
	
	public void setInputBill(InputBill entity) {
		this.entity = entity;
	}
	
	public void setFormAct(String actForm) {
		// Configura o modo do formulario, se ele é para cadastrar
		// ou para atualizar um registro;
		
		if (actForm.equals("NEW")) {
			System.out.println("2.1: " + actForm);
			lblAnoMes.setVisible(false);
			cbMes.setVisible(true);
			cbAno.setVisible(true);
			isNew = true;
		} else {
			System.out.println("2.2: " + actForm);
			lblAnoMes.setVisible(true);
			cbMes.setVisible(false);
			cbAno.setVisible(false);
			isNew = false;
		}
		
	}
	
	public void setServices(InputBillServices ibServices, ClientServices clServices, ClientTypeServices ctServices, OwnerServices owServices, BillTagsServices btServices) {
		this.ibServices = ibServices;
		this.clServices = clServices;
		this.ctServices = ctServices;
		this.owServices = owServices;
		this.btServices = btServices;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		fillComboBox();
		initializeComboBoxBillTags();
		initializeComboBoxServidores();
		Constraints.setTextFieldMaxLength(txtAgente, 200);
		Constraints.setTextFieldMaxLength(txtInstancia, 200);
		Constraints.setTextFieldMaxLength(txtBackupSet, 200);
		Constraints.setTextFieldMaxLength(txtSubClient, 200);
		Constraints.setTextFieldMaxLength(txtStoragePolicy, 200);
		Constraints.setTextFieldMaxLength(txtCopy, 200);
		Constraints.setTextFieldDouble(txtVRFaixa);
		Constraints.setTextFieldDouble(txtFEBackup);
		Constraints.setTextFieldDouble(txtFEArchive);
		Constraints.setTextFieldDouble(txtPrimaryApp);
		Constraints.setTextFieldDouble(txtProtectedApp);
		Constraints.setTextFieldDouble(txtMediaSize);
		Constraints.setTextFieldDouble(txtTotal);
	}

	private void initializeComboBoxServidores() {
		Callback<ListView<Client>, ListCell<Client>> factory = lv -> new ListCell<Client>() {
			@Override
			protected void updateItem(Client item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getClientName());
			}
		};
		cbServidor.setCellFactory(factory);
		cbServidor.setButtonCell(factory.call(null));
		
	}

	private void initializeComboBoxBillTags() {
		Callback<ListView<BillTags>, ListCell<BillTags>> factory = lv -> new ListCell<BillTags>() {
			@Override
			protected void updateItem(BillTags item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getBilltagName());
			}
		};
		cbBillTags.setCellFactory(factory);
		cbBillTags.setButtonCell(factory.call(null));
		
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}


	public void loadAssociatedObjects() {
		if (btServices == null) {
			throw new IllegalStateException("btServices was null");
		}
		//Carregar as listas das faixas de preço
		List<BillTags> lsbt = btServices.findAll();
		obsBT = FXCollections.observableArrayList(lsbt);
		cbBillTags.setItems(obsBT);
		if (clServices == null) {
			throw new IllegalStateException("clServices was null");
		}
		List<Client> lsserver = clServices.findAll();
		obsServer = FXCollections.observableArrayList(lsserver);
		cbServidor.setItems(obsServer);

	}

	private InputBill getFormData() {
		InputBill fatura = new InputBill();
		
		fatura.setIdInputBill(Utils.tryParseToInt(lbl_idInputBill.getText()));
		System.out.println(lbl_idInputBill.getText());
		fatura.setIb_ano_mes(vrCompetencia);
		fatura.setBilltag(cbBillTags.getValue());
		fatura.setClient(cbServidor.getValue());
		fatura.setCv_agent(txtAgente.getText());
		fatura.setCv_instance(txtInstancia.getText());
		fatura.setCv_backupset(txtBackupSet.getText());
		fatura.setCv_subclient(txtSubClient.getText());
		fatura.setCv_storagepolicy(txtStoragePolicy.getText());
		fatura.setCv_copyname(txtCopy.getText());
		fatura.setCv_febackupsize(Utils.tryParseToDouble(txtFEBackup.getText()));
		fatura.setCv_fearchivesize(Utils.tryParseToDouble(txtFEArchive.getText()));
		fatura.setCv_primaryappsize(Utils.tryParseToDouble(txtPrimaryApp.getText()));
		fatura.setCv_protectedappsize(Utils.tryParseToDouble(txtProtectedApp.getText()));
		fatura.setCv_mediasize(Utils.tryParseToDouble(txtMediaSize.getText()));
		fatura.setIb_taxcalculated(Utils.tryParseToDouble(txtTotal.getText()));

		return fatura;
	}


	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (entity.getIdInputBill()== null) {
			lbl_idInputBill.setText("");
		} else {
			lbl_idInputBill.setText(String.valueOf(entity.getIdInputBill())); 
		}
		if (entity.getIb_ano_mes() != null ) {
			lblAnoMes.setText(entity.getIb_ano_mes());
			vrCompetencia = (String) entity.getIb_ano_mes();
			System.out.println(vrCompetencia);
		}
		if (entity.getClient() == null) {
			cbServidor.getSelectionModel().selectFirst();
		} else {
			cbServidor.setValue(entity.getClient());
		}
		if (entity.getBilltag() == null ) {
			cbBillTags.getSelectionModel().selectFirst();
			txtVRFaixa.setText(String.valueOf("0.00"));
		} else {
			cbBillTags.setValue(entity.getBilltag());
			txtVRFaixa.setText(String.valueOf(entity.getBilltag().getBillPriceTB()));
		}
		txtAgente.setText(entity.getCv_agent());
		txtInstancia.setText(entity.getCv_instance());
		txtBackupSet.setText(entity.getCv_backupset());
		txtSubClient.setText(entity.getCv_subclient());
		txtStoragePolicy.setText(entity.getCv_storagepolicy());
		txtCopy.setText(entity.getCv_copyname());
		txtFEBackup.setText(String.valueOf(entity.getCv_febackupsize()));
		txtFEArchive.setText(String.valueOf(entity.getCv_fearchivesize()));
		txtPrimaryApp.setText(String.valueOf(entity.getCv_primaryappsize()));
		txtProtectedApp.setText(String.valueOf(entity.getCv_protectedappsize()));
		txtMediaSize.setText(String.valueOf(entity.getCv_mediasize()));
		if (valorTotal <= 0 ) {
			txtTotal.setText(String.valueOf(entity.getIb_taxcalculated()));
		}
	}
	
	private void fillComboBox() {
		List<String> meses = new ArrayList<>();
		List<String> anos = new ArrayList<>();
		Integer nowYear = Calendar.getInstance().get(Calendar.YEAR);
		String nowMonth[] = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ" };
		for (Integer i = 2020 ; i < (nowYear+2) ; i++) {
			//System.out.println(i);
			anos.add(i.toString() );
		}
		for (String item : nowMonth ) {
			meses.add(item);
			//System.out.println(item);
		}
		obsMes = FXCollections.observableArrayList(meses);
		obsAno = FXCollections.observableArrayList(anos);
		
		cbMes.setItems(obsMes);
		cbAno.setItems(obsAno);

	}
	
	private void calculaSoma() {

		double feb = Double.parseDouble(txtFEBackup.getText());
		double fea = Double.parseDouble(txtFEArchive.getText());
		double pria = Double.parseDouble(txtPrimaryApp.getText());
		double proa = Double.parseDouble(txtProtectedApp.getText());
		double ms = Double.parseDouble(txtMediaSize.getText());
		
			valorTotal = (feb + fea + pria + proa + ms) * vrUnitario;
	}
	
	
	@FXML
	public void cbBillTagsOnAction() {
		//private BillTags faixa = new BillTags();
		// faixa = btServices.findByName(cbBillTags.getValue().getBillPriceTB())
		
		vrUnitario = (double) cbBillTags.getValue().getBillPriceTB();
		System.out.println(vrUnitario);
		txtVRFaixa.setText(String.valueOf(vrUnitario));
		calculaSoma();
		txtTotal.setText(String.valueOf(valorTotal));
	}
	
	@FXML
	public void cbAnoOnAction() {
		String mes = null;
		String ano = null;
		
		if (cbMes.getValue() == null) {
			Alerts.showAlert("MENSAGEM DE ERRO", null, "O campo mês não pode estar vazio.", AlertType.ERROR);
			cbMes.requestFocus();
		} else {
			mes = cbMes.getValue();
		}
		
		if (cbAno.getValue() == null) {
			Alerts.showAlert("MENSAGEM DE ERRO", null, "O campo ano não pode estar vazio.", AlertType.ERROR);
			cbAno.requestFocus();
		} else {
			ano = cbAno.getValue();
		}
		
		vrCompetencia = mes + " " + ano;
		System.out.println(vrCompetencia);
	}
	

	@FXML
	public void cbMesOnAction() {
		String mes = null;
		String ano = null;
		
		if (cbMes.getValue() == null) {
			Alerts.showAlert("MENSAGEM DE ERRO", null, "O campo mês não pode estar vazio.", AlertType.ERROR);
			cbMes.requestFocus();
		} else {
			mes = cbMes.getValue();
		}
		
		if (cbAno.getValue() == null) {
			Alerts.showAlert("MENSAGEM DE ERRO", null, "O campo ano não pode estar vazio.", AlertType.ERROR);
			cbAno.requestFocus();
		} else {
			ano = cbAno.getValue();
		}
		
		vrCompetencia = mes + " " + ano;
		System.out.println(vrCompetencia);
	}
	
	@FXML
	public void btSaveOnAction(ActionEvent event) {
		System.out.println("btSaveOnAction");
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (ibServices == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			System.out.println("Get Form Data: " + entity.getIdInputBill());
			ibServices.saveORupdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
		
		
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	@FXML
	public void btCancelOnAction(ActionEvent event) {
		System.out.println("btCancelOnAction");
		Utils.currentStage(event).close();
	}
	
	@FXML
	public void cbServidor (KeyEvent e) {
		if(e.getCode().equals(KeyCode.ENTER)) {
	        txtAgente.requestFocus();
		}
	}
	
	@FXML
	private void txtAgente(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtInstancia.requestFocus();
		}
	}

	@FXML
	private void txtInstancia(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtBackupSet.requestFocus();
		}
	}

	@FXML
	private void txtBackupSet(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtSubClient.requestFocus();
		}
	}

	@FXML
	private void txtSubClient(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtStoragePolicy.requestFocus();
		}
	}

	@FXML
	private void txtStoragePolicy(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtCopy.requestFocus();
		}
	}

	@FXML
	private void txtCopy(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtFEBackup.requestFocus();
		}
	}

	@FXML
	private void txtFEBackup(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtFEArchive.requestFocus();
		}
	}

	@FXML
	private void txtFEArchive(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtPrimaryApp.requestFocus();
		}
	}

	@FXML
	private void txtPrimaryApp(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtProtectedApp.requestFocus();
		}
	}

	@FXML
	private void txtProtectedApp(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			txtMediaSize.requestFocus();
		}
	}

	@FXML
	private void txtMediaSize(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			calculaSoma();
			txtTotal.setText(String.valueOf(valorTotal));
			txtTotal.requestFocus();
		}
	}
	
	@FXML
	private void txtTotalOnKeyPressed(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			btSave.requestFocus();
		}
	}

}
