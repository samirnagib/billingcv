package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.ClientType;
import model.services.ClientTypeServices;

public class ClientTypeListController implements Initializable, DataChangeListener {

	@FXML
	private TableView<ClientType> tbViewClientType;
	
	@FXML
	private TableColumn<ClientType, Integer> tbColumnidType;
	
	@FXML
	private TableColumn<ClientType, String> tbColumntypeName;
	
	@FXML 
	private TableColumn<ClientType, ClientType> tbColumnEDIT;
	
	@FXML 
	private TableColumn<ClientType, ClientType> tbColumnREMOVE;
	
	@FXML
	private Button btNovo;
	
	private ClientTypeServices service;
	
	private ObservableList<ClientType> obsList;
	
	
	@FXML
	private void btNovoOnAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		ClientType obj = new ClientType();
		createDialogForm(obj, "/gui/ClientTypeForm.fxml", parentStage);
	}
	
	public void setClientTypeServices(ClientTypeServices service) {
		this.service = service;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		tbColumnidType.setCellValueFactory(new PropertyValueFactory<>("idType"));
		tbColumntypeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));
		
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tbViewClientType.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<ClientType> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tbViewClientType.setItems(obsList);
		initEditButtons();
		initRemoveButtons(); 
	}
	
	private void createDialogForm(ClientType obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			ClientTypeFormController controller = loader.getController();
			controller.setClientType(obj);
			controller.setClientTypeService(new ClientTypeServices());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os Tipos de servidor:");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}

	@Override
	public void onDataChanged() {
		updateTableView();
		
	}
	
	private void initEditButtons() {
		tbColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tbColumnEDIT.setCellFactory(param -> new TableCell<ClientType, ClientType>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(ClientType obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ClientTypeForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	
	private void initRemoveButtons() {
		tbColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tbColumnREMOVE.setCellFactory(param -> new TableCell<ClientType, ClientType>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(ClientType obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
			
		});
	} 
	
	private void removeEntity(ClientType obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}


}
