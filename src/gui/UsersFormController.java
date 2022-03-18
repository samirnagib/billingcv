package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.UserAccessLevel;
import model.entities.Users;
import model.exceptions.ValidationException;
import model.services.UsersServices;

public class UsersFormController implements Initializable {

	private Users entity;
	private UsersServices service;
	private ObservableList<UserAccessLevel> obsList;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
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
	private void onbtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saverOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		} 
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListener() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Users getFormData() {
		Users obj = new Users();
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setUserId(Utils.tryParseToInt(txtuserId.getText()));
		
		if (txtuserLogin.getText() == null || txtuserLogin.getText().trim().equals("")) {
			exception.addError("login", "O Campo não pode estar vazio");
		}
		if (txtuserPasswd1.getText() == null || txtuserPasswd1.getText().trim().equals("") || txtuserPasswd2.getText() == null || txtuserPasswd2.getText().trim().equals("") ) {
			exception.addError("passwd1", "O Campo não pode estar vazio");
			exception.addError("passwd2", "O Campo não pode estar vazio");
		
		} else {

			if ( validatePasswdEquals(txtuserPasswd1.getText(), txtuserPasswd2.getText()) == false ) {
				exception.addError("password", "Os campos de Senha e Confirmacção de senha devem ser iguais");
			}

		}
	
		
		if (txtuserFullName.getText() == null || txtuserFullName.getText().trim().equals("")) {
			exception.addError("name", "O Campo não pode estar vazio");
		}
		if (txtuserEmail.getText() == null || txtuserEmail.getText().trim().equals("")) {
			exception.addError("email", "O Campo não pode estar vazio");
		}
		else {
			if (Utils.validaEmail(txtuserEmail.getText()) == false) {
				exception.addError("email2", "Não é um email válido.");
			}
		}
		obj.setUserLogin(txtuserLogin.getText());
		obj.setUserPasswd(txtuserPasswd1.getText());
		obj.setUserFullName(txtuserFullName.getText());
		obj.setUserEmail(txtuserEmail.getText());
		UserAccessLevel uac = comboBoxUserLevelAccess.getSelectionModel().getSelectedItem();
		obj.setUserLevelAccess(uac.getIdLevel());

		return obj;
		
	}

	@FXML
	private void onbtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@FXML
	private void oncomboBoxUserLevelAccess() {
		UserAccessLevel ual = comboBoxUserLevelAccess.getSelectionModel().getSelectedItem();
		System.out.println(ual.getIdLevel());
	}
	
	@FXML
		
	public void setUsers(Users entity) {
		this.entity = entity;
	}
	
	public void setUsersServices(UsersServices service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
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
		
		initializedNodes();

	}
	
	private void initializedNodes() {
		Constraints.setTextFieldInteger(txtuserId);
		Constraints.setTextFieldMaxLength(txtuserFullName, 200);
		Constraints.setTextFieldMaxLength(txtuserLogin, 200);
		Constraints.setTextFieldMaxLength(txtuserPasswd1, 200);
		Constraints.setTextFieldMaxLength(txtuserPasswd2, 200);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtuserId.setText(String.valueOf(entity.getUserId()));
		txtuserLogin.setText(entity.getUserLogin());
		txtuserPasswd1.setText(entity.getUserPasswd());
		txtuserPasswd2.setText(entity.getUserPasswd());
		txtuserFullName.setText(entity.getUserFullName());
		txtuserEmail.setText(entity.getUserEmail());
		UserAccessLevel uac = comboBoxUserLevelAccess.getSelectionModel().getSelectedItem();
		if (uac != null) {
			switch (uac.getIdLevel()) {
			case 1:
				comboBoxUserLevelAccess.getSelectionModel().select(1);
				break;
			case 2:
				comboBoxUserLevelAccess.getSelectionModel().select(2);
				break;
			case 3:
				comboBoxUserLevelAccess.getSelectionModel().select(3);
				break;
			default:
				comboBoxUserLevelAccess.getSelectionModel().clearSelection();
	
			}
		}
	}
	
	private boolean validatePasswdEquals(String passwordField1, String passwordField2) {
		if (passwordField1.equals(passwordField2)) {
			return true;
		
		}
		return false;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("login")) {
			lbErroruserLogin.setText(errors.get("login"));
		}
		if (fields.contains("passwd1")) {
			lbErroruserPasswd1.setText(errors.get("passwd1"));
		}
		if (fields.contains("passwd2")) {
			lbErroruserPasswd2.setText(errors.get("passwd2"));
		}
		if (fields.contains("password")) {
			lbErroruserPasswd2.setText(errors.get("password"));
		}
		if (fields.contains("name")) {
			lbErroruserFullName.setText(errors.get("name"));
		}
		if (fields.contains("email")) {
			lbErroruserEmail.setText(errors.get("email"));
		}
		if (fields.contains("email2")) {
			lbErroruserEmail.setText(errors.get("email2"));
		}
		
	}
	
}
