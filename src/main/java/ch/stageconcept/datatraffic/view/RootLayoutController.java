package ch.stageconcept.datatraffic.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class RootLayoutController {

	@FXML
	private AnchorPane dynTableViewAnchorPane;

	@FXML
	private AnchorPane dynTableViewFilterAnchorPane;

	@FXML
	private AnchorPane consoleAnchorPane;

	@FXML
	public void initialize() {

	}

	public AnchorPane getDynTableViewAnchorPane() {
		return dynTableViewAnchorPane;
	}

	public AnchorPane getDynTableViewFilterAnchorPane() {
		return dynTableViewFilterAnchorPane;
	}

	public AnchorPane getConsoleAnchorPane() {
		return consoleAnchorPane;
	}
}
