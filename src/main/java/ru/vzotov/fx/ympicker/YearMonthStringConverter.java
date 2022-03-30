package ru.vzotov.fx.ympicker;

import javafx.util.StringConverter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public class YearMonthStringConverter extends StringConverter<YearMonth> {

    private final DateTimeFormatter formatter;
    private final DateTimeFormatter parser;

    public YearMonthStringConverter(DateTimeFormatter formatter, DateTimeFormatter parser) {
        this.formatter = Objects.requireNonNull(formatter);
        this.parser = Objects.requireNonNull(parser);
    }

    @Override
    public String toString(YearMonth value) {
        if (value == null) {
            return "";
        }
        return formatter.format(value);
    }

    @Override
    public YearMonth fromString(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        text = text.trim();

        try {
            TemporalAccessor temporal = parser.parse(text);
            return YearMonth.from(temporal);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

}
