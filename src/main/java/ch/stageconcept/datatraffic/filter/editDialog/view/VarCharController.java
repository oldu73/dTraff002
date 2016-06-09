package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class VarCharController extends TypeController<String> {

	@FXML
	public void initialize() {

		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = "";
					valueEmpty.set(true);
				} else {
					try {
						value = newValue;
						valueEmpty.set(false);
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
