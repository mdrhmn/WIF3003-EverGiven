/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Museum;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LogController {

    @FXML
    private TextArea console;
    private PrintStream ps;

    public void initialize() {
        ps = new PrintStream(new Console(console));
    }

    public void button(ActionEvent event) {
        System.setOut(ps);
        System.setErr(ps);
        System.out.println("Hello World");
    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
            console.setEditable(false);
            console.setWrapText(true);
            // console.setPrefHeight(500);
            // console.setPrefWidth(500);
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
        }
    }
}
