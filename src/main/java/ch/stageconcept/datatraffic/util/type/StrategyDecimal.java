package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.view.DTVFEditDialogDecimalController;
import javafx.scene.control.ComboBox;

public class StrategyDecimal implements Strategy<DTVFEditDialogDecimalController, Double> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getDouble(i));
	}

	@Override
	public void setControllerFilterValue(DynTableViewFilter<?> selectedDynTableViewFilter, DTVFEditDialogDecimalController filterController) {
		filterController.setFilterValue((Double) selectedDynTableViewFilter.getDbColumnValue());
	}

	@Override
	public DynTableViewFilter<Double> createDynTableViewFilter(ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox, DTVFEditDialogDecimalController filterController) {
		return new DynTableViewFilter<Double>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
