package ch.stageconcept.datatraffic.view;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;

import ch.stageconcept.datatraffic.util.TextAreaAppender;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class ConsoleController {

	@FXML
	private StackPane stackPane;

	private final TextArea loggingView = new TextArea();

	//private Logger logger = Logger.getLogger(this.getClass());

	@FXML
	public void initialize() {

		TextAreaAppender.setTextArea(loggingView);

		stackPane.getChildren().add(loggingView);

		/*
		PropertyConfigurator.configure(path);
		logger.debug("msg de debogage");
		logger.info("msg d'information");
		logger.warn("msg d'avertissement");
		logger.error("msg d'erreur");
		logger.fatal("msg d'erreur fatale");
		*/
		
		/* in order to use logging in a class, include following template :
		 	private Logger logger = Logger.getLogger(this.getClass());
			PropertyConfigurator.configure(ConsoleController.LOG4J_PROPERTIES_PATH);
			logger.fatal("Hello, world!");
		 */

	}

}
