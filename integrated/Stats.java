/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.event.*;

public class Stats extends Application {

    static VBox statsLayout;
    private int totalVisitor, currentVisitor, queuedVisitor, rejectedPurchases = 0;
    private int dailyVisitorLimit = 500, hourlyVisitorLimit = 100, expectedTotalVisitors;
    private String museumStatus = "";
    private TextField totalVisitorTxtField, dailyVisitorLimitTxtField, currentVisitorTxtField,
            hourlyVisitorLimitTxtField, queuedVisitorTxtField, expectedTotalVisitorsTxtField, rejectedPurchasesTxtField,
            statusTxtField;
    public static Button visitorEnterBtn;
    public static Button visitorExitBtn;
    public static Button museumOpenBtn;
    public static Button museumClosedBtn;
    public static Button museumFullBtn;
    public static Button rejectedPurchasesBtn;
    public static Button queuedVisitorBtn;

    @Override
    public void start(Stage stage) throws Exception {

        // Museum museum;
        // dailyVisitorLimit = Museum.getDailyVisitorsLimit();
        // hourlyVisitorLimit = Museum.getIntCurrentVisitorsLimit();
        // expectedTotalVisitors;

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

        totalVisitorTxtField.setEditable(false);
        dailyVisitorLimitTxtField.setEditable(false);
        currentVisitorTxtField.setEditable(false);
        hourlyVisitorLimitTxtField.setEditable(false);
        queuedVisitorTxtField.setEditable(false);
        expectedTotalVisitorsTxtField.setEditable(false);
        rejectedPurchasesTxtField.setEditable(false);
        statusTxtField.setEditable(false);

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

        visitorEnterBtn = new Button("visitorEnterBtn");
        visitorExitBtn = new Button("visitorExitBtn");
        museumOpenBtn = new Button("museumOpenBtn");
        museumClosedBtn = new Button("museumClosedBtn");
        museumFullBtn = new Button("museumFullBtn");
        rejectedPurchasesBtn = new Button("rejectedPurchasesBtn");
        // queuedVisitorBtn = new Button("queuedVisitorBtn");

        // queuedVisitorBtn.setOnAction(new EventHandler<ActionEvent>() {
        //     @Override
        //     public void handle(ActionEvent e) {
        //         if (getMuseumStatus() != "CLOSED") {
        //             if (getMuseumStatus() == "OPEN") {
        //                 decreaseQueuedVisitor();
        //                 increaseCurrentVisitor();
        //             } else if (getMuseumStatus() == "FULL") {
        //                 increaseQueuedVisitor();
        //             }
        //             if (getHourlyVisitorLimit() <= getCurrentVisitor()) {
        //                 museumFull();
        //             }
        //             if (getDailyVisitorLimit() <= getTotalVisitor()) {
        //                 museumClosed();
        //             }
        //         }
        //     }
        // });
        visitorEnterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (getMuseumStatus() != "CLOSED") {
                    if (getMuseumStatus() == "OPEN") {
                        // decreaseQueuedVisitor();
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
                } else{
                    increaseQueuedVisitor();
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
        museumStatsPane.setVgap(10);
        museumStatsPane.setAlignment(Pos.CENTER);
        ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setPrefWidth(40.0);
        museumStatsPane.getColumnConstraints().addAll(colConstraint, colConstraint, colConstraint, colConstraint,
                colConstraint, colConstraint, colConstraint);

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

        Clock clockLayout = new Clock();
        VBox clockVBox = new VBox(clockLayout.getLayout());
        VBox statusVBox = new VBox(statusTxtField, statusTxt);
        VBox totalVisitorVBox = new VBox(totalVisitorTxtField, totalVisitorTxt);
        VBox currentVisitorVBox = new VBox(currentVisitorTxtField, currentVisitorTxt);
        VBox mainStatsVBox = new VBox(statusVBox, totalVisitorVBox, currentVisitorVBox);
        HBox mainPaneHBox = new HBox(clockVBox, mainStatsVBox);

        clockVBox.setAlignment(Pos.CENTER);
        statusVBox.setAlignment(Pos.CENTER);
        totalVisitorVBox.setAlignment(Pos.CENTER);
        currentVisitorVBox.setAlignment(Pos.CENTER);
        mainStatsVBox.setAlignment(Pos.CENTER);
        mainPaneHBox.setAlignment(Pos.CENTER);

        mainPaneHBox.setPadding(new Insets(15, 12, 15, 12));

        statusVBox.setSpacing(5);
        totalVisitorVBox.setSpacing(5);
        currentVisitorVBox.setSpacing(5);
        mainStatsVBox.setSpacing(15);
        mainPaneHBox.setSpacing(50);

        // GridPane.setHalignment(clock, HPos.CENTER);
        GridPane.setHalignment(statusTxtField, HPos.CENTER);
        GridPane.setHalignment(statusTxt, HPos.CENTER);
        GridPane.setHalignment(totalVisitorTxtField, HPos.CENTER);
        GridPane.setHalignment(totalVisitorTxt, HPos.CENTER);
        GridPane.setHalignment(currentVisitorTxtField, HPos.CENTER);
        GridPane.setHalignment(currentVisitorTxt, HPos.CENTER);

        Text cursorPosition = new Text();
        museumStatsPane.setOnMousePressed(e -> {
            updateMousePosition(e, cursorPosition);
        });
        museumStatsPane.setOnMouseDragged(e -> {
            updateMousePosition(e, cursorPosition);
        });
        museumStatsPane.setOnMouseReleased(e -> cursorPosition.setVisible(true));
        // museumStatsPane.add(cursorPosition, 2, 9);

        VBox vBox = new VBox(mainPaneHBox, museumStatsPane);
        vBox.setAlignment(Pos.CENTER);

        GridPane gapPane = new GridPane();
        gapPane.setMinSize(20, 20);

        HBox hBox = new HBox(gapPane, vBox);
        hBox.setAlignment(Pos.CENTER);

        statsLayout = new VBox(hBox);
        statsLayout.setAlignment(Pos.CENTER);
        statsLayout.getStyleClass().add("background");

        // Setting title to the Stage
        stage.setTitle("Ever Given Museum");

        // Creating a scene object
        Scene scene = new Scene(statsLayout);
        scene.setFill(Color.WHITESMOKE);

        String cssFile1 = this.getClass().getResource("bootstrap.css").toExternalForm();
        scene.getStylesheets().addAll(cssFile1);

        // Adding scene to the stage
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(1000);

        // Displaying the contents of the stage
        // stage.show();
    }

    public VBox getLayout() throws Exception {
        start(new Stage());
        return statsLayout;
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

    // records relative x and y co-ordinates.
    class Delta {
        double x, y;
    }
}