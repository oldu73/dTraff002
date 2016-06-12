package ch.stageconcept.datatraffic.filter.table.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DynTableFilter<T> {

	private final StringProperty dbColumnName;
	private final StringProperty dbColumnType;
	private final ObjectProperty<T> dbColumnValue;

	/**
	 * Default constructor.
	 */
	public DynTableFilter() {
		this(null, null, null);
	}

	/**
	 * Constructor.
	 *
	 * @param dbColumnName
	 * @param dbColumnType
	 * @param dbColumnValue
	 */
	public DynTableFilter(String dbColumnName, String dbColumnType, T dbColumnValue) {
		this.dbColumnName = new SimpleStringProperty(dbColumnName);
		this.dbColumnType = new SimpleStringProperty(dbColumnType);
		this.dbColumnValue = new SimpleObjectProperty<T>(dbColumnValue);
	}
	
	public DynTableFilter(String dbColumnName, String dbColumnType) {
		this.dbColumnName = new SimpleStringProperty(dbColumnName);
		this.dbColumnType = new SimpleStringProperty(dbColumnType);
		this.dbColumnValue = null;
	}
	
	public void setDynTableViewFilterValue(DynTableFilter<T> dynTableFilter) {
		this.setDbColumnName(dynTableFilter.getDbColumnName());
		this.setDbColumnType(dynTableFilter.getDbColumnType());
		this.setDbColumnValue(dynTableFilter.getDbColumnValue());
	}

	public String getDbColumnName() {
		return dbColumnName.get();
	}

	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName.set(dbColumnName);
	}

	public StringProperty dbColumnNameProperty() {
		return dbColumnName;
	}

	public String getDbColumnType() {
		return dbColumnType.get();
	}

	public void setDbColumnType(String dbColumnType) {
		this.dbColumnType.set(dbColumnType);
	}

	public StringProperty dbColumnTypeProperty() {
		return dbColumnType;
	}

	public T getDbColumnValue() {
		return dbColumnValue.get();
	}

	public void setDbColumnValue(T dbColumnValue) {
		this.dbColumnValue.set(dbColumnValue);
	}

	public ObjectProperty<T> dbColumnValueProperty() {
		return dbColumnValue;
	}
}
