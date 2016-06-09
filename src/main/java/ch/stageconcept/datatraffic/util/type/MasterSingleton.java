package ch.stageconcept.datatraffic.util.type;

import java.time.LocalDateTime;
import ch.stageconcept.datatraffic.filter.editDialog.view.DateTimeController;
import ch.stageconcept.datatraffic.filter.editDialog.view.DecimalController;
import ch.stageconcept.datatraffic.filter.editDialog.view.IntController;
import ch.stageconcept.datatraffic.filter.editDialog.view.VarCharController;

public enum MasterSingleton {

	INSTANCE;

	// DB (MySQL)
	private static final String DB_TYPE_INT = "INT";
	public static final String DB_TYPE_DATETIME = "DATETIME";
	private static final String DB_TYPE_DECIMAL = "DECIMAL";
	private static final String DB_TYPE_VARCHAR = "VARCHAR";

	// ===

	public static final String FILTER_FXML_FILE_BASE_DIR = "filter/editDialog/view/";

	private static final String MEMBER_INT_PRIMITIVE = "int";
	private static final String MEMBER_INTEGER_TYPE = "Integer";
	private static final String INT_FILTER_FXML_FILE = FILTER_FXML_FILE_BASE_DIR + "Int.fxml";

	private static final String MEMBER_DOUBLE_PRIMITIVE = "double";
	private static final String MEMBER_DOUBLE_TYPE = "Double";
	private static final String DOUBLE_FILTER_FXML_FILE = FILTER_FXML_FILE_BASE_DIR + "Decimal.fxml";

	private static final String MEMBER_OBJECT_PRIMITIVE = "Object";
	private static final String MEMBER_OBJECT_TYPE = MEMBER_OBJECT_PRIMITIVE;

	private static final String MEMBER_OBJECT_LOCALDATETIME = "LocalDateTime";
	private static final String IMPORT_LITTERAL_LOCALDATETIME = "java.time.LocalDateTime";
	private static final String LOCALDATETIME_FILTER_FXML_FILE = FILTER_FXML_FILE_BASE_DIR
			+ "DateTime.fxml";

	private static final String MEMBER_STRING_PRIMITIVE = "String";
	private static final String MEMBER_STRING_TYPE = MEMBER_STRING_PRIMITIVE;
	private static final String STRING_FILTER_FXML_FILE = FILTER_FXML_FILE_BASE_DIR + "VarChar.fxml";

	// Data
	private final Data<IntController, Integer> dataInt;
	private final Data<DecimalController, Double> dataDecimal;
	private final Data<DateTimeController, LocalDateTime> dataDateTime;
	private final Data<VarCharController, String> dataString;

	private final DefaultHashMap<String, Data<?, ?>> dataHashMap;

	private MasterSingleton() {

		dataInt = new Data<>(MEMBER_INT_PRIMITIVE, MEMBER_INTEGER_TYPE, null, null, INT_FILTER_FXML_FILE,
				new StrategyInt());

		dataDecimal = new Data<>(MEMBER_DOUBLE_PRIMITIVE, MEMBER_DOUBLE_TYPE, null, null, DOUBLE_FILTER_FXML_FILE,
				new StrategyDecimal());

		dataDateTime = new Data<>(MEMBER_OBJECT_PRIMITIVE, MEMBER_OBJECT_TYPE, MEMBER_OBJECT_LOCALDATETIME,
				IMPORT_LITTERAL_LOCALDATETIME, LOCALDATETIME_FILTER_FXML_FILE, new StrategyDateTime());

		dataString = new Data<>(MEMBER_STRING_PRIMITIVE, MEMBER_STRING_TYPE, null, null, STRING_FILTER_FXML_FILE,
				new StrategyString());

		dataHashMap = new DefaultHashMap<>(dataString); // String (mysql
														// VARCHAR) type by
														// default
		dataHashMap.put(DB_TYPE_INT, dataInt);
		dataHashMap.put(DB_TYPE_DECIMAL, dataDecimal);
		dataHashMap.put(DB_TYPE_DATETIME, dataDateTime);
		dataHashMap.put(DB_TYPE_VARCHAR, dataString);

	}

	public DefaultHashMap<String, Data<?, ?>> getDataHashMap() {
		return dataHashMap;
	}

}
