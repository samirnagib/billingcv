package gui.util;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	public static void showConfirmation(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
		
		Optional<ButtonType> option = alert.showAndWait();
		
		if (option.get()==null) {
			alert.setTitle("Clique em OK ou cancelar");
		} else if (option.get()== ButtonType.OK) {
			Platform.exit();
		} else if (option.get()== ButtonType.CANCEL) {
			System.out.println("Cancelado");
		}
		
		
	}
}