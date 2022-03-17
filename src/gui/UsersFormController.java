package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.UserAccessLevel;

public class UsersFormController implements Initializable {

	private ObservableList<UserAccessLevel> obsList;
	
	@FXML
	private TextField txtuserId;
	
	@FXML
	private TextField txtuserLogin;

	@FXML
	private PasswordField txtuserPasswd1;
	
	@FXML
	private PasswordField txtuserPasswd2;

	
	@FXML
	private TextField txtuserFullName;
	
	@FXML
	private TextField txtuserEmail;
	
	@FXML
	private ComboBox<UserAccessLevel> comboBoxUserLevelAccess;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Label lbErroruserLogin;
	
	@FXML
	private Label lbErroruserPasswd1;

	@FXML
	private Label lbErroruserPasswd2;

	@FXML
	private Label lbErroruserFullName;

	@FXML
	private Label lbErroruserEmail;

	@FXML
	private Label lbErrorUserLevelAccess;

	@FXML
	private void onbtSaveAction() {
		System.out.println("btSalvar");
	}
	
	@FXML
	private void onbtCancelAction() {
		System.out.println("btCancel");
	}
	
	@FXML
	private void oncomboBoxUserLevelAccess() {
		UserAccessLevel ual = comboBoxUserLevelAccess.getSelectionModel().getSelectedItem();
		System.out.println(ual.getIdLevel());
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		List<UserAccessLevel> list = new ArrayList<>();
		list.add(new UserAccessLevel(1, "Administrador"));
		list.add(new UserAccessLevel(2, "Operador"));
		list.add(new UserAccessLevel(3, "Vizualizador"));
		
		obsList = FXCollections.observableArrayList(list);
		comboBoxUserLevelAccess.setItems(obsList);
		
		Callback<ListView<UserAccessLevel>, ListCell<UserAccessLevel>> factory = lv -> new ListCell<UserAccessLevel>() {
		    @Override
		    protected void updateItem(UserAccessLevel item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty ? "" : item.getLevelAccess());
		    }
		};

		comboBoxUserLevelAccess.setCellFactory(factory);
		comboBoxUserLevelAccess.setButtonCell(factory.call(null));
		
	}
}
