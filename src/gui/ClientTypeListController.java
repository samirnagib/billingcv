package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.ClientType;

public class ClientTypeListController implements Initializable {

	@FXML
	private TableView<ClientType> tbViewClientType;
	
	@FXML
	private TableColumn<ClientType, Integer> tbColumnidType;
	
	@FXML
	private TableColumn<ClientType, String> tbColumntypeName;
	
	@FXML 
	private TableColumn<ClientType, ClientType> tbColumnEDIT;
	
	@FXML 
	private TableColumn<ClientType, ClientType> tbColumnREMOVE;
	
	@FXML
	private Button btNovo;
	
	
	
	@FXML
	private void btNovoOnAction() {
		System.out.println("btNovoOnAction");
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
	}

}
