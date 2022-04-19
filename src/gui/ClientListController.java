package gui;

import java.awt.Frame;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.Client;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.OwnerServices;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;





public class ClientListController implements Initializable, DataChangeListener {


	@FXML
	private TableView<Client> tableViewClient;
	
	@FXML
	private TableColumn<Client, Integer> tableColumnidClient;
	
	@FXML
	private TableColumn<Client, String> tableColumnclientName;
	
	@FXML
	private TableColumn<Client, String> tableColumnclientHostname;
	
	@FXML
	private TableColumn<Client, String> tableColumntypeName;
	
	@FXML
	private TableColumn<Client, String> tableColumnowName;
	
	@FXML
	private TableColumn<Client, Client> tableColumnEDIT;
	
	@FXML
	private TableColumn<Client, Client> tableColumnREMOVE;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private Button brPrint;
	
	private ClientServices service;
	
	private ObservableList<Client> obsList;
	
	public void setClientServices(ClientServices service) {
		this.service = service;
	}
	
	@FXML
	private void btNovoOnAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Client obj = new Client();
		createDialogForm(obj, "/gui/ClientForm.fxml", parentStage);
		//System.out.println("btNovoOnAction");
	}
	
	
	@FXML
	private void brPrintOnAction() {
		/*Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		Utils.configureFileChooserImportFiles(fileChooser, "Abrir arquivo de relatorio");
		
		String fileName =fileChooser.showOpenDialog(stage).getAbsolutePath(); 
		System.out.println(fileName);
		*/ 
		String fileName = "D:\\hds\\Billing\\workspace\\billingcv\\src\\reports\\ListagemClientes.jrxml";
		
		try {
			JasperReport jasperReport =  JasperCompileManager.compileReport(fileName);
			Connection conn = DB.getConnection();
			Map<String, Object> hm = new HashMap<String,Object>();
			
			JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, conn);
			JasperViewer oia = new JasperViewer(print, false);
			oia.setVisible(true);
			oia.setExtendedState(Frame.MAXIMIZED_BOTH);
			oia.setTitle("Title");
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}/*	
		*/			
				
		System.out.println("Sent to printer.");
		
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnidClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
		tableColumnclientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
		tableColumnclientHostname.setCellValueFactory(new PropertyValueFactory<>("clientHostname"));
		
		
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewClient.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<Client> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewClient.setItems(obsList);
		
		initEditButtons();
		initRemoveButtons(); 
	}

	private void createDialogForm(Client obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			ClientFormController controller = loader.getController();
			controller.setClient(obj);
			controller.setServices(new ClientServices(), new ClientTypeServices(), new OwnerServices());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com dos dados do Cliente:");
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

	@Override
	public void onDataChanged() {
		updateTableView();
		
	}
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Client, Client>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Client obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/ClientForm.fxml", Utils.currentStage(event)));
						
			}
		});
	}
	
		
		
	
	
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Client, Client>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(Client obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
			
		});
	} 
	
	private void removeEntity(Client obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}


}
