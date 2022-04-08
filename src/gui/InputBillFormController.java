package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import gui.listeners.DataChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.entities.InputBill;
import model.services.BillTagsServices;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.InputBillServices;
import model.services.OwnerServices;

public class InputBillFormController implements Initializable {

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private InputBillServices ibServices;
	private ClientServices clServices;
	private ClientTypeServices ctServices;
	private OwnerServices owServices;
	private BillTagsServices btServices;
	private InputBill entity;
	
	
	
	public void setInputBill(InputBill entity) {
		this.entity = entity;
	}
	
	public void setServices(InputBillServices ibServices, ClientServices clServices, ClientTypeServices ctServices, OwnerServices owServices, BillTagsServices btServices) {
		this.ibServices = ibServices;
		this.clServices = clServices;
		this.ctServices = ctServices;
		this.owServices = owServices;
		this.btServices = btServices;
	}
	
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}


	public void loadAssociatedObjects() {
		// TODO Auto-generated method stub
		
	}




	public void updateFormData() {
		// TODO Auto-generated method stub
		
	}
	

}
