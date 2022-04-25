package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.entities.InputBill;
import model.entities.Owner;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.InputBillServices;
import model.services.OwnerServices;

public class PrintFormController implements Initializable {

	@FXML
	private Button btPrint;
	
	@FXML
	private Button btFechar;
	
	@FXML
	private Button btClearFilter;
	
	@FXML
	private ComboBox<String> cbCompetencia;
	
	@FXML
	private ComboBox<String> cbResponsavel;
	
	@FXML
	private Label lbFiltroAplicado;
	
	private InputBillServices ibServices;
	private ClientServices clServices;
	private ClientTypeServices ctServices;
	private OwnerServices owServices;
	private BillTagsServices btServices;
	
	private ObservableList<String> obsListOwner;
	
	private ObservableList<String> obsListIBCPT;
	
	
	private List<String> Responsaveis = new ArrayList<>();	
	private List<String> Meses = new ArrayList<>();
	
	private boolean bCPT = false;
	private boolean bOWN = false;
	
			
	public void setServices(InputBillServices ibServices, ClientServices clServices, ClientTypeServices ctServices, OwnerServices owServices, BillTagsServices btServices) {
		this.ibServices = ibServices;
		this.clServices = clServices;
		this.ctServices = ctServices;
		this.owServices = owServices;
		this.btServices = btServices;
	}
	
	
	
	
	
	
	@FXML
	private void btPrintOnAction() {
		System.out.println("btPrint");
	}
	
	@FXML
	private void brFecharOnAction() {
		System.out.println("btFechar");
	}
	
	@FXML
	private void btClearFiltersOnAction() {
		System.out.println("btClearFilters");
	}
	
	@FXML
	private void cbCompetenciaOnAction() {
		System.out.println("cbCompetencia");
		if ( bCPT = true ) {
			if ( cbCompetencia.getValue() == "") {
				lbFiltroAplicado.setText( loadFilterFatura(false,false) );
				
			}
			
			lbFiltroAplicado.setText( loadFilterFatura(true,false) );
			
		} else {
			lbFiltroAplicado.setText( loadFilterFatura(true,false) );
			bCPT = true;
		}
		
		
	}
	
	@FXML
	private void cbResponsavelOnAction() {
		System.out.println("cbResponsavel");
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	

	public void loadAssociatedObjects() {
		if (ibServices == null ) {
			throw new IllegalStateException("ibServices was nulll");
		}
		
		List<InputBill> lstCpt = ibServices.listCompetencia();
		List<Owner> lstOwner = owServices.findAll();
		Meses.add("TODAS");
		Responsaveis.add("TODOS");
		
		for ( InputBill ib : lstCpt ) {
			Meses.add(ib.getIb_ano_mes()) ;
		}
		

		for ( Owner ow : lstOwner ) {
			Responsaveis.add(ow.getOwName());
		}
		
		
		obsListIBCPT = FXCollections.observableArrayList(Meses);
		cbCompetencia.setItems(obsListIBCPT);
		
		obsListOwner = FXCollections.observableArrayList(Responsaveis);
		cbResponsavel.setItems(obsListOwner);
		
		lbFiltroAplicado.setText( loadFilterFatura(false,false) );
			
	}
	
	private String loadFilterFatura(boolean Competencia, boolean Responsavel) {
		boolean cpt = Competencia;
		boolean own = Responsavel;
		
		String retCPT;
		String retOWN;
		
		if ( cpt = true) {
			retCPT = cbCompetencia.getValue();
			if ( retCPT == null ) {
				retCPT = " Todas Competencias ";
			}
				
		} else {
			retCPT = " Todas Competencias ";
		}
	
		if ( own = true ) {
			retOWN = cbResponsavel.getValue();
			if ( retOWN == null ) {
				retOWN = " Todos Responsáveis ";
			}
				
		} else {
			retOWN = " Todos Responsáveis ";
		}
			
		
		
		return "Competencia: " + retCPT + " Responsavel: " + retOWN;
	}
	

	

}
