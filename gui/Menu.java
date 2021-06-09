
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {

    private int selectID;

    @Override
    public void start(Stage stage) {
        String instruction = "This is a JavaFX application for our team (Ever Given)'s submission for Concurrent Programming [WIF3003] Group Project (Phase 2) titled 'Museum Under Pandemic' in Semester 2, 2020/2021 session."
                + "The purpose of this assignment is to assess the students’ ability to apply adequate programming skills and use appropriate constructs in the Java language to solve a concurrent problem. "
                + "\n\nTeam Members:\n1. Muhammad Rahiman bin Abdulmanab\n2. Nur Faidz Hazirah binti Nor'Azman\n3. Muhammad Luqman bin Sulaiman\n4. Muhammad Farouq bin Shaharuddin"
                + "\n\nPlease select a test case before running the program:";

        TextArea intro = new TextArea(instruction);
        intro.setWrapText(true);
        intro.setEditable(false);
        intro.setId("intro");
        intro.getStyleClass().setAll("text-area");
        intro.setPrefHeight(500);

        // Select option
        // VBox message = new VBox(5, intro);
        ComboBox<String> opt = new ComboBox<>();
        opt.setId("opt");
        opt.getItems().addAll("Test Case 1", "Test Case 2", "Test Case 3", "Test Case 4", "Test Case 5");
        opt.setValue("Select");
        opt.setOnAction((event) -> {
            setID(opt.getSelectionModel().getSelectedIndex());
            int selectedIndex = opt.getSelectionModel().getSelectedIndex();
            Object selectedItem = opt.getSelectionModel().getSelectedItem();
            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("ComboBox.getValue(): " + opt.getValue());
        });
        opt.getStyleClass().setAll("combo-box");

        // Start Button
        Button start = new Button("Start");
        start.setId("start");
        HBox line1 = new HBox(20, opt, start);
        line1.setAlignment(Pos.CENTER);
        start.getStyleClass().setAll("button", "primary");
        start.setStyle("-fx-font-size:18;");

        Text label1 = new Text("Ever Given - Museum Under Pandemic");
        label1.getStyleClass().setAll("lg");
        HBox top = new HBox(20, label1);
        top.setAlignment(Pos.CENTER);

        // Creating a Grid Pane for Scene 1
        GridPane gridPane = new GridPane();
        // gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        // Arranging all the nodes in the grid
        gridPane.add(top, 0, 0);
        gridPane.add(intro, 0, 1);
        gridPane.add(line1, 0, 4);
        gridPane.setId("gridPane");

        Scene scene = new Scene(gridPane, 600, 450);
        scene.getStylesheets().add("bootstrap.css");

        GUI gui = new GUI();

        // Setting title to the Stage
        stage.setTitle("Ever Given Museum");
        start.setOnAction(e -> {
            try {
                stage.setScene(gui.getScene());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Adding scene to the stage
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setID(int x) {
        this.selectID = x;
    }

    public int getID() {
        return selectID;
    }

}
