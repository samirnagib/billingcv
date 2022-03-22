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
import model.entities.Owner;
import model.exceptions.ValidationException;
import model.services.OwnerServices;

public class OwnerFormController implements Initializable {

	private Owner entity;
	private OwnerServices service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtidOwner;
	
	@FXML
	private TextField txtowName;
	
	@FXML
	private TextField txtowEmail1;
	
	@FXML
	private TextField txtowEmail2;
	
	@FXML
	private TextField txtowProjectArea;
	
	@FXML
	private TextField txtowAR;

	@FXML
	private Label lblErrorowName;
	
	@FXML
	private Label lblErrorowEmail1;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button brCancelar;
	
	public void setOwner(Owner entity) {
		this.entity = entity;
	}
	
	public void setOwnerService(OwnerServices service) {
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

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Owner getFormData() {
		Owner obj = new Owner();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setIdOwner(Utils.tryParseToInt(txtidOwner.getText()));
		obj.setOwName(txtowName.getText());
		if (txtowName.getText() == null || txtowName.getText().trim().equals("") ) {
			exception.addError("name", "O Campo nome não pode estar vazio");
		}
		obj.setOwEmail1(txtowEmail1.getText());
		if (txtowEmail1.getText() == null || txtowEmail1.getText().trim().equals("") ) {
			exception.addError("email", "O Email Principal não pode estar vazio");
		}

		obj.setOwEmail2(txtowEmail2.getText());
		obj.setOwProjectArea(txtowProjectArea.getText());
		obj.setOwAR(txtowAR.getText());
		
		if (exception.getErrors().size() > 0 ) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	private void btCancelarOnAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtidOwner);
		Constraints.setTextFieldMaxLength(txtowName, 200);
		Constraints.setTextFieldMaxLength(txtowEmail1, 200);
		Constraints.setTextFieldMaxLength(txtowEmail2, 200);
		Constraints.setTextFieldMaxLength(txtowProjectArea, 200);
		Constraints.setTextFieldMaxLength(txtowAR, 200);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtidOwner.setText(String.valueOf(entity.getIdOwner()));
		txtowName.setText(entity.getOwName());
		txtowEmail1.setText(entity.getOwEmail1());
		txtowEmail2.setText(entity.getOwEmail2());
		txtowProjectArea.setText(entity.getOwProjectArea());
		txtowAR.setText(entity.getOwAR());
	}
	
	private void setErrorMessages(Map<String, String> error ) {
		Set<String> fields = error.keySet();
		
		if (fields.contains("name")) {
			lblErrorowName.setText(error.get("name"));
		}

		if (fields.contains("email")) {
			lblErrorowEmail1.setText(error.get("email"));
		}

	}

}
