package ru.vzotov.fx.ympicker;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class DefaultYearMonthPickerContent extends YearMonthPickerContent {
    private static final String STYLE_BUTTON_PREV = "btn-prev";
    private static final String STYLE_BUTTON_NEXT = "btn-next";
    private static final String STYLE_BUTTON_MONTH = "btn-month";
    private static final String STYLE_INPUT_YEAR = "input-year";
    private static final String STYLE_GRID = "grid";

    private final Locale locale = Locale.getDefault();

    private final ToggleGroup monthGroup;
    private final ToggleButton[] monthButtons;

    private final TextField yearField;

    private final Button prevYear;
    private final Button nextYear;

    public DefaultYearMonthPickerContent(YearMonthPicker yearMonthPicker) {
        super(yearMonthPicker);

        yearField = new TextField();
        yearField.getStyleClass().add(STYLE_INPUT_YEAR);
        yearField.setMaxWidth(50);
        yearField.setAlignment(Pos.CENTER);
        yearField.setOnAction(event -> {
            YearMonth ym = getDisplayedYearMonth();
            try {
                setDisplayedYearMonth(ym.withYear(
                        Integer.parseInt(yearField.getText())
                ));
            } catch (NumberFormatException ignored) {
            }
        });

        prevYear = new Button("<");
        prevYear.getStyleClass().add(STYLE_BUTTON_PREV);
        prevYear.setOnAction(event -> {
            YearMonth ym = getDisplayedYearMonth();
            setDisplayedYearMonth(ym.withYear(Math.max(ym.getYear() - 1, Year.MIN_VALUE)));
        });

        nextYear = new Button(">");
        nextYear.getStyleClass().add(STYLE_BUTTON_NEXT);
        nextYear.setOnAction(event -> {
            YearMonth ym = getDisplayedYearMonth();
            setDisplayedYearMonth(ym.withYear(Math.min(ym.getYear() + 1, Year.MAX_VALUE)));
        });

        final BorderPane years = new BorderPane(yearField, null, nextYear, null, prevYear);

        final GridPane months = new GridPane();
        months.getStyleClass().add(STYLE_GRID);
        VBox.setVgrow(months, Priority.ALWAYS);

        monthGroup = new ToggleGroup();
        monthButtons = new ToggleButton[Month.values().length];
        for (Month month : Month.values()) {
            final ToggleButton btn = new ToggleButton(month.getDisplayName(TextStyle.SHORT_STANDALONE, locale));
            btn.getStyleClass().add(STYLE_BUTTON_MONTH);
            btn.setUserData(month);
            btn.setToggleGroup(monthGroup);
            btn.setOnAction(this::onMonthSelected);

            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setFillWidth(btn, true);
            GridPane.setFillHeight(btn, true);

            monthButtons[month.ordinal()] = btn;

            months.add(btn, month.ordinal() % 3, month.ordinal() / 3);
        }

        getChildren().addAll(years, months);

        // bind controls
        displayedYearMonthProperty().addListener((observable, oldValue, newValue) -> {
            yearField.setText(newValue == null ? "" : String.valueOf(newValue.getYear()));
            if (newValue != null) {
                monthButtons[newValue.getMonth().ordinal()].setSelected(true);
            } else {
                monthGroup.selectToggle(null);
            }
        });
    }

    private void onMonthSelected(ActionEvent event) {
        final Month month = (Month) ((ToggleButton) event.getSource()).getUserData();
        setDisplayedYearMonth(getDisplayedYearMonth().withMonth(month.getValue()));
        commit();
    }

}
