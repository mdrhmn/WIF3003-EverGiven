import java.util.ArrayList;

public class Main {

    public static void museumOperation(String filename) throws InterruptedException {

        // Initialise Reader object and Thread for Reader object
        TicketingSystem read = new TicketingSystem(filename);
        Thread t = new Thread(read);

        // Start TicketingSystem thread
        t.start();
        t.join();

        // Create Visitors and Museum
        ArrayList<String[]> readList = read.getVisitorList();
        Museum museum = new Museum("Kuching Museum", 50, 300);
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

    public static void main(String[] args) throws InterruptedException {
        // Need to consider dailyVisitorsLimit;
        // Museum museum = new Museum("Kuching Museum", 10);
        // // Museum m = new Museum(100, 100, timer);
        // /*
        //  * 1. Visitor class = handles purchasing of Tickets 2. Ticket class = handles
        //  * entry
        //  */
        // // String visitorID, int noOfTickets, Museum museum
        // Visitor v1 = new Visitor("V001", 2, museum);
        // Visitor v2 = new Visitor("V002", 3, museum);
        // Visitor v3 = new Visitor("V003", 1, museum);
        // Visitor v4 = new Visitor("V004", 4, museum);
        // Visitor v5 = new Visitor("V005", 4, museum);

        // Thread th1 = new Thread(v1);
        // Thread th2 = new Thread(v2);
        // Thread th3 = new Thread(v3);
        // Thread th4 = new Thread(v4);
        // Thread th5 = new Thread(v5);

        // th1.start();
        // th2.start();
        // th3.start();
        // th4.start();
        // th5.start();

        museumOperation("TestCase.txt");
    }
}
