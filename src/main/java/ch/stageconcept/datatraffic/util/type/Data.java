package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.filter.table.model.DynTableFilter;
import ch.stageconcept.datatraffic.dbToDynTableView.util.ClassComposer;
import ch.stageconcept.datatraffic.util.Pair;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Data<T, U> {

	private String memberPrimitive;
	private String memberType;
	private String memberGeneric;
	private String importLitteral;
	private String filterEditDialogBodyFxmlFileName;
	private T filterController;
	private Strategy<T, U> strategy;

	public Data(String memberPrimitive, String memberType, String memberGeneric, String importLitteral,
			String filterEditDialogBodyFxmlFileName, Strategy<T, U> strategy) {
		this.memberPrimitive = memberPrimitive;
		this.memberType = memberType;
		this.memberGeneric = memberGeneric;
		this.importLitteral = importLitteral;
		this.filterEditDialogBodyFxmlFileName = filterEditDialogBodyFxmlFileName;
		this.strategy = strategy;
	}

	public final void dynClassMemberMasterCreate(ClassComposer dynClass, String dbColumnName) {
		dynClass.addImport(importLitteral, true);
		dynClass.memberMasterCreate(memberPrimitive, memberType, memberGeneric, dbColumnName);
	}

	public final void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		strategy.addRowData(dataRow, dbResultSet, i);
	}

	public String getFilterEditDialogBodyFxmlFileName() {
		return filterEditDialogBodyFxmlFileName;
	}

	public final void addFilterComboBoxData(ObservableList<DynTableFilter<?>> columnNameTypeComboBoxData,
			Pair<String, String> temp) {
		columnNameTypeComboBoxData.add(new DynTableFilter<U>(temp.getFirst(), temp.getSecond()));
	}

	public final void setFilterEditDialogDataController(T filterController) {
		this.filterController = filterController;
	}

	public final void setControllerFilterValue(DynTableFilter<?> selectedDynTableFilter) {
		strategy.setControllerFilterValue(selectedDynTableFilter, filterController);
	}

	public final DynTableFilter<U> createDynTableViewFilter(
			ComboBox<DynTableFilter<?>> columnNameTypeComboBox) {
		return strategy.createDynTableViewFilter(columnNameTypeComboBox, filterController);
	}

}
