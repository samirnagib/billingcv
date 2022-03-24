package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Client;
import model.entities.ClientType;
import model.entities.Owner;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.OwnerServices;

public class ClientFormController implements Initializable {

	private Client entity;
	private ClientServices service;
	private ClientTypeServices typeService;
	private OwnerServices ownerService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtidClient;
	
	@FXML
	private TextField txtclientName;
	
	@FXML
	private TextField txtclientHostname;
	
	@FXML
	ComboBox<ClientType> cbClientType;
	
	@FXML
	ComboBox<Owner> cbOwner;
	
	@FXML
	private Button btSalvar;
	
	@FXML 
	private Button btCancelar;
	
	private ObservableList<ClientType> obsListTYPE;
	
	private ObservableList<Owner> obsListOWNER;
	
	public void setClient(Client entity) {
		this.entity = entity;
	}
	
	public void setServices(ClientServices service, ClientTypeServices typeService, OwnerServices ownerService) {
		this.service = service;
		this.typeService = typeService;
		this.ownerService = ownerService;
	}
	

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();	
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtidClient);
		Constraints.setTextFieldMaxLength(txtclientName, 200);
		Constraints.setTextFieldMaxLength(txtclientHostname, 200);
		
		initializeComboBoxClientType();
		initializeComboBoxOwner();
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtidClient.setText(String.valueOf(entity.getIdClient()));
		txtclientName.setText(entity.getClientName());
		txtclientHostname.setText(entity.getClientHostname());
		if (entity.getTypeName().equals(null)) {
			cbClientType.getSelectionModel().selectFirst();
		} else {
			cbClientType.setValue(entity.getTypeName());
		}
		
	}

	
	public void loadAssociatedObjects() {
		if (typeService == null) {
			throw new IllegalStateException("typeService was null");
		}
		
		if (ownerService == null) {
			throw new IllegalStateException("ownerService was null");
		}
		
		// Carregar os objetos do tipo de servidor
		List<ClientType> lsct = typeService.findAll();
		obsListTYPE = FXCollections.observableArrayList(lsct);
		cbClientType.setItems(obsListTYPE);
		
		// Carregar os objetos do Responsável 
		List<Owner> lsOw = ownerService.findAll();
		obsListOWNER = FXCollections.observableArrayList(lsOw);
		cbOwner.setItems(obsListOWNER);
		
	}
	
	private void initializeComboBoxClientType() {
		Callback<ListView<ClientType>, ListCell<ClientType>> factory = lv -> new ListCell<ClientType>() {
			@Override
			protected void updateItem(ClientType item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getTypeName());
			}
		};
		cbClientType.setCellFactory(factory);
		cbClientType.setButtonCell(factory.call(null));
	}
	
	private void initializeComboBoxOwner() {
		Callback<ListView<Owner>, ListCell<Owner>> factory = lv -> new ListCell<Owner>() {
			@Override
			protected void updateItem(Owner item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getOwName());
			}
		};
		cbOwner.setCellFactory(factory);
		cbOwner.setButtonCell(factory.call(null));
	}

	
}
