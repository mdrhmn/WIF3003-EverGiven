
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DesignA extends Application {

    Scene scene1, scene2;
    private int selectID;

    @Override
    public void start(Stage stage) {
        String instruction = "Lorem ipsum dolor sit amet, consectetur adipiscing elit Lorem ipsum dolor sit amet, consectetur adipiscing elit";

        TextArea intro = new TextArea(instruction);
        intro.setWrapText(true);
        intro.setEditable(false);
        intro.setId("intro");
        intro.getStyleClass().setAll("border", "shadow");

        // Select option
        VBox message = new VBox(5, intro);
        ComboBox opt = new ComboBox();
        opt.setId("opt");
        opt.getItems().addAll("Test Case 1", "Test Case 2", "Test Case 3", "Test Case 4", "Test Case 5");
        opt.setValue("Select");
        opt.setOnAction((event) -> {
            setID(opt.getSelectionModel().getSelectedIndex());
            int selectedIndex = opt.getSelectionModel().getSelectedIndex();
            Object selectedItem = opt.getSelectionModel().getSelectedItem();
            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("   ComboBox.getValue(): " + opt.getValue());
        });
        
//        opt.getStyleClass().setAll("combo-box");

        TextArea m2 = new TextArea();
        m2.setWrapText(true);
        m2.setEditable(false);
        m2.setId("welcome");

        //Start Button
        Button start = new Button("Start");
        start.setId("start");
//        start.getStyleClass().setAll("button", "danger", "p-5");
        HBox line1 = new HBox(20, opt, start);
        line1.setAlignment(Pos.CENTER);

        //Scene 2
        Label label1 = new Label("Scene 2");
        label1.setAlignment(Pos.CENTER);
        VBox l2 = new VBox(20);
        l2.getChildren().addAll(label1, m2);

        //Creating a Grid Pane for Scene 1
        GridPane gridPane = new GridPane();
//        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        gridPane.add(intro, 0, 0);
        gridPane.add(line1, 0, 4);
        gridPane.setId("gridPane");

        //Creating a Group object  
        Scene scene = new Scene(gridPane, 500, 350);
        scene2 = new Scene(l2, 500, 350);
//        scene.getStylesheets().add("/CSS/style.css").toExternalForm();
//        getClass().getClassLoader().getResource("style.css").toExternalForm();
        scene.getStylesheets().add("style.css");
        //Setting title to the Stage 
        stage.setTitle("Scene 1 Design A");
        start.setOnAction(e -> {
            stage.setScene(scene2);
            m2.setText("Test case id: " + getID());
        });

        //Adding scene to the stage 
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
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
