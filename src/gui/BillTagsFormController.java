package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.BillTags;
import model.exceptions.ValidationException;
import model.services.BillTagsServices;

public class BillTagsFormController implements Initializable {

	private BillTags entity;
	private BillTagsServices service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	@FXML
	private TextField txtidbillTag;
	
	@FXML
	private TextField txtbilltagName;
	
	@FXML
	private TextField txtbillPriceTB;
	
	@FXML
	private Label lblError;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setBillTags(BillTags entity) {
		this.entity = entity;
	}
	
	public void setBillTagsService(BillTagsServices service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	private void btSalvarOnAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null.");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null.");
		}
		try {
			entity = getFormData();
			service.saverOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	private void btCancelOnAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private BillTags getFormData() {
		BillTags obj = new BillTags();
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setIdbillTag((Utils.tryParseToInt(txtidbillTag.getText())));
		
		obj.setBilltagName(txtbilltagName.getText());
		if (txtbilltagName.getText() == null || txtbilltagName.getText().trim().equals("") ) {
			exception.addError("name", "O Campo nome não pode estar vazio");
		}
		
		obj.setBillPriceTB(Utils.tryParseToDouble(txtbillPriceTB.getText()));
		
		if (exception.getErrors().size() > 0 ) {
			throw exception;
		}
		
		return obj;
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();		
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtidbillTag);
		Constraints.setTextFieldMaxLength(txtbilltagName, 200);
		Constraints.setTextFieldDouble(txtbillPriceTB);
		
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtidbillTag.setText(String.valueOf(entity.getIdbillTag()));
		txtbilltagName.setText(entity.getBilltagName());
		txtbillPriceTB.setText(String.valueOf(entity.getBillPriceTB()));
		
	}
	
	private void setErrorMessages(Map<String, String> error ) {
		Set<String> fields = error.keySet();
		
		if (fields.contains("name")) {
			lblError.setText(error.get("name"));
		}

	}

	
}
