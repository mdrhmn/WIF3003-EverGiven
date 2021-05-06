import java.util.ArrayList;
import java.util.Scanner;

public class Main {

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

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        int option;
        boolean flag = true;

        System.out.println("\nWelcome to Ever Given's Museum During Pandemic simulation!");

        do {
            System.out.println("\nSelect test case option (enter -1 to exit):");
            System.out.println("1. Within Daily Limit");
            System.out.println("2. Exceed Daily Limit");
            System.out.println("3. Within Current Limit");
            System.out.println("4. Exceed Current Limit");
            System.out.print("\nEnter option: ");
            option = scanner.nextInt();

            if (option == -1)
                flag = true;
            else if (!(option > 0 && option < 5))
                flag = false;
            else
                flag = true;

            if (flag == false) {
                System.out.println("Invalid input. Please choose between option 1 and 4.");
            }

        } while (flag == false);

        switch (option) {
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
        }

        scanner.close();

        // museumOperation("TestCaseExceedDailyLimit.txt");
        // museumOperation("TestCaseWithinDailyLimit.txt");
        // museumOperation("TestCase2.txt");

    }
}
