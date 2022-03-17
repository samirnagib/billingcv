package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.UsersServices;

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
	public void onMenuAboutAction() {
		loadView("/gui/About.fxml", x -> {} );		
	}
		
	@FXML
	public void onMenuExitAction() {
		System.out.println("onMenuExitAction");
		//Alerts.showConfirmation("Pergunta", "", "Você deseja Sair?");
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
		}
		
	}
/*	
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
			
			UsersListController controller = loader.getController();
			controller.setUsersService(new UsersServices());
			controller.updateTableView();
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loadind view", e.getMessage(), AlertType.ERROR);
		}
		
	} */

}
