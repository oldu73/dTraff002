package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.filter.editDialog.view.DecimalController;
import javafx.scene.control.ComboBox;

class StrategyDecimal implements Strategy<DecimalController, Double> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getDouble(i));
	}

	@Override
	public void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter, DecimalController filterController) {
		filterController.setFilterValue((Double) selectedDynTableFilter.getDbColumnValue());
	}

	@Override
	public DynTableFilter<Double> createDynTableViewFilter(ComboBox<DynTableFilter<?>> columnNameTypeComboBox, DecimalController filterController) {
		return new DynTableFilter<>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
