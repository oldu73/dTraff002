package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DecimalController {

	private double value;

	@FXML
	private TextField filterValue;

	@FXML
	public void initialize() {

		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = 0.0;
				} else {
					try {
						value = Double.parseDouble(newValue);
					} catch (NumberFormatException e) {
						filterValue.setText(oldValue);
					}
				}
			}
		});

	}

	public double getValue() {
		return value;
	}

	public void setFilterValue(Double filterValue) {
		this.filterValue.setText(filterValue.toString());
	}

}
