package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Users;

public class UsersListController implements Initializable{

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
	private TableColumn<Users, String> tableColumnuserLevelAccess;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
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
		tableColumnuserLevelAccess.setCellValueFactory(new PropertyValueFactory<>("useruserLevelAccess"));
		
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewUsers.prefHeightProperty().bind(stage.heightProperty());
		
	}

}
