package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.fxml.FXML;

public class IntController extends TypeController<Integer> {

    @FXML
	public void initialize() {

		// http://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx

		/*
		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = 0;
					valueEmpty.set(true);
				} else {
					try {
						value = Integer.parseInt(newValue);
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
                value = 0;
                valueEmpty.set(true);
            } else {
                try {
                    value = Integer.parseInt(newValue);
                    valueEmpty.set(false);
                } catch (NumberFormatException e) {
                    filterValue.setText(oldValue);
                }
            }
        });

	}

	public Integer getValue() {
		return value;
	}

	public void setFilterValue(Integer filterValue) {
		this.filterValue.setText(filterValue.toString());
	}

}
