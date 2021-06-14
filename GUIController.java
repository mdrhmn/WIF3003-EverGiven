
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Jun21
 */
public class GUIController implements Initializable {

    @FXML
    private TextField setDailyVisitorLimitTxtField;
    @FXML
    private TextField setHourlyVisitorLimitTxtField;
    @FXML
    private ComboBox<String> cb;
    @FXML
    private Button sb;
    @FXML
    private TextArea console;
    @FXML
    private VBox museumTime;
    @FXML
    private Text timerTxtField;
    @FXML
    private TextField statusTxtField;
    @FXML
    private TextField totalVisitorTxtField;
    @FXML
    private TextField currentVisitorTxtField;
    @FXML
    private TextField queuedVisitorTxtField;
    @FXML
    private TextField rejectedPurchasesTxtField;
    @FXML
    private TextField NET1;
    @FXML
    private TextField NET3;
    @FXML
    private TextField NET4;
    @FXML
    private TextField NET2;
    @FXML
    private TextField SET1;
    @FXML
    private TextField SET3;
    @FXML
    private TextField SET4;
    @FXML
    private TextField SET2;
    @FXML
    private TextField WET1;
    @FXML
    private TextField WET3;
    @FXML
    private TextField WET4;
    @FXML
    private TextField WET2;
    @FXML
    private TextField EET1;
    @FXML
    private TextField EET3;
    @FXML
    private TextField EET4;
    @FXML
    private TextField EET2;

    public int totalVisitor, currentVisitor, queuedVisitor, rejectedPurchases = 0;
    public int dailyVisitorLimit = 500, hourlyVisitorLimit = 100;
    public String museumStatus = "CLOSED";
    public PrintStream ps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb.getItems().addAll("Test Case 1", "Test Case 2", "Test Case 3", "Test Case 4", "Test Case 5");
        ps = new PrintStream(new GUIController.Console(console));

        try {
            museumOperation("TestCaseWithinDailyLimit.txt");
        } catch (IOException | InterruptedException e) {

        }
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

    public void setDailyVisitorLimit(int limit) {
        dailyVisitorLimit = limit;
        setDailyVisitorLimitTxtField.setText(Integer.toString(limit));
    }

    public void setHourlyVisitorLimit(int limit) {
        hourlyVisitorLimit = limit;
        setHourlyVisitorLimitTxtField.setText(Integer.toString(limit));
    }

    public void museumOpen() {
        museumStatus = "OPEN";
        statusTxtField.setText("OPEN");
    }

    public void museumClosed() {
        museumStatus = "CLOSED";
        statusTxtField.setText("CLOSED");
    }

    public void museumFull() {
        museumStatus = "FULL";
        statusTxtField.setText("FULL");
    }

    private String getMuseumStatus() {
        return museumStatus;
    }

    private void increaseCurrentVisitor() {
        currentVisitor++;
        totalVisitor++;
        currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
        totalVisitorTxtField.setText(String.valueOf(getTotalVisitor()));
    }

    public void increaseQueuedVisitor() {
        queuedVisitor++;
        queuedVisitorTxtField.setText(String.valueOf(getQueuedVisitor()));
    }

    private void decreaseCurrentVisitor() {
        if (currentVisitor > 0) {
            currentVisitor--;
        }
        currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
    }

    private void decreaseQueuedVisitor() {
        if (queuedVisitor > 0) {
            queuedVisitor--;
        }
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

    public void increaseRejectedPurchase() {
        rejectedPurchases++;
        rejectedPurchasesTxtField.setText(String.valueOf(getRejectedPurchases()));
    }

    private int getRejectedPurchases() {
        return rejectedPurchases;
    }

    private int getDailyVisitorLimit() {
        return dailyVisitorLimit;
    }

    public int getHourlyVisitorLimit() {
        return hourlyVisitorLimit;
    }

    public void visitorEnter() {
        if (getMuseumStatus() != "CLOSED") {
            if (getMuseumStatus() == "OPEN") {
                decreaseQueuedVisitor();
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
        } else {
            increaseQueuedVisitor();
        }
    }

    public void visitorExit() {
        decreaseCurrentVisitor();
        // currentVisitor.setText(String.valueOf(getCurrentVisitor()));
        museumOpen();
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

        Museum museum = new Museum("Kuching Museum", 100, 500, this);
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
