package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.filter.editDialog.view.IntController;
import javafx.scene.control.ComboBox;

public class StrategyInt implements Strategy<IntController, Integer> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getInt(i));
	}

	@Override
	public void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter, IntController filterController) {
		filterController.setFilterValue((Integer) selectedDynTableFilter.getDbColumnValue());
	}

	@Override
	public DynTableFilter<Integer> createDynTableViewFilter(
			ComboBox<DynTableFilter<?>> columnNameTypeComboBox, IntController filterController) {
		return new DynTableFilter<Integer>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
