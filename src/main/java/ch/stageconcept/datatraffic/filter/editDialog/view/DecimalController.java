package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.fxml.FXML;

public class DecimalController extends TypeController<Double> {

	@FXML
	public void initialize() {

		/*
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
		 */

		filterValue.textProperty().addListener((observable, oldValue, newValue) -> {
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
        });

	}

	public Double getValue() {
		return value;
	}

	public void setFilterValue(Double filterValue) {
		this.filterValue.setText(filterValue.toString());
	}

}
