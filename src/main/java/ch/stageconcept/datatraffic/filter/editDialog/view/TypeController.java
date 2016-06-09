package ch.stageconcept.datatraffic.filter.editDialog.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by OLDU on 09.06.2016.
 *
 */
abstract class TypeController<T> {

    protected BooleanProperty valueEmpty;

    TypeController(boolean value) {
        this.valueEmpty = new SimpleBooleanProperty(value);
    }

    public abstract void initialize();

    public abstract T getValue();

    public BooleanProperty isValueEmpty() {
        return valueEmpty;
    }

    public abstract void setFilterValue(T filterValue);

}
