package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.plaf.synth.SynthSeparatorUI;

import db.DbIntegrityException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
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
	private ComboBox<String> cbCompetencia;
	
	@FXML
	private ComboBox<String> cbClientes;
	
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
	
	private ObservableList<String> obsListClient;
	
	private ObservableList<String> obsListIBCPT;
	private ObservableList<InputBill> obsListIBTV;
	
	private List<String> Servidores = new ArrayList<>();	
	private List<String> Meses = new ArrayList<>();
	
	private String competencia;
	
	//public String actForm;
	
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
						
		} else {
			updateTableView("ALL", null, null);
			cbClientes.setDisable(true);
			
		}
		
	}
	
	@FXML
	private void btNovoOnAction(ActionEvent event) {
		//System.out.println("btNovoOnAction");
		Stage parentStage = Utils.currentStage(event);
		InputBill obj = new InputBill();
		createDialogForm(obj, "/gui/InputBillForm.fxml", parentStage, "NEW");
		
		
		
		//createDialogForm(obj, "/gui/InputBillForm.fxml", parentStage);
		
		
	};
	
	@FXML
	private void btRecalcularOnAction() {
		System.out.println("btRecalcularOnAction");
		if (competencia == null) {
			Alerts.showAlert("MENSAGEM DE ERRO", null, "Favor seleciona uma competência", AlertType.ERROR);
			cbCompetencia.requestFocus();
		} else {
			
			List<InputBill> fatura = new ArrayList<>();
			// BillTags preco = new BillTags();
			fatura = ibServices.findByCompetencia(competencia);
			//preco = btServices.findAll();
			for (InputBill item : fatura ) {
				
				System.out.println("Codigo da Fatura" + item.getIdInputBill());
				
				double feb = item.getCv_febackupsize();
				double fea = item.getCv_fearchivesize();
				double pria = item.getCv_primaryappsize();
				double proa = item.getCv_protectedappsize();
				double ms = item.getCv_mediasize();
				
				BillTags preco = btServices.findByName((String) item.getBilltag().getBilltagName());
				
				double vrUnitario = (double) preco.getBillPriceTB();
				double valorTotal = (feb + fea + pria + proa + ms) * vrUnitario;

				item.setIb_taxcalculated(valorTotal);
				
				ibServices.saveORupdate(item);
				
			}
			updateTableView("CPT", competencia, null);
		}
		
		
		
	};
	
	@FXML
	private void btEraseCPTOnAction() {
		//System.out.println("btEraseCPTOnAction");
		if (competencia == null) {
			Alerts.showAlert("MENSAGEM DE ERRO", null, "Favor seleciona uma competência", AlertType.ERROR);
			cbCompetencia.requestFocus();
		} else {
			ibServices.removeCompetencia(competencia);
			updateTableView("ALL", null, null);
		}
		
		
	};
	
	@FXML
	private void btClearFiltersOnAction(ActionEvent e) {
		chkFilter.fire();
		competencia= "";
		cbCompetencia.getSelectionModel().selectFirst();
		updateTableView("ALL", null, null);
	};
	
	@FXML
	private void cbCompetenciaOnAction() {
		competencia = cbCompetencia.getValue();
		updateTableView("CPT",competencia,null);
	}
	
	@FXML
	private void cbClientesOnAction() {
		String servidor = cbClientes.getValue();
		updateTableView("SRV", competencia, servidor);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle br) {
		initializeNodes();
	}

	
	private void createDialogForm(InputBill obj, String absoluteName, Stage parentStage, String modo) {
		try {
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			InputBillFormController controller = loader.getController();
			
			controller.setInputBill(obj);
			controller.setFormAct(modo);
			controller.setServices(new InputBillServices(), new ClientServices(), new ClientTypeServices(), new OwnerServices(), new BillTagsServices());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Registro de Movimentação única:");
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
		
		
		// initializeComboBoxCompetencia();
		
		Stage stage = (Stage) LoginController.getMainScene().getWindow();
		tableViewFatura.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void loadAssociatedObjects() {
		if (ibServices == null ) {
			throw new IllegalStateException("ibServices was nulll");
		}
		
		List<InputBill> lstCpt = ibServices.listCompetencia();
		List<InputBill> lstClientes = ibServices.listDistinctClient();
		
		for ( InputBill ib : lstCpt ) {
			Meses.add(ib.getIb_ano_mes()) ;
		}
		

		for ( InputBill ib : lstClientes ) {
			Servidores.add(ib.getClient().getClientName().toUpperCase()) ;
		}
		
		
		obsListIBCPT = FXCollections.observableArrayList(Meses);
		cbCompetencia.setItems(obsListIBCPT);
		
		obsListClient = FXCollections.observableArrayList(Servidores);
		cbClientes.setItems(obsListClient);
			
	}
	

	
	public void updateTableView(String Method, String competencia, String servidor) {
		/* DEFAULTS PARAMETERS
		 *  ALL - List All Clientes and Competencias
		 * 	CPT - Filter by Competencia
		 *  SRV - Filter by Compentencia and Clients
		 */
		if (ibServices == null) {
			throw new IllegalStateException("O Service estava nulo");
		}
		List<InputBill> list = new ArrayList<>();
		if (Method.equals("ALL") ) {
			list = ibServices.findAll();
		} else if (Method.equals("CPT") )  {
			if (competencia != null ) {
				list = ibServices.findByCompetencia(competencia);
			} else {
				throw new IllegalArgumentException("Os parametros Competencia e Servidor não podem estar vazios");
			}
			
		} else if (Method.equals("SRV") )  {
			if (competencia != null && servidor != null) {
				list = ibServices.findByCompetenciaAndClient(competencia, servidor);
			} else {
				throw new IllegalArgumentException("Os parametros Competencia e Servidor não podem estar vazios");
			}
		}
		
		obsListIBTV = FXCollections.observableArrayList(list);
		tableViewFatura.setItems(obsListIBTV);
		
		initEditButtons();
		initRemoveButtons(); 
	}
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<InputBill, InputBill>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(InputBill obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						                          
						event -> createDialogForm(obj, "/gui/InputBillForm.fxml", Utils.currentStage(event),"EDT"));
						
			}
		});
	}
	
	
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<InputBill, InputBill>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(InputBill obj, boolean empty) {
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
	
	private void removeEntity(InputBill obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (ibServices == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				ibServices.remove(obj);
				updateTableView("ALL", null, null);
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	

	@Override
	public void onDataChanged() {
		updateTableView("ALL",null,null);
		
	}

}
