package ru.vzotov.fx.ympicker;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.time.YearMonth;

import static ru.vzotov.fx.ympicker.YearMonthPicker.STYLE_BUTTON_ARROW;
import static ru.vzotov.fx.ympicker.YearMonthPicker.STYLE_ICON_ARROW;

public class YearMonthPickerSkin extends TextFieldSkin {

    private final YearMonthPicker yearMonthPicker;
    private final YearMonthPickerContent yearMonthPickerContent;
    private final HBox root;
    private Popup popup;

    public YearMonthPickerSkin(YearMonthPicker control) {
        super(control);

        this.yearMonthPicker = control;
        this.yearMonthPickerContent = yearMonthPicker.getContentFactory().apply(yearMonthPicker);

        control.converterProperty().addListener(it -> {
            setupConverter();
        });
        setupConverter();

        control.valueProperty().addListener(this::valueChanged);
        valueChanged(control.valueProperty(), null, control.getValue());

        Region icon = new Region();
        icon.getStyleClass().add(STYLE_ICON_ARROW);

        Button arrow = new Button();
        arrow.getStyleClass().add(STYLE_BUTTON_ARROW);
        arrow.setGraphic(icon);
        arrow.textProperty().bind(control.arrowSymbolProperty());
        arrow.setCursor(Cursor.DEFAULT);
        arrow.setOnAction(this::onArrow);

        root = new HBox(arrow);
        root.setManaged(false);
        root.setFillHeight(true);
        root.setAlignment(Pos.CENTER_LEFT);

        getChildren().add(root);
        control.requestLayout();
    }

    private void setupConverter() {
        yearMonthPicker.textProperty().unbindBidirectional(yearMonthPicker.valueProperty());
        yearMonthPicker.textProperty().bindBidirectional(yearMonthPicker.valueProperty(), yearMonthPicker.getConverter());
    }

    private void valueChanged(ObservableValue<? extends YearMonth> o, YearMonth oldValue, YearMonth newValue) {
        hidePopup();
        yearMonthPickerContent.setDisplayedYearMonth(newValue);
    }

    private void onArrow(ActionEvent event) {
        event.consume();
        togglePopup();
    }

    private void hidePopup() {
        if (popup != null) {
            popup.hide();
            popup = null;
        }
    }

    private void togglePopup() {
        if (popup != null) {
            hidePopup();
        } else {
            popup = new Popup();
            popup.getContent().add(yearMonthPickerContent);
            popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_RIGHT);

            final Bounds localBounds = getSkinnable().getBoundsInLocal();
            final Point2D pt = getSkinnable().localToScreen(localBounds.getMaxX(), localBounds.getMaxY());
            popup.show(getSkinnable(), pt.getX(), pt.getY());

            popup.requestFocus();
            popup.focusedProperty().addListener((o, wasFocused, isFocused) -> {
                if (wasFocused != isFocused) {
                    popup.hide();
                    popup = null;
                }
            });
        }
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        final double width = root.prefWidth(h);
        final Insets i = yearMonthPicker.getInsets();
        super.layoutChildren(x, y, w - width, h);
        layoutInArea(root, x + w - width + i.getRight(), y, width, h, 0, Insets.EMPTY, true, true, HPos.RIGHT, VPos.CENTER);
    }


}
