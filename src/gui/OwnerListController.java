package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.Owner;

public class OwnerListController implements Initializable {

	@FXML
	private TableView<Owner> tableViewOwner;
	
	@FXML
	private TableColumn<Owner, Integer> tableColumnidOwner;
	
	@FXML
	private TableColumn<Owner, String> tableColumowName;
	
	@FXML
	private TableColumn<Owner, String> tableColumowEmail1;
	
	@FXML
	private TableColumn<Owner, String> tableColumowEmail2;
	
	@FXML
	private TableColumn<Owner, String> tableColumowProjectArea;
	
	@FXML
	private TableColumn<Owner, String> tableColumowAR;
	
	@FXML
	private TableColumn<Owner, Owner> tableColumEDIT;
	
	@FXML
	private TableColumn<Owner, Owner> tableColumREMOVE;
	
	
	
	@FXML
	private Button btNovo;
	
	@FXML
	private void onBtNovoAction() {
		System.out.println("onBtNovoAction");
		
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
