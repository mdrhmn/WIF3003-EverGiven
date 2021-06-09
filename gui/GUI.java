import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.stage.*;


public class GUI extends Application {

    static Scene scene;
    @Override
    public void start(Stage stage) throws Exception {

        Stats statsLayout = new Stats();
        Log logLayout = new Log();
        HBox hBox = new HBox(logLayout.getLayout(), statsLayout.getLayout());
        
        // Creating a scene object
        // Scene scene = new Scene(hBox);
        scene = new Scene(hBox);
        scene.setFill(Color.WHITESMOKE);

        String cssFile1 = this.getClass().getResource("stats.css").toExternalForm();
        String cssFile2 = this.getClass().getResource("clock.css").toExternalForm();
        String cssFile3 = this.getClass().getResource("bootstrap.css").toExternalForm();
        scene.getStylesheets().addAll(cssFile1, cssFile2, cssFile3);

        // Adding scene to the stage
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(1000);
        // Displaying the contents of the stage
        // stage.show();
    }

    public Scene getScene() throws Exception {
        start(new Stage());
        return scene;
    }

}