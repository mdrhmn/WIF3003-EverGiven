
import javafx.scene.control.Button;
import javafx.geometry.Insets;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Faidz Hazirah
 */
public class DesignB extends Application {

    @Override
    public void start(Stage stage) {

        String instruction = "Lorem ipsum dolor sit amet, consectetur adipiscing elit Lorem ipsum dolor sit amet, consectetur adipiscing elit";

        BorderPane bPane = new BorderPane();

//        bPane.setTop(new Label(instruction));
//        Label welcome = new Label(instruction);
//        Label ins = new Label(instruction);
        
        TextArea ins = new TextArea(instruction);
        TextArea welcome = new TextArea(instruction);
        ins.setWrapText(true);
        ins.setEditable(false);
        welcome.setWrapText(true);
        welcome.setEditable(false);
        
        ins.setId("ins");
        welcome.setId("welcome");
        
        VBox message = new VBox(10, welcome, ins);

        Button case1 = new Button("Case 1");
        case1.setId("case1");
//        case1.setOnAction(e -> message.setText("Hello World!"));
        Button case2 = new Button("Case 2");
        case2.setId("case2");
//        case2.setOnAction(e -> message.setText("Goodbye!!"));
        Button case3 = new Button("Case 3");
        case3.setId("case3");
//        case3.setOnAction(e -> Platform.exit());
        Button case4 = new Button("Case 4");
        case4.setId("case4");
        Button case5 = new Button("Case 5");
        case5.setId("case5");
        Button start = new Button("Start");
        start.setId("start");

        HBox line1 = new HBox(20, case1, case2, case3);
        HBox line2 = new HBox(20, case3, case4);
        VBox last = new VBox(10, case5, start);
        line1.setAlignment(Pos.CENTER);
        line2.setAlignment(Pos.CENTER);
        last.setAlignment(Pos.CENTER);



//        BorderPane root = new BorderPane();
//        root.setCenter(message);
//        root.setBottom(buttonBar);
        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();
//        gridPane.setMinSize(400, 200);

        //Setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
//        gridPane.add(message, 0, 0);
        gridPane.add(message, 0, 0);
        gridPane.add(line1, 0, 4);
        gridPane.add(line2, 0, 6);
        gridPane.add(last, 0, 8);

        //Creating a Group object  
        Scene scene = new Scene(gridPane, 500, 350);
//        scene.getStylesheets().add("/CSS/style.css").toExternalForm();
//        getClass().getClassLoader().getResource("style.css").toExternalForm();
        scene.getStylesheets().add("style.css");

        //Setting title to the Stage 
        stage.setTitle("Scene 1 Design B");

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

}
