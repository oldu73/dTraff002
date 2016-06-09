package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.filter.editDialog.view.VarCharController;
import javafx.scene.control.ComboBox;

public class StrategyString implements Strategy<VarCharController, String> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getString(i));
	}

	@Override
	public void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter, VarCharController filterController) {
		filterController.setFilterValue((String) selectedDynTableFilter.getDbColumnValue());
	}

	@Override
	public DynTableFilter<String> createDynTableViewFilter(ComboBox<DynTableFilter<?>> columnNameTypeComboBox, VarCharController filterController) {
		return new DynTableFilter<String>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
