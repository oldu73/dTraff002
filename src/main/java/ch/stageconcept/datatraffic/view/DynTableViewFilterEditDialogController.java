package ch.stageconcept.datatraffic.view;

import java.io.IOException;
import java.util.List;
import ch.stageconcept.datatraffic.MainApp;
import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.util.Pair;
import ch.stageconcept.datatraffic.util.type.MasterSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class DynTableViewFilterEditDialogController {

	@FXML
	private AnchorPane bodyAnchorPane;

	private boolean okClicked = false;
	private DynTableViewFilter<?> dynTableViewFilter;

	@FXML
	private ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox;

	@FXML
	private Button okButton;

	private ObservableList<DynTableViewFilter<?>> columnNameTypeComboBoxData = FXCollections.observableArrayList();

	private Stage dialogStage;

	public void setDynTableViewFilter(DynTableViewFilter<?> tempFilter) {
		this.dynTableViewFilter = tempFilter;
	}

	@FXML
	public void initialize() {

		okButton.setDisable(true);

		// http://code.makery.ch/blog/javafx-8-event-handling-examples/

		// Define rendering of the list of values in ComboBox drop down.
		columnNameTypeComboBox.setCellFactory((comboBox) -> {
			return new ListCell<DynTableViewFilter<?>>() {
				@Override
				protected void updateItem(DynTableViewFilter<?> item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getDbColumnName() + " - " + item.getDbColumnType());
					}
				}
			};
		});

		// Define rendering of selected value shown in ComboBox.
		columnNameTypeComboBox.setConverter(new StringConverter<DynTableViewFilter<?>>() {
			@Override
			public String toString(DynTableViewFilter<?> dynTableViewFilterValue) {
				if (dynTableViewFilterValue == null) {
					return null;
				} else {
					if (dynTableViewFilterValue.getDbColumnName() == null
							&& dynTableViewFilterValue.getDbColumnType() == null) {
						return "Please select...";
					}
					return dynTableViewFilterValue.getDbColumnName() + " - "
							+ dynTableViewFilterValue.getDbColumnType();
				}
			}

			@Override
			public DynTableViewFilter<?> fromString(String dynTableViewFilterSelectString) {
				return null; // No conversion fromString needed.
			}
		});

		// Handle ComboBox event.
		columnNameTypeComboBox.setOnAction((event) -> {

			DynTableViewFilter<?> selectedDynTableViewFilterValue = columnNameTypeComboBox.getSelectionModel()
					.getSelectedItem();

			/*
			 * System.out.println("ComboBox Action (selected: " +
			 * selectedDynTableViewFilter.toString() + ")");
			 */

			initEditDialogBodyLayout(selectedDynTableViewFilterValue);

			okButton.setDisable(false);
		});
	}

	private void initEditDialogBodyLayout(DynTableViewFilter<?> selectedDynTableViewFilter) {
		try {

			String resourceString;

			resourceString = MasterSingleton.INSTANCE.getDataHashMap().get(selectedDynTableViewFilter.getDbColumnType())
					.getFilterEditDialogBodyFxmlFileName();

			// Load layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(resourceString));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			MasterSingleton.INSTANCE.getDataHashMap().get(selectedDynTableViewFilter.getDbColumnType())
					.setFilterEditDialogDataController(loader.getController());

			try {
				MasterSingleton.INSTANCE.getDataHashMap().get(selectedDynTableViewFilter.getDbColumnType())
						.setControllerFilterValue(selectedDynTableViewFilter);

				okButton.setDisable(false);

			} catch (NullPointerException npe) {
				//
			}

			/*
			 * //
			 * http://stackoverflow.com/questions/15223812/javafx-panel-inside-
			 * panel-auto-resizing AnchorPane.setTopAnchor(stackPane, 0.0);
			 * AnchorPane.setRightAnchor(stackPane, 0.0);
			 * AnchorPane.setLeftAnchor(stackPane, 0.0);
			 * AnchorPane.setBottomAnchor(stackPane, 0.0);
			 */

			bodyAnchorPane.getChildren().clear();
			bodyAnchorPane.getChildren().add(anchorPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		// dynTableViewFilter.setDynTableViewFilterValue(columnNameTypeComboBox.getValue());

		dynTableViewFilter = MasterSingleton.INSTANCE.getDataHashMap()
				.get(columnNameTypeComboBox.getValue().getDbColumnType())
				.createDynTableViewFilter(columnNameTypeComboBox);

		okClicked = true;
		dialogStage.close();
	}

	public DynTableViewFilter<?> getdynTableViewFilter() {
		return dynTableViewFilter;
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void populateColumnNameTypeComboBox(List<Pair<String, String>> columnsNamesTypes) {
		columnNameTypeComboBoxData.clear();

		for (Pair<String, String> temp : columnsNamesTypes) {
			MasterSingleton.INSTANCE.getDataHashMap().get(temp.getSecond())
					.addFilterComboBoxData(columnNameTypeComboBoxData, temp);
		}

		columnNameTypeComboBox.getItems().clear();
		// Init ComboBox items.
		columnNameTypeComboBox.setItems(columnNameTypeComboBoxData);
		columnNameTypeComboBox.setValue(this.dynTableViewFilter);

		if (this.dynTableViewFilter.getDbColumnName() != null && this.dynTableViewFilter.getDbColumnType() != null) {
			initEditDialogBodyLayout(this.dynTableViewFilter);
		}

	}

}
