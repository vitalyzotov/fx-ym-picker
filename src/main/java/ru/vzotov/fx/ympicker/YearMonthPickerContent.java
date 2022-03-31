package ru.vzotov.fx.ympicker;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;

import java.time.YearMonth;

public class YearMonthPickerContent extends VBox {
    private static final String DEFAULT_STYLE_CLASS = "yearmonth-picker-content";

    protected final YearMonthPicker yearMonthPicker;

    public YearMonthPickerContent(YearMonthPicker yearMonthPicker) {
        super();
        this.yearMonthPicker = yearMonthPicker;
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    protected void commit() {
        yearMonthPicker.setValue(getDisplayedYearMonth());
    }

    // displayedYearMonth
    private final ObjectProperty<YearMonth> displayedYearMonth = new SimpleObjectProperty<YearMonth>(this, "displayedYearMonth");

    public YearMonth getDisplayedYearMonth() {
        return displayedYearMonth.get();
    }

    public void setDisplayedYearMonth(YearMonth displayedYearMonth) {
        this.displayedYearMonth.set(displayedYearMonth);
    }

    public ObjectProperty<YearMonth> displayedYearMonthProperty() {
        return displayedYearMonth;
    }

}
