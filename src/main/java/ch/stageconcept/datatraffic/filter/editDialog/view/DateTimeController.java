package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.time.LocalDateTime;

public class DateTimeController extends TypeController<LocalDateTime> {

	@FXML
	private DatePicker datePicker;

	@FXML
	private Slider hourSlider;

	@FXML
	private Slider minuteSlider;

	@FXML
	private Slider secondSlider;

	@FXML
	private Label hourLabel;

	@FXML
	private Label minuteLabel;

	@FXML
	private Label secondLabel;

	@FXML
	public void initialize() {

		/*
		filterValue.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty()) {
					filterValue.setText("");
					value = null;
					valueEmpty.set(true);
				} else {
					try {
						value = LocalDateTime.parse(newValue);
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
                value = null;
                valueEmpty.set(true);
            } else {
                try {
                    value = LocalDateTime.parse(newValue);
                    valueEmpty.set(false);
                } catch (NumberFormatException e) {
                    filterValue.setText(oldValue);
                }
            }
        });

		hourLabel.textProperty().bind(Bindings.format("%02.0f", hourSlider.valueProperty()));
		minuteLabel.textProperty().bind(Bindings.format("%02.0f", minuteSlider.valueProperty()));
		secondLabel.textProperty().bind(Bindings.format("%02.0f", secondSlider.valueProperty()));

		/*
		datePicker.setOnAction(event -> {
			refreshDateTimeValue();
		});
		 */

		datePicker.setOnAction(event -> refreshDateTimeValue());

		/*
		hourSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				refreshDateTimeValue();
			}
		});
		 */

		hourSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            refreshDateTimeValue();
        });

		/*
		minuteSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				refreshDateTimeValue();
			}
		});
		 */

		minuteSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            refreshDateTimeValue();
        });

		/*
		secondSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				refreshDateTimeValue();
			}
		});
		 */

		secondSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            refreshDateTimeValue();
        });
	}

	private void refreshDateTimeValue() {

		String stringDateTimeValue;

		if (datePicker.valueProperty().getValue() != null) {
			stringDateTimeValue = datePicker.valueProperty().getValue().toString();
		} else {
			stringDateTimeValue = "0000-01-01";
		}

		stringDateTimeValue += "T";
		stringDateTimeValue += String.format("%02.0f", hourSlider.valueProperty().getValue());
		stringDateTimeValue += ":";
		stringDateTimeValue += String.format("%02.0f", minuteSlider.valueProperty().getValue());
		stringDateTimeValue += ":";
		stringDateTimeValue += String.format("%02.0f", secondSlider.valueProperty().getValue());

		filterValue.setText(stringDateTimeValue);
	}

	public LocalDateTime getValue() {
		return value;
	}

	public void setFilterValue(LocalDateTime filterValue) {
		this.filterValue.setText(filterValue.toString());
		
		datePicker.valueProperty().set(filterValue.toLocalDate());
		hourSlider.valueProperty().set(filterValue.getHour());
		minuteSlider.valueProperty().set(filterValue.getMinute());
		secondSlider.valueProperty().set(filterValue.getSecond());
	}

}
