package ch.stageconcept.datatraffic.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DTVFEditDialogVarCharController {

	private String value;

	@FXML
	private TextField filterValue;

	@FXML
	public void initialize() {

		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = "";
				} else {
					try {
						value = newValue;
					} catch (NumberFormatException e) {
						filterValue.setText(oldValue);
					}
				}
			}
		});

	}

	public String getValue() {
		return value;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue.setText(filterValue);
	}

}
