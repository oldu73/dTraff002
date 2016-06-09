package ch.stageconcept.datatraffic.filter.editDialog.view;

import java.io.IOException;
import java.util.List;
import ch.stageconcept.datatraffic.MainApp;
import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.util.Pair;
import ch.stageconcept.datatraffic.util.type.MasterSingleton;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
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

public class ContainerController {

	@FXML
	private AnchorPane bodyAnchorPane;

	private boolean okClicked = false;
	private DynTableFilter<?> dynTableFilter;

	@FXML
	private ComboBox<DynTableFilter<?>> columnNameTypeComboBox;

	@FXML
	private Button okButton;

	private ObservableList<DynTableFilter<?>> columnNameTypeComboBoxData = FXCollections.observableArrayList();

	private Stage dialogStage;

	public void setDynTableFilter(DynTableFilter<?> tempFilter) {
		this.dynTableFilter = tempFilter;
	}

	@FXML
	public void initialize() {

		okButton.setDisable(true);

		// http://code.makery.ch/blog/javafx-8-event-handling-examples/

		// Define rendering of the list of values in ComboBox drop down.
		columnNameTypeComboBox.setCellFactory((comboBox) -> {
			return new ListCell<DynTableFilter<?>>() {
				@Override
				protected void updateItem(DynTableFilter<?> item, boolean empty) {
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
		columnNameTypeComboBox.setConverter(new StringConverter<DynTableFilter<?>>() {
			@Override
			public String toString(DynTableFilter<?> dynTableFilterValue) {
				if (dynTableFilterValue == null) {
					return null;
				} else {
					if (dynTableFilterValue.getDbColumnName() == null
							&& dynTableFilterValue.getDbColumnType() == null) {
						return "Please select...";
					}
					return dynTableFilterValue.getDbColumnName() + " - "
							+ dynTableFilterValue.getDbColumnType();
				}
			}

			@Override
			public DynTableFilter<?> fromString(String dynTableViewFilterSelectString) {
				return null; // No conversion fromString needed.
			}
		});

		// Handle ComboBox event.
		columnNameTypeComboBox.setOnAction((event) -> {

			DynTableFilter<?> selectedDynTableFilterValue = columnNameTypeComboBox.getSelectionModel()
					.getSelectedItem();

			/*
			 * System.out.println("ComboBox Action (selected: " +
			 * selectedDynTableViewFilter.toString() + ")");
			 */

			initEditDialogBodyLayout(selectedDynTableFilterValue);
		});
	}

	private void initEditDialogBodyLayout(DynTableFilter<?> selectedDynTableFilter) {
		try {

			String resourceString;

			resourceString = MasterSingleton.INSTANCE.getDataHashMap().get(selectedDynTableFilter.getDbColumnType())
					.getFilterEditDialogBodyFxmlFileName();

			// Load layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(resourceString));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			MasterSingleton.INSTANCE.getDataHashMap().get(selectedDynTableFilter.getDbColumnType())
					.setFilterEditDialogDataController(loader.getController());

			TypeController<?> bodyController = loader.getController();
			// OK button disabled until value field isn't empty
			okButton.disableProperty().bind(bodyController.isValueEmpty());

			try {
				MasterSingleton.INSTANCE.getDataHashMap().get(selectedDynTableFilter.getDbColumnType())
						.setControllerFilterValue(selectedDynTableFilter);
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
		// dynTableFilter.setDynTableViewFilterValue(columnNameTypeComboBox.getValue());

		dynTableFilter = MasterSingleton.INSTANCE.getDataHashMap()
				.get(columnNameTypeComboBox.getValue().getDbColumnType())
				.createDynTableViewFilter(columnNameTypeComboBox);

		okClicked = true;
		dialogStage.close();
	}

	public DynTableFilter<?> getdynTableViewFilter() {
		return dynTableFilter;
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
		columnNameTypeComboBox.setValue(this.dynTableFilter);

		if (this.dynTableFilter.getDbColumnName() != null && this.dynTableFilter.getDbColumnType() != null) {
			initEditDialogBodyLayout(this.dynTableFilter);
		}

	}

}
