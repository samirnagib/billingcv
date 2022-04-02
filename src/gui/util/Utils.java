package gui.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Locale;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.dao.BillTagsDao;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.BillTags;
import model.entities.Client;

public class Utils {
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
		
	}

	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch (NumberFormatException e) {
			return null;
		}

	}
	public static boolean validaEmail(String emailAddress) {
		String regexPattern = "^(.+)@(\\S+)$";
		
		return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
	
	
	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}
	
	public static void configureFileChooserImportFiles(final FileChooser fileChooser, String title ) {      
	            fileChooser.setTitle(title);
	            fileChooser.setInitialDirectory(
	                new File(System.getProperty("user.home"))
	            );                 
	            fileChooser.getExtensionFilters().addAll(
	                
	                new FileChooser.ExtensionFilter("Arquivo CSV", "*.csv"),
	                new FileChooser.ExtensionFilter("Arquivo do MS Excel", "*.xlsx"),
	                new FileChooser.ExtensionFilter("Arquivo de Texto", "*.txt"),
	                new FileChooser.ExtensionFilter("Todos os Arquivos", "*.*")
	            );
	    }
	
	
	public static Client searchClientbyName(String searchClient) {
		ClientDao cDao = DaoFactory.createClientDao();
		Client client = new Client();
		
		client = cDao.findByName(searchClient);
		
		if (client == null) {
			return null;
		}
		
		return client;
		
	}
	
	public static BillTags searchBillsTags(String searchBillTags ) {
		BillTagsDao btDao = DaoFactory.createBillTagsDao();
		BillTags bt = new BillTags();
		
		bt = btDao.findByName(searchBillTags);
		
		if (bt == null) {
			return null;
		}
		return bt;
	}

	  
	
}
