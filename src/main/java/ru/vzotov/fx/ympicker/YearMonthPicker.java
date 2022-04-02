package ru.vzotov.fx.ympicker;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class YearMonthPicker extends TextField {

    private static final String DEFAULT_STYLE_CLASS = "yearmonth-picker";
    public static final String STYLE_BUTTON_ARROW = "arrow-button";
    public static final String STYLE_ICON_ARROW = "icon";

    private static final StringConverter<YearMonth> defaultConverter = new YearMonthStringConverter(
            DateTimeFormatter.ofPattern("yyyy-MM"),
            DateTimeFormatter.ofPattern("yyyy-MM")
    );

    private static final Function<YearMonthPicker, ? extends YearMonthPickerContent> defaultContentFactory =
            DefaultYearMonthPickerContent::new;

    public YearMonthPicker() {
        this(YearMonth.now());
    }

    public YearMonthPicker(YearMonth value) {
        this(value, defaultContentFactory);
    }

    public YearMonthPicker(YearMonth value, Function<YearMonthPicker, ? extends YearMonthPickerContent> contentFactory) {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setValue(value);
        this.contentFactory = contentFactory;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new YearMonthPickerSkin(this);
    }

    // content factory

    private final Function<YearMonthPicker, ? extends YearMonthPickerContent> contentFactory;

    public Function<YearMonthPicker, ? extends YearMonthPickerContent> getContentFactory() {
        return contentFactory;
    }

    // value

    private final ObjectProperty<YearMonth> value = new SimpleObjectProperty<>(this, "value", null);

    public YearMonth getValue() {
        return value.get();
    }

    public ObjectProperty<YearMonth> valueProperty() {
        return value;
    }

    public void setValue(YearMonth value) {
        this.value.set(value);
    }

    // converter

    private final ObjectProperty<StringConverter<YearMonth>> converter = new SimpleObjectProperty<>(this, "converter", defaultConverter);

    public StringConverter<YearMonth> getConverter() {
        return converter.get();
    }

    public ObjectProperty<StringConverter<YearMonth>> converterProperty() {
        return converter;
    }

    public void setConverter(StringConverter<YearMonth> converter) {
        this.converter.set(converter);
    }

    // arrow symbol

    private final StringProperty arrowSymbol = new SimpleStringProperty(this, "arrowSymbol", "@");

    public String getArrowSymbol() {
        return arrowSymbol.get();
    }

    public StringProperty arrowSymbolProperty() {
        return arrowSymbol;
    }

    public void setArrowSymbol(String arrowSymbol) {
        this.arrowSymbol.set(arrowSymbol);
    }
}
