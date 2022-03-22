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
import model.entities.Users;
import model.services.UsersServices;

public class UsersListController implements Initializable, DataChangeListener{
	
	private UsersServices service;
	
	@FXML
	private Button btNew;
	
	@FXML
	private TableView<Users> tableViewUsers;
	
	@FXML
	private TableColumn<Users, Integer> tableColumnuserID;
	
	@FXML
	private TableColumn<Users, String> tableColumnuserLogin;
	
	@FXML
	private TableColumn<Users, String> tableColumnuserFullName;
	
	@FXML
	private TableColumn<Users, String> tableColumnuserEmail;
	
	@FXML  
	private TableColumn<Users, String> tableColumnuserLevelAccess;
	
	@FXML 
	private TableColumn<Users, Users> tableColumnEDIT;
	
	@FXML
	private TableColumn<Users, Users> tableColumnREMOVE;
	
	private ObservableList<Users> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Users obj = new Users();
		createDialogForm(obj, "/gui/UsersForm.fxml", parentStage);
	}

	public void setUsersService(UsersServices service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	
	}

	private void initializeNodes() {
		tableColumnuserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
		tableColumnuserLogin.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
		tableColumnuserFullName.setCellValueFactory(new PropertyValueFactory<>("userFullName"));
		tableColumnuserEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		tableColumnuserLevelAccess.setCellValueFactory(new PropertyValueFactory<>("userLevelAccess"));
		
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewUsers.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<Users> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewUsers.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createDialogForm(Users obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			UsersFormController controller = loader.getController();
			controller.setUsers(obj);
			controller.setUsersServices(new UsersServices());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com dos dados do usuário:");
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
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Users, Users>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Users obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/UsersForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Users, Users>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(Users obj, boolean empty) {
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
	
	private void removeEntity(Users obj) {
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
