package ch.stageconcept.datatraffic.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ch.stageconcept.datatraffic.view.ConsoleController;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

public final class LoggTableViewSelCell {

	private TableView<?> tableView;
	ObservableList<?> selectedCells;
	private final ListChangeListener<Object> listChangeListener;
	final ContextMenu contextMenu = new ContextMenu();
	private Logger logger = Logger.getLogger(this.getClass());

	public LoggTableViewSelCell(TableView<?> tableView) {

		this.tableView = tableView;
		selectedCells = tableView.getSelectionModel().getSelectedCells();

		listChangeListener = c -> {
			try {
				@SuppressWarnings("rawtypes")
				TablePosition tablePosition = (TablePosition) selectedCells.get(0);
				Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
				logger.info("Selected Cell (Row: " + tablePosition.getRow() + " / Col: "
						+ tablePosition.getColumn() + ") Value: " + val + " / " + val.getClass());
			} catch (IndexOutOfBoundsException e) {
				//
			} catch (NullPointerException e) {
				//
			}
		};

		String loggerBaseText = "Selected cell logger: ";
		MenuItem item1 = new MenuItem(loggerBaseText + "ON?");
		item1.setDisable(false);
		MenuItem item2 = new MenuItem(loggerBaseText + "OFF?");
		item2.setDisable(true);

		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				set();
				item1.setDisable(true);
				item2.setDisable(false);
			}
		});

		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				reset();
				item1.setDisable(false);
				item2.setDisable(true);
			}
		});

		contextMenu.getItems().addAll(item1, item2);

		tableView.setContextMenu(contextMenu);
	}

	public void set() {
		// http://stackoverflow.com/questions/17388866/getting-selected-item-from-a-javafx-tableview
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		// tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		selectedCells.addListener(listChangeListener);
	}

	public void reset() {
		tableView.getSelectionModel().setCellSelectionEnabled(false);
		selectedCells.removeListener(listChangeListener);
	}

}
