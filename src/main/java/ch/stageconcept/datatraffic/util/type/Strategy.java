package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import javafx.scene.control.ComboBox;

interface Strategy<T, U> {

	void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException;

	void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter, T filterController);

	DynTableFilter<U> createDynTableViewFilter(
			ComboBox<DynTableFilter<?>> columnNameTypeComboBox, T filterController);

}
