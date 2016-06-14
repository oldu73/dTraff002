package ch.stageconcept.datatraffic;

import java.io.IOException;

import ch.stageconcept.datatraffic.dbToDynTableView.view.DynTableController;
import ch.stageconcept.datatraffic.filter.table.view.DynTableFilterController;
import ch.stageconcept.datatraffic.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static final String STAGE_TITLE = "DataTraffic";
	private static final String DYN_TABLE = "dbToDynTableView/view/DynTable.fxml";
	private static final String DYN_TABLE_FILTER = "filter/table/view/DynTableFilter.fxml";
	private static final String CONSOLE = "view/Console.fxml";

	private Stage primaryStage;
	private AnchorPane dynTableViewAnchorPane;
	private AnchorPane dynTableViewFilterAnchorPane;
	private AnchorPane consoleAnchorPane;
	private FXMLLoader dynTableViewLoader;
	private FXMLLoader dynTableViewFilterLoader;
	private FXMLLoader consoleLoader;
	private DynTableController dynTableController;

	public MainApp() {
		dynTableViewLoader = new FXMLLoader();
		dynTableViewFilterLoader = new FXMLLoader();
		consoleLoader = new FXMLLoader();
	}

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(STAGE_TITLE);

		initRootLayout();

		initLayout(DYN_TABLE, dynTableViewAnchorPane, dynTableViewLoader);
		dynTableController = dynTableViewLoader.getController();

		initLayout(DYN_TABLE_FILTER, dynTableViewFilterAnchorPane, dynTableViewFilterLoader);
		DynTableFilterController dynTableFilterController = dynTableViewFilterLoader.getController();
		// Give the controller access to the main app.
		dynTableFilterController.setMainApp(this);

		initLayout(CONSOLE, consoleAnchorPane, consoleLoader);
	}

	/**
	 * Initializes the root layout.
	 */
	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			// INFO: http://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
			// include fxml resources in maven pom file
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			BorderPane rootLayout = loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			dynTableViewAnchorPane = controller.getDynTableViewAnchorPane();
			dynTableViewFilterAnchorPane = controller.getDynTableViewFilterAnchorPane();
			consoleAnchorPane = controller.getConsoleAnchorPane();

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes layout.
	 */
	private void initLayout(String fxmlFileName, AnchorPane anchorPane, FXMLLoader loader) {
		try {
			// Load layout from fxml file.
			loader.setLocation(MainApp.class.getResource(fxmlFileName));
			StackPane stackPane = loader.load();

			// http://stackoverflow.com/questions/15223812/javafx-panel-inside-panel-auto-resizing
			AnchorPane.setTopAnchor(stackPane, 0.0);
			AnchorPane.setRightAnchor(stackPane, 0.0);
			AnchorPane.setLeftAnchor(stackPane, 0.0);
			AnchorPane.setBottomAnchor(stackPane, 0.0);

			anchorPane.getChildren().add(stackPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DynTableController getDynTableController() {
		return dynTableController;
	}

	/**
	 * Returns the main stage.
	 *
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
