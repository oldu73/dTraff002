package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.view.DTVFEditDialogVarCharController;
import javafx.scene.control.ComboBox;

public class StrategyString implements Strategy<DTVFEditDialogVarCharController, String> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getString(i));
	}

	@Override
	public void setControllerFilterValue(DynTableViewFilter<?> selectedDynTableViewFilter, DTVFEditDialogVarCharController filterController) {
		filterController.setFilterValue((String) selectedDynTableViewFilter.getDbColumnValue());
	}

	@Override
	public DynTableViewFilter<String> createDynTableViewFilter(ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox, DTVFEditDialogVarCharController filterController) {
		return new DynTableViewFilter<String>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
