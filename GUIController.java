
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.scene.layout.VBox;
import java.util.logging.Level;
import javafx.scene.text.Text;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import javafx.fxml.FXML;
import java.net.URL;

public class GUIController implements Initializable {

    @FXML
    public TextField dailyVisitorLimitTxtField;
    @FXML
    public TextField hourlyVisitorLimitTxtField;
    @FXML
    public ComboBox<String> cb;
    @FXML
    public Button sb;
    @FXML
    public TextArea console;
    @FXML
    public VBox museumTime;
    @FXML
    public Text timerTxtField;
    @FXML
    public TextField statusTxtField;
    @FXML
    public TextField totalVisitorTxtField;
    @FXML
    public TextField currentVisitorTxtField;
    @FXML
    public TextField queuedVisitorTxtField;
    @FXML
    public TextField rejectedPurchasesTxtField;
    @FXML
    public TextField NET1;
    @FXML
    public TextField NET3;
    @FXML
    public TextField NET4;
    @FXML
    public TextField NET2;
    @FXML
    public TextField SET1;
    @FXML
    public TextField SET3;
    @FXML
    public TextField SET4;
    @FXML
    public TextField SET2;
    @FXML
    public TextField WET1;
    @FXML
    public TextField WET3;
    @FXML
    public TextField WET4;
    @FXML
    public TextField WET2;
    @FXML
    public TextField EET1;
    @FXML
    public TextField EET3;
    @FXML
    public TextField EET4;
    @FXML
    public TextField EET2;

    public int totalVisitor, currentVisitor, queuedVisitor, rejectedPurchases = 0;
    public int dailyVisitorLimit, hourlyVisitorLimit;
    public String museumStatus = "TICKET CLOSED";
    public PrintStream ps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb.getItems().addAll("Test Case 1", "Test Case 2", "Test Case 3", "Test Case 4", "Test Case 5");
        ps = new PrintStream(new GUIController.Console(console));

        sb.setOnAction(e -> {
            try {
                btnStart();
            } catch (InterruptedException ex) {
                Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void displayText(String text) {
        ps.println(text);
    }

    public class Console extends OutputStream {

        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
            console.setEditable(false);
            console.setWrapText(true);
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
        }
    }

    public void btnStart() throws InterruptedException, IOException {

        if (cb.getSelectionModel().getSelectedIndex() > -1) {
            switch (cb.getSelectionModel().getSelectedIndex() + 1) {
                case 1:
                    museumOperation("TestCaseWithinDailyLimit_813.txt");
                    break;
                case 2:
                    museumOperation("TestCaseExceedDailyLimit.txt");
                    break;
                case 3:
                    museumOperation("TestCaseWithinCurrentLimit.txt");
                    break;
                case 4:
                    museumOperation("TestCaseExceedCurrentLimit.txt");
                    break;
                case 5:
                    displayText("Test Case 5");
                    break;
            }
            sb.setDisable(true);
        }
    }

    public void ticketOpen() {
        museumStatus = "TICKET OPEN";
        statusTxtField.setText(museumStatus);
    }

    public void ticketClosed() {
        museumStatus = "TICKET CLOSED";
        statusTxtField.setText(museumStatus);
    }

    public void museumOpen() {
        museumStatus = "MUSEUM OPEN";
        statusTxtField.setText(museumStatus);
    }

    public void museumClosed() {
        museumStatus = "MUSEUM CLOSED";
        statusTxtField.setText(museumStatus);
    }

    public void museumFull() {
        museumStatus = "MUSEUM FULL";
        statusTxtField.setText(museumStatus);
    }

    private String getMuseumStatus() {
        return museumStatus;
    }

    public void setTime(String time) {
        timerTxtField.setText(time);
    }

    private void displayTotalVisitor() {
        totalVisitorTxtField.setText(String.valueOf(getTotalVisitor()));
    }

    private void displayCurrentVisitor() {
        currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
    }

    private void displayQueuedVisitor() {
        queuedVisitorTxtField.setText(String.valueOf(getQueuedVisitor()));
    }

    private void displayRejectedPurchases() {
        rejectedPurchasesTxtField.setText(String.valueOf(getRejectedPurchases()));
    }

    private void increaseCurrentVisitor() {
        currentVisitor++;
        totalVisitor++;
        displayCurrentVisitor();
        displayTotalVisitor();
    }

    public void increaseQueuedVisitor() {
        queuedVisitor++;
        displayQueuedVisitor();
    }

    private void decreaseCurrentVisitor() {
        if (currentVisitor > 0) {
            currentVisitor--;
        }
        displayCurrentVisitor();
    }

    private void decreaseQueuedVisitor() {
        if (queuedVisitor > 0) {
            queuedVisitor--;
        }
        displayQueuedVisitor();
    }

    public void increaseRejectedPurchase() {
        rejectedPurchases++;
        displayRejectedPurchases();
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

    private int getRejectedPurchases() {
        return rejectedPurchases;
    }

    // private int getDailyVisitorLimit() {
    //     return dailyVisitorLimit;
    // }

    // public int getHourlyVisitorLimit() {
    //     return hourlyVisitorLimit;
    // }

    public void visitorEnter() {
        decreaseQueuedVisitor();
        increaseCurrentVisitor();
    }

    public void visitorExit() {
        decreaseCurrentVisitor();
        if (getMuseumStatus().equals("MUSEUM FULL")) {
            museumOpen();
        }
    }

    public void museumOperation(String filename) throws InterruptedException, IOException {
        System.out.println("\nRUNNING TEST CASE " + filename + ":");

        // Initialise Reader object and Thread for Reader object
        TicketingSystem read = new TicketingSystem(filename);
        Thread t = new Thread(read);

        // Start TicketingSystem thread
        t.start();
        t.join();

        // Create Visitors and Museum
        ArrayList<String[]> readList = read.getVisitorList();

        Museum museum = new Museum("Kuching Museum", 100, 900, this);
        ArrayList<Visitor> visitorList = new ArrayList<>();

        for (int i = 0; i < readList.size(); i++) {

            // String visitorID, int noOfTickets, Museum museum
            String visitorID = readList.get(i)[0];
            int noOfTickets = Integer.parseInt(readList.get(i)[1]);

            Visitor visitor = new Visitor(visitorID, noOfTickets, museum);
            visitorList.add(visitor);
        }

        // Timer timer = new Timer(museum, this);
        // Thread timerThread = new Thread(timer);
        // timerThread.start();

        // Create Visitor thread
        for (int i = 0; i < visitorList.size(); i++) {
            Runnable visitor = visitorList.get(i);
            Thread visitorThread = new Thread(visitor);
            visitorThread.start();
        }
    }

}
