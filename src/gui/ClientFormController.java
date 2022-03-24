package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
	
	
	
	
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
				
	}

}
