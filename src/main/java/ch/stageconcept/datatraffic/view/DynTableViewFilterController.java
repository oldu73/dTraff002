package ch.stageconcept.datatraffic.view;

import static ch.stageconcept.datatraffic.util.LoggMode.*;

import java.io.IOException;
import ch.stageconcept.datatraffic.MainApp;
import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.util.LoggTableViewSelCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class DynTableViewFilterController {

	// Reference to the main application.
	private MainApp mainApp;

	private DynTableViewFilterEditDialogController filterEditDialogController;
	
	@SuppressWarnings("unused")
	private LoggTableViewSelCell loggTableViewSelCell;

	/**
	 * The data as an observable list of DynTableViewFilter.
	 */
	private ObservableList<DynTableViewFilter<?>> dynTableViewFilterData = FXCollections.observableArrayList();

	@FXML
	private TableView<DynTableViewFilter<?>> dynTableViewFilterTable;

	@FXML
	private TableColumn<DynTableViewFilter<?>, String> dbColumnNameColumn;

	@FXML
	private TableColumn<DynTableViewFilter<?>, String> dbColumnTypeColumn;

	@FXML
	private TableColumn<DynTableViewFilter<?>, ?> dbColumnValueColumn;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		editButton.setDisable(true);
		deleteButton.setDisable(true);

		// Initialize the dynTableViewFilter table with the columns.
		dbColumnNameColumn.setCellValueFactory(new PropertyValueFactory<>("dbColumnName"));
		dbColumnTypeColumn.setCellValueFactory(new PropertyValueFactory<>("dbColumnType"));
		dbColumnValueColumn.setCellValueFactory(new PropertyValueFactory<>("dbColumnValue"));

		// Add observable list data to the table
		dynTableViewFilterTable.setItems(dynTableViewFilterData);
		
		if (ENABLE_LOGG_TABLEVIEW_SELECTED_CELL) {
			loggTableViewSelCell = new LoggTableViewSelCell(dynTableViewFilterTable);
		}

		dynTableViewFilterTable.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						editButton.setDisable(false);
						deleteButton.setDisable(false);
					} else {
						editButton.setDisable(true);
						deleteButton.setDisable(true);
					}
				});
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new dynTableViewFilter.
	 */
	@FXML
	private void handleNewDynTableViewFilter() {

		DynTableViewFilter<?> tempFilter = new DynTableViewFilter<>();

		boolean okClicked = showDynTableViewFilterEditDialog(tempFilter, "New Filter");
		if (okClicked) {
			dynTableViewFilterData.add(filterEditDialogController.getdynTableViewFilter());
		}

	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditDynTableViewFilter() {

		DynTableViewFilter<?> tempFilter = dynTableViewFilterTable.getSelectionModel().getSelectedItem();

		if (tempFilter != null) {
			boolean okClicked = showDynTableViewFilterEditDialog(tempFilter, "Edit Filter");
			if (okClicked) {
				dynTableViewFilterData.set(dynTableViewFilterData.indexOf(tempFilter),
						filterEditDialogController.getdynTableViewFilter());

				// TODO: Selected row in blue?!
				dynTableViewFilterTable.getSelectionModel().select(filterEditDialogController.getdynTableViewFilter());
			}

		} else {
			// Nothing selected.
			alertNoSelection();
		}

	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteDynTableViewFilter() {
		int selectedIndex = dynTableViewFilterTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			dynTableViewFilterTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			alertNoSelection();
		}
	}

	private void alertNoSelection() {
		// Nothing selected.
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("No Selection");
		alert.setHeaderText("No Filter Selected");
		alert.setContentText("Please select a filter in the table.");
		alert.showAndWait();
	}

	private boolean showDynTableViewFilterEditDialog(DynTableViewFilter<?> tempFilter, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DynTableViewFilterEditDialog.fxml"));
			AnchorPane anchorPane = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(anchorPane);
			dialogStage.setScene(scene);

			filterEditDialogController = loader.getController();
			filterEditDialogController.setDialogStage(dialogStage);
			filterEditDialogController.setDynTableViewFilter(tempFilter);
			filterEditDialogController.populateColumnNameTypeComboBox(
					mainApp.getDynTableViewController().getDynTableView().dbGetColumnsNamesTypesPairList());

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return filterEditDialogController.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
