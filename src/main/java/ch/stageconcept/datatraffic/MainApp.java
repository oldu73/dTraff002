package ch.stageconcept.datatraffic;

import java.io.IOException;

import ch.stageconcept.datatraffic.view.DynTableViewController;
import ch.stageconcept.datatraffic.view.DynTableViewFilterController;
import ch.stageconcept.datatraffic.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane dynTableViewAnchorPane;
	private AnchorPane dynTableViewFilterAnchorPane;
	private AnchorPane consoleAnchorPane;
	private FXMLLoader dynTableViewLoader;
	private FXMLLoader dynTableViewFilterLoader;
	private FXMLLoader consoleLoader;
	private DynTableViewController dynTableViewController;
	private DynTableViewFilterController dynTableViewFilterController;

	public MainApp() {
		dynTableViewLoader = new FXMLLoader();
		dynTableViewFilterLoader = new FXMLLoader();
		consoleLoader = new FXMLLoader();
	}

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Say hi to intellij, from home..");
		System.out.println();
		System.out.println("Say hi to intellij, from office..");
		System.out.println();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DataTraffic");

		initRootLayout();

		initLayout("view/DynTableView.fxml", dynTableViewAnchorPane, dynTableViewLoader);
		dynTableViewController = dynTableViewLoader.getController();

		initLayout("view/DynTableViewFilter.fxml", dynTableViewFilterAnchorPane, dynTableViewFilterLoader);
		dynTableViewFilterController = dynTableViewFilterLoader.getController();
		// Give the controller access to the main app.
		dynTableViewFilterController.setMainApp(this);

		initLayout("view/Console.fxml", consoleAnchorPane, consoleLoader);
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			// INFO: http://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
			// include fxml resources in maven pom file
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

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
			StackPane stackPane = (StackPane) loader.load();

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

	public DynTableViewController getDynTableViewController() {
		return dynTableViewController;
	}

	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
