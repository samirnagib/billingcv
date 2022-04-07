package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.BillTags;
import model.entities.Client;
import model.entities.InputBill;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.InputBillServices;
import model.services.OwnerServices;

public class InputBillListController implements Initializable, DataChangeListener {
	
	@FXML
	private Button btNovo;
	
	@FXML
	private Button btRecalcular;
	
	@FXML
	private Button btEraseCPT;
	
	@FXML
	private Button btClearFilters;
	
	@FXML
	private ComboBox<InputBill> cbCompetencia;
	
	@FXML
	private ComboBox<InputBill> cbClientes;
	
	@FXML
	private CheckBox chkFilter;
	
	@FXML
	private TableView<InputBill> tableViewFatura;
	
	@FXML
	private TableColumn<InputBill, Integer> tc_idInputBill;
	
	@FXML
	private TableColumn<InputBill, String> tc_ib_ano_mes;
	
	@FXML
	private TableColumn<InputBill, String> tc_ClientName;
	
	@FXML
	private TableColumn<InputBill, String> tc_BillTag;
	
	@FXML
	private TableColumn<InputBill, String> tc_Agente;
	
	@FXML
	private TableColumn<InputBill, String> tc_Instancia;
	
	@FXML
	private TableColumn<InputBill, String> tc_BackupSet;
	
	@FXML
	private TableColumn<InputBill, String> tc_SubClient;
	
	@FXML
	private TableColumn<InputBill, Double> tc_Total;
	
	@FXML
	private TableColumn<InputBill, InputBill> tableColumnEDIT;
	
	@FXML
	private TableColumn<InputBill, InputBill> tableColumnREMOVE;
	
	private InputBillServices ibServices;
	private ClientServices clServices;
	private ClientTypeServices ctServices;
	private OwnerServices owServices;
	private BillTagsServices btServices;
	
	private ObservableList<InputBill> obsListClient;
	private ObservableList<InputBill> obsListIBCPT;
	private ObservableList<InputBill> obsListIBTV;
	
	public void setServices(InputBillServices ibServices, ClientServices clServices, ClientTypeServices ctServices, OwnerServices owServices, BillTagsServices btServices) {
		this.ibServices = ibServices;
		this.clServices = clServices;
		this.ctServices = ctServices;
		this.owServices = owServices;
		this.btServices = btServices;
	}
	
	
	

	@FXML
	private void chkFilterOnAction() {
		if ( chkFilter.selectedProperty().getValue() == true ) {
			cbClientes.setDisable(false);
			loadClientObjects();
			initializeComboBoxClientes();
			
		} else {
			cbClientes.getSelectionModel().clearSelection();
			cbClientes.getItems().clear();
			cbClientes.setDisable(true);
			
		}
		
	}
	
	@FXML
	private void btNovoOnAction() {
		System.out.println("btNovoOnAction");
	};
	
	@FXML
	private void btRecalcularOnAction() {
		System.out.println("btRecalcularOnAction");
	};
	
	@FXML
	private void btEraseCPTOnAction() {
		System.out.println("btEraseCPTOnAction");
	};
	
	@FXML
	private void btClearFiltersOnAction(ActionEvent e) {
		System.out.println("btClearFilters");
		cbCompetencia.getSelectionModel().clearAndSelect(0);
		initializeComboBoxCompetencia();
		updateTableView("ALL");
	};
	
	@FXML
	private void cbCompetenciaOnAction() {
		cbCompetencia.setOnAction(e -> {
			InputBill ib = new InputBill();
			ib = cbCompetencia.getValue();
			updateTableView((String) ib.getIb_ano_mes()) ;
			System.out.println(ib.getIb_ano_mes());
		});
	}
	
	@FXML
	private void cbClientesOnAction() {
		System.out.println("cbClientesOnAction");
		System.out.println(cbClientes.getValue());
	}
	
