package ch.stageconcept.datatraffic.util.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ch.stageconcept.datatraffic.model.DynTableViewFilter;
import ch.stageconcept.datatraffic.util.DynClassFactory;
import ch.stageconcept.datatraffic.util.Pair;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Data<T, U> {

	protected String memberPrimitive;
	protected String memberType;
	protected String memberGeneric;
	protected String importLitteral;
	protected String filterEditDialogBodyFxmlFileName;
	protected T filterController;
	protected Strategy<T, U> strategy;

	public Data(String memberPrimitive, String memberType, String memberGeneric, String importLitteral,
			String filterEditDialogBodyFxmlFileName, Strategy<T, U> strategy) {
		this.memberPrimitive = memberPrimitive;
		this.memberType = memberType;
		this.memberGeneric = memberGeneric;
		this.importLitteral = importLitteral;
		this.filterEditDialogBodyFxmlFileName = filterEditDialogBodyFxmlFileName;
		this.strategy = strategy;
	}

	public final void dynClassMemberMasterCreate(DynClassFactory dynClass, String dbColumnName) {
		dynClass.addImport(importLitteral, true);
		dynClass.memberMasterCreate(memberPrimitive, memberType, memberGeneric, dbColumnName);
	}

	public final void addRowData(List<Object> dataRow, ResultSet dbResultSet, int i) throws SQLException {
		strategy.addRowData(dataRow, dbResultSet, i);
	}

	public String getFilterEditDialogBodyFxmlFileName() {
		return filterEditDialogBodyFxmlFileName;
	}

	public final void addFilterComboBoxData(ObservableList<DynTableViewFilter<?>> columnNameTypeComboBoxData,
			Pair<String, String> temp) {
		columnNameTypeComboBoxData.add(new DynTableViewFilter<U>(temp.getFirst(), temp.getSecond()));
	}

	public final void setFilterEditDialogDataController(T filterController) {
		this.filterController = filterController;
	}

	public final void setControllerFilterValue(DynTableViewFilter<?> selectedDynTableViewFilter) {
		strategy.setControllerFilterValue(selectedDynTableViewFilter, filterController);
	}

	public final DynTableViewFilter<U> createDynTableViewFilter(
			ComboBox<DynTableViewFilter<?>> columnNameTypeComboBox) {
		return strategy.createDynTableViewFilter(columnNameTypeComboBox, filterController);
	}

}
