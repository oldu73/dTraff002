package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DecimalController extends TypeController<Double> {

	private double value;

	@FXML
	private TextField filterValue;

	public DecimalController() {
		super(true);
	}

	@FXML
	public void initialize() {

		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = 0.0;
					valueEmpty.set(true);
				} else {
					try {
						value = Double.parseDouble(newValue);
						valueEmpty.set(false);
					} catch (NumberFormatException e) {
						filterValue.setText(oldValue);
					}
				}
			}
		});

	}

	public Double getValue() {
		return value;
	}

	public void setFilterValue(Double filterValue) {
		this.filterValue.setText(filterValue.toString());
	}

}
