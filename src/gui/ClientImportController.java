package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entities.Client;
import model.entities.ClientType;
import model.entities.Owner;
import model.services.ClientServices;
import model.services.ClientTypeServices;
import model.services.OwnerServices;

public class ClientImportController implements Initializable  {


	@FXML
	private Button btAbrir;
	
	@FXML
	private Button btImportar;
	
	@FXML
	private Button btFechar;
	
	@FXML
	private TextField txtPath;
	
	@FXML
	private ProgressBar pbProgresso;
	
	@FXML
	private Label lblMessage;
	
	private File file;
	
	
	private String campo[] = new String[8];
	 // variaveis para inseção do banco
	
	private String clientName;
	private String clientHostname;
	private String typeName;
	private String owName;
	private String owEmail1;
	private String owEmail2;
	private String owProjectArea;
	private String owAR;
		
	
	List<Client> client = new ArrayList<>();
	List<ClientType> ct = new ArrayList<>();
	List<Owner> resp = new ArrayList<>();

	private ClientServices clientService;
	private ClientTypeServices typeServices;
	private OwnerServices respServices;
	
	public void setService(ClientServices clientService, ClientTypeServices typeServices, OwnerServices respServices) {
		this.clientService = clientService;
		this.typeServices = typeServices;
		this.respServices = respServices;
		
	}
	
	
	
	
	@FXML
	private void btAbrirOnAction() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		Utils.configureFileChooserImportFiles(fileChooser, "Importar arquivo de CLientes");
		file = fileChooser.showOpenDialog(stage);
		txtPath.setText(file.getAbsolutePath());
	}
	
	@FXML
	private void btImportarOnAction(ActionEvent event) throws FileNotFoundException, IOException {
			if (txtPath.getText() == null || txtPath.getText().trim().equals("")) {
				Alerts.showAlert("Aviso", null, "Selecione o arquivo a ser importado", AlertType.ERROR);
			} else {
				long lines = 0;
				long lAtual = 0;
				String path = txtPath.getText();
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {
					String lineCSV = br.readLine();
					
					 while (lineCSV != null ) {
							 lblMessage.setText("Processando a linha " + lAtual + " de " +lines );
							 campo = lineCSV.split(";");
							 
							 if ( !campo[0].equals("Client") ) {
								 clientName = campo[0];
								 clientHostname = campo[1];
								 typeName = campo[2];
								 owName = campo[3];
								 owEmail1 = campo[4];
								 owEmail2  = campo[5];
								 owProjectArea = campo[6];
								 owAR  = campo[7];
								 
								 client.add(new Client(null, clientName, clientHostname, null, null, null, null));
								 ct.add(new ClientType(null, typeName));
								 resp.add(new Owner(null, owName, owEmail1, owEmail2, owProjectArea, owAR));
							 }						 
							 
							 lAtual++;
							 System.out.println("Processando a linha " + lAtual );
							 lineCSV = br.readLine();
						
					 }
					
					 for(ClientType item2 : ct) {
						 System.out.println("Typo: " + item2.getTypeName());
					 }
					 
					 
					 System.out.println("Resultado");
					 for(Client item : client) {
						 System.out.println("Cliente: " + item.getClientName() + " | Hostname: " + item.getClientHostname());
						 //clientService.
					 }
					
					 for(ClientType item2 : ct) {
						 System.out.println("Typo: " + item2.getTypeName());
					 }
					 for(Owner item : resp) {
						 System.out.println("Responsavel: " + item.getOwName() + " | Email1 " + item.getOwEmail1());
					 }
					
					 
				}catch (IOException e) {
					System.out.println("Error reading file: " + e.getMessage());
				}
			} 
				
				
				
			
		
		
		
		

	}
	
	@FXML
	private void btFecharOnAction() {
		System.out.println("btFecharOnAction");
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	
	
	// COnfigurando a caixa de dialogo
	
		

}
