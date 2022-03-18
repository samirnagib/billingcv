package gui.util;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

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
	
}
