/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.stage.*;

public class GUI extends Application {

    private String filename;
    static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Stats statsLayout = new Stats();
        Log logLayout = new Log();
        // HBox hBox = new HBox(logLayout.getLayout(), statsLayout.getLayout());
        HBox hBox = new HBox(statsLayout.getLayout());


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

    public static void museumOperation(String filename) throws InterruptedException {
        // Initialise Reader object and Thread for Reader object
        TicketingSystem read = new TicketingSystem(filename);
        Thread t = new Thread(read);

        // Start TicketingSystem thread
        t.start();
        t.join();

        // Create Visitors and Museum
        ArrayList<String[]> readList = read.getVisitorList();
        Museum museum = new Museum("Kuching Museum", 100, 500);
        ArrayList<Visitor> visitorList = new ArrayList<>();

        for (int i = 0; i < readList.size(); i++) {
            // String visitorID, int noOfTickets, Museum museum
            String visitorID = readList.get(i)[0];
            int noOfTickets = Integer.parseInt(readList.get(i)[1]);

            Visitor visitor = new Visitor(visitorID, noOfTickets, museum);
            visitorList.add(visitor);
        }

        // Create Visitor thread
        for (int i = 0; i < visitorList.size(); i++) {
            Runnable visitor = visitorList.get(i);
            Thread visitorThread = new Thread(visitor);
            visitorThread.start();
        }
    }

}