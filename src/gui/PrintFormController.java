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
import reports.report;

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
	
	private Integer ReportChoice;
	
	
	
	
	@FXML
	private void btPrintOnAction() {
		System.out.println("btPrint");
		
		InputBill fatura = new InputBill();
		Owner resp = new Owner();
		
		
		switch (ReportChoice) {
			case 0:   // todos
				System.out.println("Todos as competencias e todos os responsáveis");
				report.callRelatorio("RateioMensal1", "Relatorio de Rateio - TODOS");
				break;
			case 1:   // Todas as Competencias e UM Responsável
				System.out.println("Todas as Competencias e UM Responsável");
				resp = owServices.findOwnerByName(cbResponsavel.getValue());
				report.callRelatorioChargeBack("RateioMensal2", "Relatorio de Rateio - por responsável: " + cbResponsavel.getValue(), fatura, resp);
				break;
			case 2:   // Uma competencia e Todos os responsáveis
				System.out.println("Uma competencia e Todos os responsáveis");
				fatura = ibServices.ibfindCPT(cbCompetencia.getValue());
				report.callRelatorioChargeBack("RateioMensal3", "Relatorio de Rateio - por competencia: " + cbCompetencia.getValue(), fatura, resp);
				
				break;
			case 3:   // Uma competencia e um reponsável
				System.out.println("Uma competencia e um reponsável");
				resp = owServices.findOwnerByName(cbResponsavel.getValue());
				fatura = ibServices.ibfindCPT(cbCompetencia.getValue());
				report.callRelatorioChargeBack("RateioMensal4", "Relatorio de Rateio - por resposável e competencia: " +  cbResponsavel.getValue().toUpperCase() + " " + cbCompetencia.getValue(), fatura, resp);
				break;
			default:   // padrão
				System.out.println("erro");
				
				break;
		
		}
		
		
	}
	
	@FXML
	private void brFecharOnAction() {
		System.out.println("btFechar");
	}
	
	@FXML
	private void btClearFiltersOnAction() {
		//System.out.println("btClearFilters");
		lbFiltroAplicado.setText( loadFilterFatura(false,false) );
		cbCompetencia.setValue("TODAS");
		cbResponsavel.setValue("TODOS");
		bCPT = false;
		bOWN = false;
		ReportChoice = 0;
	}
	
	@FXML
	private void cbCompetenciaOnAction() {
		System.out.println("Report Choice: " + ReportChoice);
				
		if ( bCPT = true ) {
			if ( cbCompetencia.getValue() == "") {
				lbFiltroAplicado.setText( loadFilterFatura(false,false) );
				
				
			}
			
			lbFiltroAplicado.setText( loadFilterFatura(true,false) );
			
		} else {
			lbFiltroAplicado.setText( loadFilterFatura(true,false) );
			bCPT = true;
		}
		
		ReportChoice = checkChoice();
		
	}
	
	@FXML
	private void cbResponsavelOnAction() {
		System.out.println("Report Choice: " + ReportChoice);
		if ( bOWN = true ) {
			if ( cbResponsavel.getValue() == "") {
				lbFiltroAplicado.setText( loadFilterFatura(false,false) );
				
			}
			
			lbFiltroAplicado.setText( loadFilterFatura(true,false) );
			
		} else {
			lbFiltroAplicado.setText( loadFilterFatura(true,false) );
			bOWN = true;
		}
		ReportChoice = checkChoice();
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

	private Integer checkChoice() {
		Integer Choice;
		if ( (( cbCompetencia.getValue() == null ) ||  cbCompetencia.getValue().equalsIgnoreCase("TODAS"))) {
			//todas as cptd
			System.out.println("-= Fora do if =-");
			System.out.println("cbComptencia..: " + cbCompetencia.getValue() );
			System.out.println("cbResponsavel.: " + cbResponsavel.getValue() );
			if (( cbResponsavel.getValue() == null ) || ( cbResponsavel.getValue().equalsIgnoreCase("TODOS") )) {
				System.out.println("-=  if choice 0 =-");
				System.out.println("cbComptencia..: " + cbCompetencia.getValue() );
				System.out.println("cbResponsavel.: " + cbResponsavel.getValue() );
				Choice = 0;
			} else {
				// um resp e todas cpts
				System.out.println("-=  if choice 1 =-");
				System.out.println("cbComptencia..: " + cbCompetencia.getValue() );
				System.out.println("cbResponsavel.: " + cbResponsavel.getValue() );
				Choice = 1;
			}
		}
		else {
			// uma cpt 
			if (( cbResponsavel.getValue() == null ) || ( cbResponsavel.getValue().equalsIgnoreCase("TODOS") )) {
				System.out.println("-=  if choice 2 =-");
				System.out.println("cbComptencia..: " + cbCompetencia.getValue() );
				System.out.println("cbResponsavel.: " + cbResponsavel.getValue() );
				Choice = 2;
			} else {
				// um resp e uma cpts
				System.out.println("-=  if choice 3 =-");
				System.out.println("cbComptencia..: " + cbCompetencia.getValue() );
				System.out.println("cbResponsavel.: " + cbResponsavel.getValue() );
				Choice = 3;
			}
		}
		return Choice;
	}
	
	public void setReport(Integer status) {
		this.ReportChoice = status;
	}
	

}
