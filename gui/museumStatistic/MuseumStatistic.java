import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.stage.*;
import javafx.util.*;

public class MuseumStatistic extends Application {

    // private TextField totalVisitorTxtField, currentVisitorTxtField,
    // queuedVisitorTxtField, timeTxtField;
    // totalVisitor
    // dailyVisitorLimit
    // currentVisitor
    // hourlyVisitorLimit
    // queuedVisitor
    // expectedTotalVisitors
    // rejectedPurchases
    private int totalVisitor, currentVisitor, queuedVisitor, rejectedPurchases = 0;
    private int dailyVisitorLimit = 20, hourlyVisitorLimit = 5, expectedTotalVisitors = 12;
    private String museumStatus = "";
    private TextField totalVisitorTxtField, dailyVisitorLimitTxtField, currentVisitorTxtField,
            hourlyVisitorLimitTxtField, queuedVisitorTxtField, expectedTotalVisitorsTxtField, rejectedPurchasesTxtField,
            statusTxtField;

    @Override
    public void start(Stage stage) {

        Text museumStatisticsTxt = new Text("Museum Statistics");
        Text totalVisitorTxt = new Text("Total Visitors");
        Text dailyVisitorLimitTxt = new Text("Daily Visitor Limit");
        Text currentVisitorTxt = new Text("Current Visitors");
        Text hourlyVisitorLimitTxt = new Text("Hourly Visitor Limit");
        Text queuedVisitorTxt = new Text("Queued Visitors");
        Text expectedTotalVisitorsTxt = new Text("Expected Total Visitors");
        Text rejectedPurchasesTxt = new Text("Rejected Purchases");
        Text statusTxt = new Text("Status");

        totalVisitorTxtField = new TextField(String.valueOf(totalVisitor));
        dailyVisitorLimitTxtField = new TextField(String.valueOf(dailyVisitorLimit));
        currentVisitorTxtField = new TextField(String.valueOf(currentVisitor));
        hourlyVisitorLimitTxtField = new TextField(String.valueOf(hourlyVisitorLimit));
        queuedVisitorTxtField = new TextField(String.valueOf(queuedVisitor));
        expectedTotalVisitorsTxtField = new TextField(String.valueOf(expectedTotalVisitors));
        rejectedPurchasesTxtField = new TextField(String.valueOf(rejectedPurchases));
        statusTxtField = new TextField(String.valueOf(museumStatus));

        totalVisitorTxtField.setStyle("-fx-font: 16 arial;");
        totalVisitorTxtField.setPrefWidth(120);
        totalVisitorTxtField.setPrefHeight(40);
        dailyVisitorLimitTxtField.setStyle("-fx-font: 16 arial;");
        dailyVisitorLimitTxtField.setPrefWidth(120);
        dailyVisitorLimitTxtField.setPrefHeight(40);
        currentVisitorTxtField.setStyle("-fx-font: 16 arial;");
        currentVisitorTxtField.setPrefWidth(120);
        currentVisitorTxtField.setPrefHeight(40);
        hourlyVisitorLimitTxtField.setStyle("-fx-font: 16 arial;");
        hourlyVisitorLimitTxtField.setPrefWidth(120);
        hourlyVisitorLimitTxtField.setPrefHeight(40);
        queuedVisitorTxtField.setStyle("-fx-font: 16 arial;");
        queuedVisitorTxtField.setPrefWidth(120);
        queuedVisitorTxtField.setPrefHeight(40);
        expectedTotalVisitorsTxtField.setStyle("-fx-font: 16 arial;");
        expectedTotalVisitorsTxtField.setPrefWidth(120);
        expectedTotalVisitorsTxtField.setPrefHeight(40);
        rejectedPurchasesTxtField.setStyle("-fx-font: 16 arial;");
        rejectedPurchasesTxtField.setPrefWidth(120);
        rejectedPurchasesTxtField.setPrefHeight(40);
        statusTxtField.setStyle("-fx-font: 16 arial;");
        statusTxtField.setPrefWidth(120);
        statusTxtField.setPrefHeight(40);
        // totalVisitorTxtField.getStyleClass().add("text-field");
        // dailyVisitorLimitTxtField.getStyleClass().add("textfield");
        // currentVisitorTxtField.getStyleClass().add("textfield");
        // hourlyVisitorLimitTxtField.getStyleClass().add("textfield");
        // queuedVisitorTxtField.getStyleClass().add("textfield");
        // expectedTotalVisitorsTxtField.getStyleClass().add("textfield");
        // rejectedPurchasesTxtField.getStyleClass().add("textfield");
        // statusTxtField.getStyleClass().add("textfield");

        Button visitorEnterBtn = new Button("visitorEnterBtn");
        Button visitorExitBtn = new Button("visitorExitBtn");
        Button museumOpenBtn = new Button("museumOpenBtn");
        Button museumClosedBtn = new Button("museumClosedBtn");
        Button museumFullBtn = new Button("museumFullBtn");
        Button rejectedPurchasesBtn = new Button("rejectedPurchasesBtn");

        visitorEnterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (getMuseumStatus() != "CLOSED") {
                    if (getMuseumStatus() == "OPEN") {
                        decreaseQueuedVisitor();
                        increaseCurrentVisitor();
                    } else if (getMuseumStatus() == "FULL") {
                        increaseQueuedVisitor();
                    }
                    if (getHourlyVisitorLimit() <= getCurrentVisitor()) {
                        museumFull();
                    }
                    if (getDailyVisitorLimit() <= getTotalVisitor()) {
                        museumClosed();
                    }
                }
            }
        });
        visitorExitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                decreaseCurrentVisitor();
                currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
                if (getMuseumStatus() == ("FULL")) {
                    museumOpenBtn.fire();
                }
            }
        });
        museumOpenBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                museumOpen();
            }
        });
        museumClosedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                museumClosed();
            }
        });
        museumFullBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                museumFull();
            }
        });
        rejectedPurchasesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                increaseRejectedPurchase();
            }
        });

        museumOpenBtn.fire();

        GridPane museumStatsPane = new GridPane();
        museumStatsPane.setId("pane");
        // welcome.getStyleClass().setAll("border", "border-dark", "p-5");
        museumStatsPane.setMinSize(350, 280);
        museumStatsPane.setPadding(new Insets(10, 10, 10, 10));
        museumStatsPane.setVgap(5);
        museumStatsPane.setAlignment(Pos.CENTER);
        ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setPrefWidth(40.0);
        museumStatsPane.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint, colConstraint,
                colConstraint, colConstraint, colConstraint);
        // museumStatsPane.setBorder(new Border(
        // new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
        // BorderWidths.DEFAULT)));

        museumStatsPane.add(museumStatisticsTxt, 2, 0, 3, 1);
        museumStatsPane.add(queuedVisitorTxtField, 0, 1, 3, 1);
        museumStatsPane.add(queuedVisitorTxt, 0, 2, 3, 1);
        museumStatsPane.add(dailyVisitorLimitTxtField, 4, 1, 3, 1);
        museumStatsPane.add(dailyVisitorLimitTxt, 4, 2, 3, 1);
        museumStatsPane.add(rejectedPurchasesTxtField, 0, 3, 3, 1);
        museumStatsPane.add(rejectedPurchasesTxt, 0, 4, 3, 1);
        museumStatsPane.add(hourlyVisitorLimitTxtField, 4, 3, 3, 1);
        museumStatsPane.add(hourlyVisitorLimitTxt, 4, 4, 3, 1);
        museumStatsPane.add(expectedTotalVisitorsTxtField, 2, 5, 3, 1);
        museumStatsPane.add(expectedTotalVisitorsTxt, 2, 6, 3, 1);
        // museumStatsPane.setGridLinesVisible(true);

        GridPane.setHalignment(museumStatisticsTxt, HPos.CENTER);
        GridPane.setHalignment(queuedVisitorTxtField, HPos.CENTER);
        GridPane.setHalignment(queuedVisitorTxt, HPos.CENTER);
        GridPane.setHalignment(dailyVisitorLimitTxtField, HPos.CENTER);
        GridPane.setHalignment(dailyVisitorLimitTxt, HPos.CENTER);
        GridPane.setHalignment(rejectedPurchasesTxtField, HPos.CENTER);
        GridPane.setHalignment(rejectedPurchasesTxt, HPos.CENTER);
        GridPane.setHalignment(hourlyVisitorLimitTxtField, HPos.CENTER);
        GridPane.setHalignment(hourlyVisitorLimitTxt, HPos.CENTER);
        GridPane.setHalignment(expectedTotalVisitorsTxtField, HPos.CENTER);
        GridPane.setHalignment(expectedTotalVisitorsTxt, HPos.CENTER);

        Circle clock = new Circle();
        clock.setRadius(100.0);
        clock.setFill(Color.DARKCYAN);

        GridPane museumMainPane = new GridPane();
        museumMainPane.setMinSize(350, 150);
        museumMainPane.setId("museumMain");
        museumMainPane.setPadding(new Insets(20, 20, 20, 20));
        museumMainPane.setVgap(5);
        museumMainPane.setHgap(20);
        museumMainPane.setAlignment(Pos.CENTER);

        museumMainPane.add(clock, 0, 0, 6, 6);
        museumMainPane.add(statusTxtField, 7, 0);
        museumMainPane.add(statusTxt, 7, 1);
        museumMainPane.add(totalVisitorTxtField, 7, 2);
        museumMainPane.add(totalVisitorTxt, 7, 3);
        museumMainPane.add(currentVisitorTxtField, 7, 4);
        museumMainPane.add(currentVisitorTxt, 7, 5);

        GridPane.setHalignment(clock, HPos.CENTER);
        GridPane.setHalignment(statusTxtField, HPos.CENTER);
        GridPane.setHalignment(statusTxt, HPos.CENTER);
        GridPane.setHalignment(totalVisitorTxtField, HPos.CENTER);
        GridPane.setHalignment(totalVisitorTxt, HPos.CENTER);
        GridPane.setHalignment(currentVisitorTxtField, HPos.CENTER);
        GridPane.setHalignment(currentVisitorTxt, HPos.CENTER);

        GridPane museumLogPane = new GridPane();
        museumLogPane.setMinSize(400, 350);
        museumLogPane.setId("pane");
        museumLogPane.add(museumClosedBtn, 0, 0);
        museumLogPane.add(museumOpenBtn, 0, 1);
        museumLogPane.add(museumFullBtn, 0, 2);
        museumLogPane.add(visitorEnterBtn, 0, 3);
        museumLogPane.add(visitorExitBtn, 0, 4);
        museumLogPane.add(rejectedPurchasesBtn, 0, 5);

        Text cursorPosition = new Text();
        museumStatsPane.setOnMousePressed(e -> {
            updateMousePosition(e, cursorPosition);
        });
        museumStatsPane.setOnMouseDragged(e -> {
            updateMousePosition(e, cursorPosition);
        });
        museumStatsPane.setOnMouseReleased(e -> cursorPosition.setVisible(true));
        // museumStatsPane.add(cursorPosition, 2, 9);

        VBox vBox = new VBox(museumMainPane, museumStatsPane);
        vBox.setAlignment(Pos.CENTER);

        GridPane gapPane = new GridPane();
        gapPane.setMinSize(20, 20);

        HBox hBox = new HBox(museumLogPane, gapPane, vBox);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox2 = new VBox(hBox);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getStyleClass().add("background");

        // Setting title to the Stage
        stage.setTitle("Ever Given Museum");

        // Creating a scene object
        Scene scene = new Scene(vBox2);
        scene.setFill(Color.WHITESMOKE);

        String cssFile1 = this.getClass().getResource("style.css").toExternalForm();
        // String cssFile2 = this.getClass().getResource("clock.css").toExternalForm();
        scene.getStylesheets().addAll(cssFile1);
        
        // Adding scene to the stage
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(900);

        // Displaying the contents of the stage
        stage.show();
    }

    private void museumOpen() {
        museumStatus = "OPEN";
        statusTxtField.setText(museumStatus);
    }

    private void museumClosed() {
        museumStatus = "CLOSED";
        statusTxtField.setText(museumStatus);
    }

    private void museumFull() {
        museumStatus = "FULL";
        statusTxtField.setText(museumStatus);
    }

    private String getMuseumStatus() {
        return museumStatus;
    }

    private void increaseCurrentVisitor() {
        currentVisitor++;
        totalVisitor++;
        totalVisitorTxtField.setText(String.valueOf(getTotalVisitor()));
        currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
    }

    private void increaseQueuedVisitor() {
        queuedVisitor++;
        queuedVisitorTxtField.setText(String.valueOf(getQueuedVisitor()));
    }

    private void decreaseCurrentVisitor() {
        if (currentVisitor > 0)
            currentVisitor--;
        queuedVisitorTxtField.setText(String.valueOf(getQueuedVisitor()));
    }

    private void decreaseQueuedVisitor() {
        if (queuedVisitor > 0)
            queuedVisitor--;
        queuedVisitorTxtField.setText(String.valueOf(getQueuedVisitor()));
    }

    private int getCurrentVisitor() {
        return currentVisitor;
    }

    private int getQueuedVisitor() {
        return queuedVisitor;
    }

    private int getTotalVisitor() {
        return totalVisitor;
    }

    private void increaseRejectedPurchase() {
        rejectedPurchases++;
        rejectedPurchasesTxtField.setText(String.valueOf(getRejectedPurchases()));
    }

    private int getRejectedPurchases() {
        return rejectedPurchases;
    }

    private int getDailyVisitorLimit() {
        return dailyVisitorLimit;
    }

    private int getHourlyVisitorLimit() {
        return hourlyVisitorLimit;
    }

    private void updateMousePosition(MouseEvent e, Text cursorPosition) {
        cursorPosition.setX(e.getX() + 2);
        cursorPosition.setY(e.getY() - 2);
        cursorPosition.setText("(" + e.getX() + ", " + e.getY() + ")");
        cursorPosition.setVisible(true);
    }

    private String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }

    static String getResource(String path) {
        return Clock.class.getResource(path).toExternalForm();
    }

    // records relative x and y co-ordinates.
    class Delta {
        double x, y;
    }

    public static void main(String args[]) {
        launch(args);
    }
}