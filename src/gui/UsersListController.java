package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Users;
import model.services.UsersServices;

public class UsersListController implements Initializable{
	
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

	/* ao exibir os nivel de acesso o sistema deverá converter o contudo do banco 
	 * que está em integer em string, seguindo essa recomendação:
	 * 1 - Administrador
	 * 2 - Operador
	 * 3 - Viewer
	 */
	@FXML  
	private TableColumn<Users, Integer> tableColumnuserLevelAccess;
	
	private ObservableList<Users> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/UsersForm.fxml", parentStage);
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
		
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
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

}
