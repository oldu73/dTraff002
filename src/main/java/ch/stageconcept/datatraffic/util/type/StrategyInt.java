package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.view.DTVFEditDialogIntController;
import javafx.scene.control.ComboBox;

public class StrategyInt implements Strategy<DTVFEditDialogIntController, Integer> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getInt(i));
	}

	@Override
	public void setControllerFilterValue(DynTableViewFilter<?> selectedDynTableViewFilter, DTVFEditDialogIntController filterController) {
		filterController.setFilterValue((Integer) selectedDynTableViewFilter.getDbColumnValue());
	}

	@Override
	public DynTableViewFilter<Integer> createDynTableViewFilter(
			ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox, DTVFEditDialogIntController filterController) {
		return new DynTableViewFilter<Integer>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
