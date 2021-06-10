/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.stage.*;


public class Log extends Application {
    GridPane museumLogPane;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Log.fxml"));
        museumLogPane = new GridPane();
        museumLogPane.setMinSize(400, 350);
        museumLogPane.setId("pane");
        // museumLogPane.getChildren().add(root);
        museumLogPane.add(root,0,0);
    }

    public GridPane getLayout() throws Exception {
        start(new Stage());
        return museumLogPane;
    }
}
