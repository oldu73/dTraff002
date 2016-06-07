package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.view.DTVFEditDialogDateTimeController;
import javafx.scene.control.ComboBox;

public class StrategyDateTime implements Strategy<DTVFEditDialogDateTimeController, LocalDateTime> {

	@Override
	public void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		dataRow.add(dbResultSet.getTimestamp(i).toLocalDateTime());
	}

	@Override
	public void setControllerFilterValue(DynTableViewFilter<?> selectedDynTableViewFilter, DTVFEditDialogDateTimeController filterController) {
		filterController.setFilterValue((LocalDateTime) selectedDynTableViewFilter.getDbColumnValue());
	}

	@Override
	public DynTableViewFilter<LocalDateTime> createDynTableViewFilter(
			ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox, DTVFEditDialogDateTimeController filterController) {
		return new DynTableViewFilter<LocalDateTime>(columnNameTypeComboBox.getValue().getDbColumnName(),
				columnNameTypeComboBox.getValue().getDbColumnType(), filterController.getValue());
	}

}
