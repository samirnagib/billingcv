package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.jbdcDAO;
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
	public void onBtOKAction() throws SQLException {
		// validação dos campos de usuario e senha
		System.out.println("OK - Click");
		if (txtUser.getText().isEmpty()) {
			Alerts.showAlert("Mensagem de Erro", "", "O Campo usuário não pode estar em branco", AlertType.ERROR);
			return;
		}
		
		if (txtSenha.getText().isEmpty()) {
			Alerts.showAlert("Mensagem de Erro", "", "O campo Senha não pode estar em branco", AlertType.ERROR);
			return;
		}
		System.out.println(txtUser.getText().toUpperCase());
		System.out.println(txtSenha.getText().toUpperCase());
		
		String LoginUser = txtUser.getText();
		String UserPassword = txtSenha.getText();
		
		jbdcDAO jay = new jbdcDAO();
		boolean flag = jay.validate(LoginUser, UserPassword);
		
		if (!flag) {
			Alerts.showAlert("Mensagem de Erro", "", "Usuário ou senha inválidos", AlertType.ERROR);
        } else {
        	Alerts.showAlert("Confirmação", "", "Acesso concedido", AlertType.CONFIRMATION);
        }
		

	}
	
	@FXML
	public void onBtCancelAction() {
		Platform.exit();  //Encerra a aplicação.
	}



}
