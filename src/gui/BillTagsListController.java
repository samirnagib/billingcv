package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.BillTags;
import model.services.BillTagsServices;


public class BillTagsListController implements Initializable, DataChangeListener {

	@FXML
	private TableView<BillTags> tableViewBillTags;
	
	@FXML
	private TableColumn<BillTags, Integer> tableColumnidBillTag;
	
	@FXML
	private TableColumn<BillTags, String> tableColumnbillTagName;
	
	@FXML
	private TableColumn<BillTags, Double> tableColumnbillPriceTB;
	
	@FXML
	private TableColumn<BillTags, BillTags> tableColumEDIT;
	
	@FXML
	private TableColumn<BillTags, BillTags> tableColumREMOVE;
	
	
	@FXML
	private Button btNovo;
	
	private BillTagsServices service;
	private ObservableList<BillTags> obsList;
	
	@FXML
	private void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		BillTags obj = new BillTags();
		createDialogForm(obj, "/gui/BillTagsForm.fxml", parentStage);
		
	}
	
	public void setBillTagsServices(BillTagsServices service) {
		this.service = service;
	}
	
	@Override
	public void onDataChanged() {
		updateTableView();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnidBillTag.setCellValueFactory(new PropertyValueFactory<>("idbillTag"));
		tableColumnbillTagName.setCellValueFactory(new PropertyValueFactory<>("billtagName"));
		tableColumnbillPriceTB.setCellValueFactory(new PropertyValueFactory<>("billPriceTB"));
		Utils.formatTableColumnDouble(tableColumnbillPriceTB, 2);
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewBillTags.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<BillTags> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewBillTags.setItems(obsList);
		initEditButtons();
		initRemoveButtons(); 
	}

	private void createDialogForm(BillTags obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			BillTagsFormController controller = loader.getController();
			controller.setBillTags(obj); 
			controller.setBillTagsService(new BillTagsServices());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com dos dados do responsável:");
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

	private void initEditButtons() {
		tableColumEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumEDIT.setCellFactory(param -> new TableCell<BillTags, BillTags>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(BillTags obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/BillTagsForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	
	private void initRemoveButtons() {
		tableColumREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumREMOVE.setCellFactory(param -> new TableCell<BillTags, BillTags>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(BillTags obj, boolean empty) {
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
	
	private void removeEntity(BillTags obj) {
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
