
import java.util.ArrayList;

public class MainTest {

    private static int tcID;

    public static void museumOperation(String filename) throws InterruptedException {
        System.out.println("\nRUNNING TEST CASE " + filename + ":");
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

    public void setTestCase(int id) throws InterruptedException {
        switch (id) {
            case -1:
                System.out.println("System will terminate.");
                System.exit(0);
                break;
            case 1:
                museumOperation("TestCaseWithinDailyLimit.txt");
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
                museumOperation("TestCase-5.txt");
                break;
        }
        tcID = id + 1;
    }

    public static int getTestCase() {
        return tcID;
    }
}
