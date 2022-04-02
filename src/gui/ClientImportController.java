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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.dao.ClientDao;
import model.dao.ClientTypeDao;
import model.dao.DaoFactory;
import model.dao.OwnerDao;
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
	private TextField txtPath;
			
	@FXML
	private Label lblMessage;
	
	private File file;
	
	//matrix do excel
	//private String excelFile[][] = new String[][];
	
	
	
	private String campo[] = new String[8];
	 // variaveis para inse��o do banco
	
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
	
	ClientDao clientDAO = DaoFactory.createClientDao();
	ClientTypeDao ctDAO = DaoFactory.createClientTypeDao();
	OwnerDao oDao = DaoFactory.createOwnerDao();
	
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
	
	@SuppressWarnings("null")
	@FXML
	private void btImportarOnAction(ActionEvent event) throws FileNotFoundException, IOException {
			if (txtPath.getText() == null || txtPath.getText().trim().equals("")) {
				Alerts.showAlert("Aviso", null, "Selecione o arquivo a ser importado", AlertType.ERROR);
			} else {
				
				String path = txtPath.getText();
				
				
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {
					 String lineCSV = br.readLine();
					
					 while (lineCSV != null ) {
						 	
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
//								 System.out.println("Cliente....: " + clientName);
//								 System.out.println("Hostname...: " + clientHostname);
//								 System.out.println("Tipo.......: " + typeName);
//								 System.out.println("Responsavel: " + owName);
//								 System.out.println("Email 1....: " + owEmail1);
//								 System.out.println("Email 2....: " + owEmail2);
//								 System.out.println("Aera ......: " + owProjectArea);
//								 System.out.println("AR.........: " + owAR);
								 
								 ClientType tserver = new ClientType();
								 Owner tresp = new Owner();
								 Client tClient = new Client();
								 tserver = ctDAO.findByName(typeName);
								 if (tserver == null ) {
									 ClientType tserver2 = new ClientType(null, typeName);
									 ctDAO.insert(tserver2);
								 }
								 tresp = oDao.findByName(owName);
								 if (tresp == null) {
									 Owner tresp2 = new Owner(null, owName, owEmail1, owEmail2, owProjectArea, owAR);
									 oDao.insert(tresp2);
								 }
								 tserver = ctDAO.findByName(typeName);
								 tresp = oDao.findByName(owName);
//								 System.out.println(tserver);
//								 System.out.println(tresp);
//								 System.out.println(tClient); 
								 tClient = new Client(null, clientName, clientHostname, tserver.getIdType(), tresp.getIdOwner(), tserver, tresp);
								// System.out.println(tClient);
								 ct.add(new ClientType(null, typeName));
								 resp.add(new Owner(null, owName, owEmail1, owEmail2, owProjectArea, owAR));
								 client.add(tClient);
							 }						 
							 lineCSV = br.readLine();
					 }
					 
					 /*  Sequencia de importa��o no banco :
					  * 1� pesquisa no tabela de Respos�veis se o respons�vel existe, caso positivo pula, em negativo cadastra;
					  * 2� pesquisa na tabela de Tipo de Servidor, se o tipo existe, caso positivo pula, em negativo cadastra;
					  * 3� pesquisa na tablea de Clientes, se o servidor existe, caso positivo pula, em negativo cadastra
					  */
					
					 
					 // tabela responsavel
					 
					 for(Owner item : resp) {
						 
						 String search = item.getOwName();
						 //System.out.println(search);
						 //System.out.println(oDao.searchByName(search));
						 if ( oDao.searchByName(search) ) {
							 lblMessage.setText("O Respons�vel " + search + " existe na base de dados" );
							 //System.out.println("O Respons�vel " + search + " existe na base de dados" );
						 } else {
							 lblMessage.setText("O Respons�vel " + search + " n�o existe na base de dados, cadastrando." );
							 //System.out.println("O Respons�vel " + search + " n�o existe na base de dados, cadastrando." );
							 //oDao.insert(item);
						 }
					 }
					
					// tabela Tipo de Servidor
					 					 
					 for(ClientType item2 : ct) {
						 
						 String search = item2.getTypeName();
						 if (ctDAO.searchByName(search)) {
							 lblMessage.setText("O Tipo de servidor " + search + " existe na base de dados" );
							 //System.out.println("O Tipo de servidor " + search + " existe na base de dados" );
						 } else {
							 lblMessage.setText("O Tipo de servidor " + search + " n�o existe na base de dados, cadastrando." );
							 //System.out.println("O Tipo de servidor " + search + " n�o existe na base de dados, cadastrando." );
							 //ctDAO.insert(item2);
						 }
						 
					 }
					 //*/
					// tabela Clientes
					
					 for(Client item : client) {
						
						String search = item.getClientName();
						
						if (clientDAO.searchByName(search)) {
							Client temp = new Client(clientDAO.findByName(search).getIdClient(),item.getClientName(),item.getClientHostname(),item.getIdType(),item.getIdOwner(),item.getClientType(),item.getOwner());
							 lblMessage.setText("O servidor " + search + " existe na base de dados" );
//							 System.out.println("O servidor " + search + " existe na base de dados" );
//							 System.out.println(temp);
							 clientDAO.update(temp);
						}else {
							lblMessage.setText("O servidor " + search + " n�o existe na base de dados, cadastrando." );
//							System.out.println("O servidor " + search + " n�o existe na base de dados, cadastrando." );
//							System.out.println(item);
							clientDAO.insert(item);
						}
						
					 }
					 
				}catch (IOException e) {
					System.out.println("Error reading file: " + e.getMessage());
				}finally {
				lblMessage.setText("Importa��o concluida." );
			}
		}

	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	
	
	// COnfigurando a caixa de dialogo
	
		

}