	@Override
	public void initialize(URL url, ResourceBundle br) {
		initializeNodes();
	}

	
	private void createDialogForm(InputBill obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			InputBillFormController controller = loader.getController();
			
			
			controller.setServices(new InputBillServices(), new ClientServices(), new ClientTypeServices(), new OwnerServices(), new BillTagsServices());
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
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	
	private void initializeNodes() {
		
		
		
		// tc_idInputBill.setCellValueFactory(new PropertyValueFactory<>("idInputBill"));
		tc_idInputBill.setCellValueFactory(new Callback<CellDataFeatures<InputBill, Integer>, ObservableValue<Integer>>() {
			public ObservableValue<Integer> call(CellDataFeatures<InputBill, Integer> p){
				return  new ReadOnlyObjectWrapper(p.getValue().getIdInputBill());
			}
		});
		tc_ib_ano_mes.setCellValueFactory(new PropertyValueFactory<>("ib_ano_mes"));
		//tc_ClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
		tc_ClientName.setCellValueFactory(new Callback<CellDataFeatures<InputBill, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<InputBill, String> p){
				return  new ReadOnlyObjectWrapper(p.getValue().getClient().getClientName());
			}
		});
		
		//tc_BillTag.setCellValueFactory(new PropertyValueFactory<>("billtagName"));
		tc_BillTag.setCellValueFactory(new Callback<CellDataFeatures<InputBill, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<InputBill, String> p){
				return  new ReadOnlyObjectWrapper(p.getValue().getBilltag().getBilltagName());
			}
		});
		tc_Agente.setCellValueFactory(new PropertyValueFactory<>("cv_agent"));
		tc_Instancia.setCellValueFactory(new PropertyValueFactory<>("cv_instance"));
		tc_BackupSet.setCellValueFactory(new PropertyValueFactory<>("cv_backupset"));
		tc_SubClient.setCellValueFactory(new PropertyValueFactory<>("cv_subclient"));
		tc_Total.setCellValueFactory(new PropertyValueFactory<>("ib_taxcalculated"));
		Utils.formatTableColumnDouble(tc_Total, 2);
		
		
		initializeComboBoxCompetencia();
		
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewFatura.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void loadAssociatedObjects() {
		if (ibServices == null ) {
			throw new IllegalStateException("ibServices was nulll");
		}
		
		List<InputBill> lstCpt = ibServices.listCompetencia();
		List<InputBill> lstClientes = ibServices.listDistinctClient();
		
		obsListIBCPT = FXCollections.observableArrayList(lstCpt);
		cbCompetencia.setItems(obsListIBCPT);
		
		obsListClient = FXCollections.observableArrayList(lstClientes);
		cbClientes.setItems(obsListClient);
			
	}
	
	private void loadClientObjects() {
		if (ibServices == null ) {
			throw new IllegalStateException("ibServices was nulll");
		}
		List<InputBill> lstClientes = ibServices.listDistinctClient();
		obsListClient = FXCollections.observableArrayList(lstClientes);
		cbClientes.setItems(obsListClient);
	}
	
	public void updateTableView(String Method) {
		if (ibServices == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<InputBill> list = new ArrayList<>();
		if (Method.equals("ALL") ) {
			list = ibServices.findAll();
		} else {
			list = ibServices.findByCompetencia(Method);
		}
		
		obsListIBTV = FXCollections.observableArrayList(list);
		tableViewFatura.setItems(obsListIBTV);
		
//		initEditButtons();
//		initRemoveButtons(); 
	}
	
	private void initializeComboBoxCompetencia() {
		Callback<ListView<InputBill>, ListCell<InputBill>> factory = lv -> new ListCell<InputBill>() {
			@Override
			protected void updateItem(InputBill item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getIb_ano_mes());
			}
		};
		cbCompetencia.setCellFactory(factory);
		cbCompetencia.setButtonCell(factory.call(null)); 
	}
	
	private void initializeComboBoxClientes() {
		Callback<ListView<InputBill>, ListCell<InputBill>> factory = lv -> new ListCell<InputBill>() {
			@Override
			protected void updateItem(InputBill item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getClient().getClientName());
			}
		};
		cbClientes.setCellFactory(factory);
		cbClientes.setButtonCell(factory.call(null)); 
	}
	

	@Override
	public void onDataChanged() {
		updateTableView("ALL");
		
	}

}
