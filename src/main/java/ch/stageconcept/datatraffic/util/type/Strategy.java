package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import javafx.scene.control.ComboBox;

public interface Strategy<T, U> {

	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException;

	public void setControllerFilterValue(DynTableViewFilter<?> selectedDynTableViewFilter, T filterController);

	public DynTableViewFilter<U> createDynTableViewFilter(
			ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox, T filterController);

}
