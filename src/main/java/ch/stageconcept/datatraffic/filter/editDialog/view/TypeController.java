package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by OLDU on 09.06.2016.
 *
 */
abstract class TypeController<T> {

    protected T value;
    BooleanProperty valueEmpty;

    @FXML
    protected TextField filterValue;

    TypeController() {
        this.valueEmpty = new SimpleBooleanProperty(true);
    }

    public abstract void initialize();

    public abstract T getValue();

    BooleanProperty isValueEmpty() {
        return valueEmpty;
    }

    public abstract void setFilterValue(T filterValue);

}
