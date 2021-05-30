import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private TextField totalVisitorTxtField, currentVisitorTxtField, queuedVisitorTxtField, timeTxtField;
    private int currentVisitor, totalVisitor, quedVisitor = 0;
    private int time = 800;

    @Override
    public void start(Stage stage) {

        // Creating a Grid Pane
        GridPane gridPane = new GridPane();

        // Setting size for the pane
        gridPane.setMinSize(600, 650);

        // Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        Text totalVisitorTxt = new Text("Total Visitors");
        Text currentVisitorTxt = new Text("Current Visitors");
        Text queuedVisitorTxt = new Text("Queued Visitors");
        Text timeTxt = new Text("Time");

        totalVisitorTxtField = new TextField(String.valueOf(totalVisitor));
        totalVisitorTxtField.setStyle("-fx-font: 16 arial;");
        totalVisitorTxtField.setPrefWidth(100);
        totalVisitorTxtField.setPrefHeight(40);

        currentVisitorTxtField = new TextField(String.valueOf(currentVisitor));
        currentVisitorTxtField.setStyle("-fx-font: 16 arial;");
        currentVisitorTxtField.setPrefWidth(100);
        currentVisitorTxtField.setPrefHeight(40);

        queuedVisitorTxtField = new TextField(String.valueOf(quedVisitor));
        queuedVisitorTxtField.setStyle("-fx-font: 16 arial;");
        queuedVisitorTxtField.setPrefWidth(100);
        queuedVisitorTxtField.setPrefHeight(40);

        timeTxtField = new TextField();
        timeTxtField.setStyle("-fx-font: 16 arial;");
        timeTxtField.setPrefWidth(100);
        timeTxtField.setPrefHeight(40);

        Button visitorEnterBtn = new Button("Visitor Enter");
        visitorEnterBtn.setPrefSize(100, 20);
        EventHandler<MouseEvent> visitorEnterListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                increaseCurrentVisitor();
                if (quedVisitor > 0) {
                    decreaseQueuedVisitor();
                    queuedVisitorTxtField.setText(String.valueOf(getQuedVisitor()));
                }
                increaseTime();
                timeTxtField.setText(String.valueOf(getTime()));
                totalVisitorTxtField.setText(String.valueOf(getTotalVisitor()));
                currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
            }
        };
        visitorEnterBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, visitorEnterListener);

        Button visitorExitBtn = new Button("Visitor Exit");
        visitorExitBtn.setPrefSize(100, 20);
        EventHandler<MouseEvent> visitorExitListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (currentVisitor > 0) {
                    decreaseCurrentVisitor();
                    increaseTime();
                    timeTxtField.setText(String.valueOf(getTime()));
                    currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
                }
            }
        };
        visitorExitBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, visitorExitListener);

        Button queueVisitorBtn = new Button("Queue Visitor");
        queueVisitorBtn.setPrefSize(100, 20);
        // EventHandler<MouseEvent> queueVisitorListener = new
        // EventHandler<MouseEvent>() {
        // @Override
        // public void handle(MouseEvent e) {
        // increaseQuedVisitor();
        // queuedVisitorTxtField.setText(String.valueOf(getQuedVisitor()));
        // }
        // };
        // queueVisitorBtn.addEventFilter(MouseEvent.MOUSE_CLICKED,
        // queueVisitorListener);

        queueVisitorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                increaseQuedVisitor();
                queuedVisitorTxtField.setText(String.valueOf(getQuedVisitor()));
            }
        });

        Button startBtn = new Button("Start");
        startBtn.setPrefSize(100, 20);

        // EventHandler<MouseEvent> startListener = new EventHandler<MouseEvent>() {
        // @Override
        // public void handle(MouseEvent e) {
        // timeTxtField.setText(String.valueOf(getTime()));
        // increaseTime();
        // }
        // };
        // startBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, startListener);

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                timeTxtField.setText(String.valueOf(getTime()));
                increaseTime();
            }
        });

        startBtn.fire();
        queueVisitorBtn.fire();

        Text txtPosition = new Text();
        gridPane.add(totalVisitorTxtField, 0, 0, 2, 1);
        gridPane.add(currentVisitorTxtField, 2, 0, 2, 1);
        gridPane.add(queuedVisitorTxtField, 4, 0, 2, 1);
        gridPane.add(timeTxtField, 6, 0, 2, 1);
        gridPane.add(totalVisitorTxt, 0, 1, 2, 1);
        gridPane.add(currentVisitorTxt, 2, 1, 2, 1);
        gridPane.add(queuedVisitorTxt, 4, 1, 2, 1);
        gridPane.add(timeTxt, 6, 1, 2, 1);
        gridPane.add(visitorEnterBtn, 0, 3, 2, 1);
        gridPane.add(visitorExitBtn, 2, 3, 2, 1);
        gridPane.add(queueVisitorBtn, 4, 3, 2, 1);
        gridPane.add(startBtn, 6, 3, 2, 1);
        // gridPane.setGridLinesVisible(true);

        GridPane.setHalignment(totalVisitorTxt, HPos.CENTER);
        GridPane.setHalignment(currentVisitorTxt, HPos.CENTER);
        GridPane.setHalignment(queuedVisitorTxt, HPos.CENTER);
        GridPane.setHalignment(timeTxt, HPos.CENTER);
        GridPane.setHalignment(visitorEnterBtn, HPos.CENTER);
        GridPane.setHalignment(visitorExitBtn, HPos.CENTER);
        GridPane.setHalignment(queueVisitorBtn, HPos.CENTER);
        GridPane.setHalignment(startBtn, HPos.CENTER);

        // Museum
        Rectangle museumArea = new Rectangle(200.0, 200.0, 400.0, 350.0);
        Rectangle westExitRectangle = new Rectangle(100.0, 200.0, 100, 350);
        Rectangle eastExitRectangle = new Rectangle(550.0, 200.0, 100, 350);
        Rectangle northEntryRectangle = new Rectangle(200.0, 100.0, 350, 100);
        Rectangle southEntryRectangle = new Rectangle(200.0, 550.0, 350, 100);
        Rectangle westExitWhiteRectangle = new Rectangle(0.0, 200.0, 100, 350);
        Rectangle eastExitWhiteRectangle = new Rectangle(650.0, 200.0, 100, 350);
        Rectangle northEntryWhiteRectangle = new Rectangle(200.0, 0.0, 350, 100);
        Rectangle southEntryWhiteRectangle = new Rectangle(200.0, 650.0, 350, 100);

        museumArea.setFill(Color.LIGHTBLUE);
        westExitRectangle.setFill(Color.LIGHTCYAN);
        eastExitRectangle.setFill(Color.LIGHTCYAN);
        northEntryRectangle.setFill(Color.LIGHTCYAN);
        southEntryRectangle.setFill(Color.LIGHTCYAN);
        westExitWhiteRectangle.setFill(Color.WHITESMOKE);
        eastExitWhiteRectangle.setFill(Color.WHITESMOKE);
        northEntryWhiteRectangle.setFill(Color.WHITESMOKE);
        southEntryWhiteRectangle.setFill(Color.WHITESMOKE);

        Circle ticket = new Circle(10, Color.GREENYELLOW);

        // Group group = new Group(museumArea, westExitRectangle, eastExitRectangle,
        // northEntryRectangle,
        // southEntryRectangle, gridPane);
        // Group group = new Group(gridPane);

        HBox hbox1 = new HBox(new Group(museumArea, westExitRectangle, eastExitRectangle, northEntryRectangle,
                southEntryRectangle, westExitWhiteRectangle, eastExitWhiteRectangle, northEntryWhiteRectangle,
                southEntryWhiteRectangle));
        hbox1.setAlignment(Pos.CENTER);

        hbox1.setOnMousePressed(e -> {
            updateMousePosition(e, txtPosition);
        });
        hbox1.setOnMouseDragged(e -> {
            updateMousePosition(e, txtPosition);
        });
        hbox1.setOnMouseReleased(e -> txtPosition.setVisible(true));

        gridPane.add(txtPosition, 2, 4, 1, 1);

        HBox hbox2 = new HBox(gridPane, txtPosition);
        hbox2.setAlignment(Pos.CENTER);

        HBox hbox3 = new HBox(hbox1, hbox2);
        hbox3.setAlignment(Pos.CENTER);

        // Setting title to the Stage
        stage.setTitle("Ever Given Museum");

        // Creating a scene object
        Scene scene = new Scene(hbox3);
        scene.setFill(Color.WHITESMOKE);

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }

    private void updateMousePosition(MouseEvent e, Text txtPosition) {
        txtPosition.setX(e.getX() + 2);
        txtPosition.setY(e.getY() - 2);
        txtPosition.setText("(" + e.getX() + ", " + e.getY() + ")");
        txtPosition.setVisible(true);
    }

    private void increaseCurrentVisitor() {
        currentVisitor++;
        totalVisitor++;
    }

    private void increaseQuedVisitor() {
        quedVisitor++;
    }

    private void decreaseCurrentVisitor() {
        currentVisitor--;
    }

    private void decreaseQueuedVisitor() {
        quedVisitor--;
    }

    private void increaseTime() {
        time++;
    }

    private int getCurrentVisitor() {
        return currentVisitor;
    }

    private int getQuedVisitor() {
        return quedVisitor;
    }

    private int getTotalVisitor() {
        return totalVisitor;
    }

    private int getTime() {
        return time;
    }

    public static void main(String args[]) {
        launch(args);
    }
}