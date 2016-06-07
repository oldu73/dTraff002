package ch.stageconcept.datatraffic.util;

import java.util.ResourceBundle;

public enum LoggMode {

	INSTANCE;

	private static final String ENABLE_LOGG_TABLEVIEW_SELECTED_CELL_KEY = "ENABLE_LOGG_TABLEVIEW_SELECTED_CELL";
	public static boolean ENABLE_LOGG_TABLEVIEW_SELECTED_CELL = false;
	
	// http://stackoverflow.com/questions/536449/cannot-refer-to-the-static-enum-field-within-an-initializer
	
	static {
		
		// http://www.avajava.com/tutorials/lessons/how-do-i-read-a-properties-file-with-a-resource-bundle.html

		ResourceBundle rb = ResourceBundle.getBundle("config");
		
		if (rb.getString(ENABLE_LOGG_TABLEVIEW_SELECTED_CELL_KEY).contains("true")) {
			ENABLE_LOGG_TABLEVIEW_SELECTED_CELL = true;
		} 
		
	}

}
