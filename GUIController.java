
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.scene.layout.VBox;
import java.util.logging.Level;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import java.net.URL;

/**
 * Controller class to handle all GUI update operations
 */
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
    @FXML
    public TextArea ticketsSoldTxtField;
    @FXML
    public TextArea ticketsEntryTxtField;
    @FXML
    public TextArea ticketsExitTxtField;
    @FXML
    public ListView<String> ticketsQueueListView;

    public int net1, net2, net3, net4, set1, set2, set3, set4, wet1, wet2, wet3, wet4, eet1, eet2, eet3, eet4;
    public int totalVisitor, currentVisitor, queuedVisitor, rejectedPurchases = 0;
    public int dailyVisitorLimit, hourlyVisitorLimit;
    public String museumStatus = "MUSEUM CLOSED";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb.getItems().addAll("Test Case 1", "Test Case 2", "Test Case 3", "Test Case 4", "Test Case 5");
        statusTxtField.setStyle("-fx-text-fill: red;");

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

    /**
     * Append sold tickets
     */
    public void appendTicketsSold(String text) {
        ticketsSoldTxtField.appendText(text + "\n\n");
    }

    /**
     * Append entered tickets
     */
    public void appendTicketsEntry(String text) {
        ticketsEntryTxtField.appendText(text + "\n\n");
    }

    /**
     * Append exited tickets
     */
    public void appendTicketsExit(String text) {
        ticketsExitTxtField.appendText(text + "\n\n");
    }

    /**
     * Add queued tickets into queueList
     */
    public void queueList(String visitor) {
        ticketsQueueListView.getItems().add(visitor);
    }

    /**
     * Remove queued tickets from queueList
     */
    public void dequeueList(String visitor) {
        ticketsQueueListView.getItems().remove(visitor);
    }

    /**
     * Start button to start the program
     */
    public void btnStart() throws InterruptedException, IOException {

        /**
         * Select dropdown to select the test cases
         */
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
                    // displayText("Test Case 5");
                    break;
            }
            sb.setDisable(true);
        }
    }

    /**
     * Change ticket purchase status to open
     */
    public void ticketOpen() {
        museumStatus = "TICKET OPEN";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: #0275d8;");
    }

    /**
     * Change ticket purchase status to closed
     */
    public void ticketClosed() {
        museumStatus = "TICKET CLOSED";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: #f0ad4e;");
    }

    /**
     * Change museum status to open
     */
    public void museumOpen() {
        museumStatus = "MUSEUM OPEN";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: green;");
    }

    /**
     * Change museum status to closed
     */
    public void museumClosed() {
        museumStatus = "MUSEUM CLOSED";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: red;");
    }

    /**
     * Change museum status to full
     */
    public void museumFull() {
        museumStatus = "MUSEUM FULL";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: yellow;");
    }

    private String getMuseumStatus() {
        return museumStatus;
    }

    /**
     * Update Museum Time text field
     */
    public void setTime(String time) {
        timerTxtField.setText(time);
    }

    /**
     * Update Total Visitors counter text field
     */
    private void displayTotalVisitor() {
        totalVisitorTxtField.setText(String.valueOf(getTotalVisitor()));
    }

    /**
     * Update Current Visitors counter text field
     */
    private void displayCurrentVisitor() {
        currentVisitorTxtField.setText(String.valueOf(getCurrentVisitor()));
    }

    /**
     * Update Queued Visitors counter text field
     */
    private void displayQueuedVisitor() {
        queuedVisitorTxtField.setText(String.valueOf(getQueuedVisitor()));
    }

    /**
     * Update Rejected Purchases counter text field. Rejected Purchases here refers
     * to when visitors attempt to buy tickets when the museum daily limit has been
     * reached.
     */
    private void displayRejectedPurchases() {
        rejectedPurchasesTxtField.setText(String.valueOf(getRejectedPurchases()));
    }

    /**
     * Increase Current Visitors counter
     */
    private void increaseCurrentVisitor() {
        currentVisitor++;
        totalVisitor++;
        /**
         * When number of visitors increase, both Current and Total Visitors counters
         * will be incremented. Total Visitors counter is cumulative while Current
         * Visitors counter can fluctuate.
         */
        displayCurrentVisitor();
        displayTotalVisitor();
    }

    /**
     * Decrease Current Visitors counter
     */
    private void decreaseCurrentVisitor() {
        if (currentVisitor > 0) {
            currentVisitor--;
        }
        displayCurrentVisitor();
    }

    /**
     * Increase Queued Visitors counter
     */
    public void increaseQueuedVisitor() {
        queuedVisitor++;
        displayQueuedVisitor();
    }

    /**
     * Decrease Queued Visitors counter
     */
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

    /**
     * Method to update Queued Visitor and Current Visitor counters upon visitor
     * entry
     */
    public void visitorEnter() {
        decreaseQueuedVisitor();
        increaseCurrentVisitor();
    }

    /**
     * Method to update QCurrent Visitor counters upon visitor exit
     */
    public void visitorExit() {
        decreaseCurrentVisitor();
        if (getMuseumStatus().equals("MUSEUM FULL")) {
            museumOpen();
        }
    }

    /**
     * Method to update all Entrance turnstile counters
     */
    public void increaseEntrance(String enter) {
        if (enter.charAt(0) == 'S') {
            switch (Character.getNumericValue(enter.charAt(3))) {
                case 1:
                    set1++;
                    SET1.setText(String.valueOf(set1));
                    break;
                case 2:
                    set2++;
                    SET2.setText(String.valueOf(set2));
                    break;
                case 3:
                    set3++;
                    SET3.setText(String.valueOf(set3));
                    break;
                case 4:
                    set4++;
                    SET4.setText(String.valueOf(set4));
                    break;
            }
        } else if (enter.charAt(0) == 'N') {
            switch (Character.getNumericValue(enter.charAt(3))) {
                case 1:
                    net1++;
                    NET1.setText(String.valueOf(net1));
                    break;
                case 2:
                    net2++;
                    NET2.setText(String.valueOf(net2));
                    break;
                case 3:
                    net3++;
                    NET3.setText(String.valueOf(net3));
                    break;
                case 4:
                    net4++;
                    NET4.setText(String.valueOf(net4));
                    break;
            }
        }
    }

    /**
     * Method to update all Exit turnstile counters
     */
    public void increaseExit(String exit) {
        if (exit.charAt(0) == 'W') {
            switch (Character.getNumericValue(exit.charAt(3))) {
                case 1:
                    wet1++;
                    WET1.setText(String.valueOf(wet1));
                    break;
                case 2:
                    wet2++;
                    WET2.setText(String.valueOf(wet2));
                    break;
                case 3:
                    wet3++;
                    WET3.setText(String.valueOf(wet3));
                    break;
                case 4:
                    wet4++;
                    WET4.setText(String.valueOf(wet4));
                    break;
            }
        } else if (exit.charAt(0) == 'E') {
            switch (Character.getNumericValue(exit.charAt(3))) {
                case 1:
                    eet1++;
                    EET1.setText(String.valueOf(eet1));
                    break;
                case 2:
                    eet2++;
                    EET2.setText(String.valueOf(eet2));
                    break;
                case 3:
                    eet3++;
                    EET3.setText(String.valueOf(eet3));
                    break;
                case 4:
                    eet4++;
                    EET4.setText(String.valueOf(eet4));
                    break;
            }
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

        // Create Visitor thread
        for (int i = 0; i < visitorList.size(); i++) {
            Runnable visitor = visitorList.get(i);
            Thread visitorThread = new Thread(visitor);
            visitorThread.start();
        }
    }

}
