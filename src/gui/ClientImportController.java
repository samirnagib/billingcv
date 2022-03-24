package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.services.ClientServices;

public class ClientImportController implements Initializable {


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
	
	private Desktop desktop = Desktop.getDesktop();
	
	@FXML
	private void btAbrirOnAction() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(stage);
		//openFile(file);
		txtPath.setText(file.getAbsolutePath() +" " + file.getName());
	}
	
	@FXML
	private void btImportarOnAction(ActionEvent event) {

	}
	
	@FXML
	private void btFecharOnAction() {
		System.out.println("btFecharOnAction");
	}
	
	private ClientServices service;
	
	public void setClientServices(ClientServices service) {
		this.service = service;
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
                
        }
    }
	
	// COnfigurando a caixa de dialogo
	
	private static void configureFileChooser(
	        final FileChooser fileChooser) {      
	            fileChooser.setTitle("Importar Arquivos");
	            fileChooser.setInitialDirectory(
	                new File(System.getProperty("user.home"))
	            );                 
	            fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("Todos arquivos", "*.*"),
	                new FileChooser.ExtensionFilter("CSV", "*.csv"),
	                new FileChooser.ExtensionFilter("TXT", "*.txt")
	            );
	    }
	

}
