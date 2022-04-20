package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.InputBillServices;
import model.services.OwnerServices;
import model.services.UsersServices;
import reports.report;

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
	private MenuItem menuChargeBack;
	
	
	@FXML
	private MenuItem menuImportClients;
	
	@FXML
	private MenuItem menuImportCVFile;
	
	@FXML
	private MenuItem menuReportVideo;
	
	@FXML
	private MenuItem menuReportChargeBack;
	
	@FXML
	private MenuItem menuReportPrint;
	
	@FXML
	private MenuItem menuAbout;
	
	@FXML
	private MenuItem menuExit;
	
	
	@FXML
	public void onMenuUsersAction() {
		loadView("/gui/UsersList.fxml", (UsersListController controller) -> {
			controller.setUsersService(new UsersServices());
			controller.updateTableView();	
		});
	}
	
	@FXML
	public void onMenuClientTypeAction() {
		loadView("/gui/ClientTypeList.fxml", (ClientTypeListController controller) -> {
			controller.setClientTypeServices(new ClientTypeServices());
			controller.updateTableView();
		});	
	}
	
	@FXML
	public void onMenuOwnerAction() {
		loadView("/gui/OwnerList.fxml", (OwnerListController controller) -> {
			controller.setOwnerServices(new OwnerServices());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuClientsAction() {
		loadView("/gui/ClientList.fxml", (ClientListController controller) -> {
			controller.setClientServices(new ClientServices());
			controller.updateTableView();
		});
		//           /gui/ClientList.fxml
		// loadView("/gui/ClientList.fxml", x -> {} );
	}
	
	@FXML
	public void onMenuBillTagAction() {
		loadView("/gui/BillTagsList.fxml", (BillTagsListController controller) -> {
			controller.setBillTagsServices(new BillTagsServices());
			controller.updateTableView();
		});	
		
	}
	
	@FXML
	public void onMenuChargeBackAction() {
		loadView("/gui/InputBillList.fxml", (InputBillListController controller) -> {
			controller.setServices(new InputBillServices(), new ClientServices(), new ClientTypeServices(), new OwnerServices(), new BillTagsServices());
			controller.loadAssociatedObjects();
			controller.updateTableView("ALL",null, null);
		});
		
	}
	@FXML
	public void onMenuImportClientsAction() {
		loadView("/gui/ClientImport.fxml",  x -> {}  );
				
	}
	
	@FXML
	public void onMenuImportCVFileAction() {
		loadView("/gui/ImportCommvaultFile.fxml", x -> {} );		
	}
	
	@FXML
	public void onMenuReportListClientAction() {
		report.callRelatorio("ListagemClientes", "Listagem de clientes");		
	}
	

	@FXML
	public void onMenuReportListChargeBackAction() {
		System.out.println("onMenuReportListChargeBackAction");	
		loadView("/gui/PrintFatura.fxml", x -> {} );
	}
	
	
	@FXML
	public void onMenuReportPrintAction() {
		System.out.println("Faixas de Cobrança: onMenuReportPrintAction");		
	}
	
	@FXML
	public void onMenuAboutAction() {
		loadView("/gui/About.fxml", x -> {} );		
	}
		
	@FXML
	public void onMenuExitAction(ActionEvent event) {
		
		Platform.exit();  //Encerra a aplicação.
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = LoginController.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller =  loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loadind view", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
		
	}
	
	private synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = LoginController.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			loader.getController();
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loadind view", e.getMessage(), AlertType.ERROR);
		}
		
	} 

}
