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

import javax.swing.plaf.synth.SynthSeparatorUI;

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
								 ClientType tserver = new ClientType();
								 Owner tresp = new Owner();
								 
								 // Client server = new Client();
								 tserver = ctDAO.findByName(typeName);
								 tresp = oDao.findByName(owName);
								 
								 Client tClient = new Client (null, clientName, clientHostname, tserver.getIdType(), tresp.getIdOwner(), tserver, tresp);
//								 
//								 System.out.println(tserver);
//								 System.out.println(tresp);
//								 System.out.println(tClient);
//								 
								 //client.add(new Client(null, clientName, clientHostname, null, null, null, null));
								 ct.add(new ClientType(null, typeName));
								 resp.add(new Owner(null, owName, owEmail1, owEmail2, owProjectArea, owAR));
								 client.add(tClient);
							 }						 
							 
							 lAtual++;
							 //System.out.println("Processando a linha " + lAtual );
							 lineCSV = br.readLine();
						
					 }
					 
					 /*  Sequencia de importação no banco :
					  * 1º pesquisa no tabela de Resposáveis se o responsável existe, caso positivo pula, em negativo cadastra;
					  * 2º pesquisa na tabela de Tipo de Servidor, se o tipo existe, caso positivo pula, em negativo cadastra;
					  * 3º pesquisa na tablea de Clientes, se o servidor existe, caso positivo pula, em negativo cadastra
					  */
/*					
					 
					 // tabela responsavel
					 
					 for(Owner item : resp) {
						 
						 String search = item.getOwName();
						 //System.out.println(search);
						 //System.out.println(oDao.searchByName(search));
						 if ( oDao.searchByName(search) ) {
							 lblMessage.setText("O Responsável " + search + " existe na base de dados" );
							 System.out.println("O Responsável " + search + " existe na base de dados" );
						 } else {
							 lblMessage.setText("O Responsável " + search + " não existe na base de dados, cadastrando." );
							 System.out.println("O Responsável " + search + " não existe na base de dados, cadastrando." );
							 oDao.insert(item);
						 }
					 }
					
					// tabela Tipo de Servidor
					 					 
					 for(ClientType item2 : ct) {
						 
						 String search2 = item2.getTypeName();
						 if (ctDAO.searchByName(search2)) {
							 lblMessage.setText("O Tipo de servidor " + search + " existe na base de dados" );
							 System.out.println("O Tipo de servidor " + search + " existe na base de dados" );
						 } else {
							 lblMessage.setText("O Tipo de servidor " + search + " não existe na base de dados, cadastrando." );
							 System.out.println("O Tipo de servidor " + search + " não existe na base de dados, cadastrando." );
							 ctDAO.insert(item2);
						 }
						 
					 }
*/					 
					// tabela Clientes
					
					 for(Client item : client) {
						
						String search = item.getClientName();
						
						if (clientDAO.searchByName(search)) {
							 lblMessage.setText("O servidor " + search + " existe na base de dados" );
							 System.out.println("O servidor " + search + " existe na base de dados" );
						}else {
							lblMessage.setText("O servidor " + search + " não existe na base de dados, cadastrando." );
							System.out.println("O servidor " + search + " não existe na base de dados, cadastrando." );
							System.out.println(item);
							clientDAO.insert(item);
						}
						
					 }
					
				
					 
				}catch (IOException e) {
					System.out.println("Error reading file: " + e.getMessage());
				}finally {
				lblMessage.setText("Importação concluida." );
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
