package ru.vzotov.fx.ympicker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class YmDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FlowPane flow = new FlowPane();
        final Pane root = new Pane(flow);
        flow.setPadding(new Insets(16));

        flow.getChildren().add(new Label("Year and month"));
        flow.getChildren().add(new YearMonthPicker());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
