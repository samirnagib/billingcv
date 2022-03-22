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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.entities.Owner;
import model.entities.Users;
import model.services.OwnerServices;


public class OwnerListController implements Initializable, DataChangeListener {

	@FXML
	private TableView<Owner> tableViewOwner;
	
	@FXML
	private TableColumn<Owner, Integer> tableColumnidOwner;
	
	@FXML
	private TableColumn<Owner, String> tableColumowName;
	
	@FXML
	private TableColumn<Owner, String> tableColumowEmail1;
	
	@FXML
	private TableColumn<Owner, String> tableColumowEmail2;
	
	@FXML
	private TableColumn<Owner, String> tableColumowProjectArea;
	
	@FXML
	private TableColumn<Owner, String> tableColumowAR;
	
	@FXML
	private TableColumn<Owner, Owner> tableColumEDIT;
	
	@FXML
	private TableColumn<Owner, Owner> tableColumREMOVE;
	
	
	
	@FXML
	private Button btNovo;

	private OwnerServices service;
	
	private ObservableList<Owner> obsList;
	
	@FXML
	private void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Owner obj = new Owner();
		createDialogForm(obj, "/gui/OwnerForm.fxml", parentStage);
		
	}
	
	public void setOwnerServices(OwnerServices service) {
		this.service = service;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		tableColumnidOwner.setCellValueFactory(new PropertyValueFactory<>("idOwner"));
		tableColumowName.setCellValueFactory(new PropertyValueFactory<>("owName"));
		tableColumowEmail1.setCellValueFactory(new PropertyValueFactory<>("owEmail1"));
		tableColumowEmail2.setCellValueFactory(new PropertyValueFactory<>("owEmail2"));
		tableColumowProjectArea.setCellValueFactory(new PropertyValueFactory<>("owProjectArea"));
		tableColumowAR.setCellValueFactory(new PropertyValueFactory<>("owAR"));

		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewOwner.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<Owner> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewOwner.setItems(obsList);
		initEditButtons();
		initRemoveButtons(); 
	}
	
	private void createDialogForm(Owner obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			OwnerFormController controller = loader.getController();
			controller.setOwner(obj);
			controller.setOwnerService(new OwnerServices());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com dos dados do responsável:");
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
		tableColumEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumEDIT.setCellFactory(param -> new TableCell<Owner, Owner>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Owner obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/OwnerForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	
	private void initRemoveButtons() {
		tableColumREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumREMOVE.setCellFactory(param -> new TableCell<Owner, Owner>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(Owner obj, boolean empty) {
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
	
	private void removeEntity(Owner obj) {
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
