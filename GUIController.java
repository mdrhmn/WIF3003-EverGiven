
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.application.Platform;
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
    // public PrintStream ps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb.getItems().addAll("Test Case 1", "Test Case 2", "Test Case 3", "Test Case 4", "Test Case 5");
        // ps = new PrintStream(new GUIController.Console(console));
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

    // public void displayText(String text) {
    // ps.println(text);
    // }

    public void appendTicketsSold(String text) {
        ticketsSoldTxtField.appendText(text + "\n");
    }

    public void appendTicketsEntry(String text) {
        ticketsEntryTxtField.appendText(text + "\n");
    }

    public void appendTicketsExit(String text) {
        ticketsExitTxtField.appendText(text + "\n");
    }

    public void queueList(String visitor) {
        ticketsQueueListView.getItems().add(visitor);
    }

    public void dequeueList(String visitor) {
        ticketsQueueListView.getItems().remove(visitor);
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
                    // displayText("Test Case 5");
                    break;
            }
            sb.setDisable(true);
        }
    }

    public void ticketOpen() {
        museumStatus = "TICKET OPEN";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: #0275d8;");
    }

    public void ticketClosed() {
        museumStatus = "TICKET CLOSED";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: #f0ad4e;");
    }

    public void museumOpen() {
        museumStatus = "MUSEUM OPEN";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: green;");
    }

    public void museumClosed() {
        museumStatus = "MUSEUM CLOSED";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: red;");
    }

    public void museumFull() {
        museumStatus = "MUSEUM FULL";
        statusTxtField.setText(museumStatus);
        statusTxtField.setStyle("-fx-text-fill: yellow;");
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
    // return dailyVisitorLimit;
    // }

    // public int getHourlyVisitorLimit() {
    // return hourlyVisitorLimit;
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
