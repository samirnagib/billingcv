package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.jbdcDAO;
import gui.util.Alerts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private PasswordField txtSenha;
	
	@FXML
	private Button btOK;
	
	@FXML 
	private Button btCancel;
	
	Stage stage1 = null;
	
	@FXML
	public void onBtOKAction() throws SQLException, IOException, Exception {
		// validação dos campos de usuario e senha
		//System.out.println("OK - Click");
		if (txtUser.getText().isEmpty()) {
			Alerts.showAlert("Mensagem de Erro", "", "O Campo usuário não pode estar em branco", AlertType.ERROR);
			return;
		}
		
		if (txtSenha.getText().isEmpty()) {
			Alerts.showAlert("Mensagem de Erro", "", "O campo Senha não pode estar em branco", AlertType.ERROR);
			return;
		}
		//System.out.println(txtUser.getText().toUpperCase());
		//System.out.println(txtSenha.getText().toUpperCase());
		
		String LoginUser = txtUser.getText();
		String UserPassword = txtSenha.getText();
		
		jbdcDAO jay = new jbdcDAO();
		boolean flag = jay.validate(LoginUser, UserPassword);
		
		if (!flag) {
			Alerts.showAlert("Mensagem de Erro", "", "Usuário ou senha inválidos", AlertType.ERROR);
        } else {
        	//Alerts.showAlert("Confirmação", "", "Acesso concedido", AlertType.CONFIRMATION);
        	// Carrega o formulario principal e fecha o formulario de login
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainForm.fxml"));
        	ScrollPane scrollPane = loader.load();
        	scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
        	Stage Stage1 = new Stage();
        	Stage1.setTitle("Sistema de rateio de custos de backup");
        	Scene scene = new Scene(scrollPane);
        	Stage1.setScene(scene);
        	Stage1.show();
        	closeLogin();
        	
        }

	}
	
	public void closeLogin() {
		stage1 = (Stage) btOK.getScene().getWindow();
		stage1.close();
	}
	
	@FXML
	public void onBtCancelAction() {
		Platform.exit();  //Encerra a aplicação.
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}



}
