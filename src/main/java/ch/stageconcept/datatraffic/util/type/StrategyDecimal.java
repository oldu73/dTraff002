package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.view.DTVFEditDialogDecimalController;
import javafx.scene.control.ComboBox;

public class StrategyDecimal implements Strategy<DTVFEditDialogDecimalController, Double> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getDouble(i));
	}

	@Override
	public void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter, DTVFEditDialogDecimalController filterController) {
		filterController.setFilterValue((Double) selectedDynTableFilter.getDbColumnValue());
	}

	@Override
	public DynTableFilter<Double> createDynTableViewFilter(ComboBox<DynTableFilter<?>> columnNameTypeComboBox, DTVFEditDialogDecimalController filterController) {
		return new DynTableFilter<Double>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
