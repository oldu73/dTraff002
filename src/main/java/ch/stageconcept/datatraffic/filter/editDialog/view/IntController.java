package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IntController {

	private int value;

	@FXML
	private TextField filterValue;

	@FXML
	public void initialize() {

		// http://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = 0;
				} else {
					try {
						value = Integer.parseInt(newValue);
					} catch (NumberFormatException e) {
						filterValue.setText(oldValue);
					}
				}
			}
		});

	}

	public int getValue() {
		return value;
	}

	public void setFilterValue(Integer filterValue) {
		this.filterValue.setText(filterValue.toString());
	}

}
