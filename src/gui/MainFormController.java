package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;

public class MainFormController implements Initializable {

	@FXML
	private MenuItem menuUsers;
	
	@FXML
	private MenuItem menuClientType;
	
	@FXML
	private MenuItem menuOwner;
	
	@FXML
	private MenuItem menuClients;
	
	@FXML
	private MenuItem menuBillTag;
	
	@FXML
	private MenuItem menuImportClients;
	
	@FXML
	private MenuItem menuImportCVFile;
	
	@FXML
	private MenuItem menuReportVideo;
	
	@FXML
	private MenuItem menuReportPrint;
	
	@FXML
	private MenuItem menuExit;
	
	
	@FXML
	public void onMenuUsersAction() {
		System.out.println("onMenuUsersAction");
	}
	
	@FXML
	public void onMenuClientTypeAction() {
		System.out.println("onMenuClientTypeAction");		
	}
	
	@FXML
	public void onMenuOwnerAction() {
		System.out.println("onMenuOwnerAction");		
	}
	
	@FXML
	public void onMenuClientsAction() {
		System.out.println("onMenuClientsAction");		
	}
	
	@FXML
	public void onMenuBillTagAction() {
		System.out.println("onMenuBillTagAction");		
	}
	
	@FXML
	public void onMenuImportClientsAction() {
		System.out.println("onMenuImportClientsAction");		
	}
	
	@FXML
	public void onMenuImportCVFileAction() {
		System.out.println("onMenuImportCVFileAction");		
	}
	
	@FXML
	public void onMenuReportVideoAction() {
		System.out.println("onMenuReportVideoAction");		
	}
	
	@FXML
	public void onMenuReportPrintAction() {
		System.out.println("onMenuReportPrintAction");		
	}
	
	@FXML
	public void onMenuExitAction() {
		System.out.println("onMenuExitAction");
		//Alerts.showConfirmation("Pergunta", "", "Você deseja Sair?");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
	}

}
