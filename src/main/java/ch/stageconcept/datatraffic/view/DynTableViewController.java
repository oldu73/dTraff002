package ch.stageconcept.datatraffic.view;

import static ch.stageconcept.datatraffic.util.LoggMode.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.stageconcept.datatraffic.model.DbToDynTableView;
import ch.stageconcept.datatraffic.util.LoggTableViewSelCell;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class DynTableViewController {

	// DB
	private final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/test001";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	private final String DB_SQL = "SELECT * from testtbl001";

	// ClassFactory
	private final String CLASS_PATH = "/temp"; // On Windows running on C:\,
												// this is C:\temp.
	private final String CLASS_FILE_EXTENSION = ".java";
	private final String CLASS_NAME = "DynClass00x";

	// "main"
	private DbToDynTableView dbToView;
	@SuppressWarnings("unused")
	private LoggTableViewSelCell loggTableViewSelCell;

	@FXML
	private StackPane stackPane;

	@FXML
	public void initialize() {
		// TableView
		try {
			dbToView = new DbToDynTableView(DB_DRIVER, DB_URL, DB_USER, DB_PASS, DB_SQL, CLASS_NAME, CLASS_PATH,
					CLASS_FILE_EXTENSION, false);
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IOException ex) {
			Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (ENABLE_LOGG_TABLEVIEW_SELECTED_CELL) {
			loggTableViewSelCell = new LoggTableViewSelCell(dbToView);
		}

		stackPane.getChildren().add(dbToView);
	}

	public DbToDynTableView getDynTableView() {
		return dbToView;
	}

}
