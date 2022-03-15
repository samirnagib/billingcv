package gui;

import gui.util.Alerts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private PasswordField txtSenha;
	
	@FXML
	private Button btOK;
	
	@FXML 
	private Button btCancel;
	
	@FXML
	public void onBtOKAction() {
		System.out.println("OK - Click");
	}
	
	@FXML
	public void onBtCancelAction() {
		Platform.exit();  //Encerra a aplicação.
	}

}
