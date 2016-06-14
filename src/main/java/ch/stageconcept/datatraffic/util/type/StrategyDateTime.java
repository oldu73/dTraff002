package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.filter.editDialog.view.DateTimeController;
import javafx.scene.control.ComboBox;

class StrategyDateTime implements Strategy<DateTimeController, LocalDateTime> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getTimestamp(i).toLocalDateTime());
	}

	@Override
	public void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter, DateTimeController filterController) {
		filterController.setFilterValue((LocalDateTime) selectedDynTableFilter.getDbColumnValue());
	}

	@Override
	public DynTableFilter<LocalDateTime> createDynTableViewFilter(
			ComboBox<DynTableFilter<?>> columnNameTypeComboBox, DateTimeController filterController) {
		return new DynTableFilter<>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
