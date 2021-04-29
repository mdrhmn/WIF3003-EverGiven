import java.util.Random;
// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReentrantLock;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Need to consider dailyVisitorsLimit;
        Museum museum = new Museum("Kuching Museum", 10);
        // Museum m = new Museum(100, 100, timer);
        /*
         * 1. Visitor class = handles purchasing of Tickets 2. Ticket class = handles
         * entry
         */
        // String visitorID, int noOfTickets, Museum museum
        Visitor v1 = new Visitor("001", 2, museum);
        Visitor v2 = new Visitor("002", 3, museum);
        Visitor v3 = new Visitor("003", 1, museum);
        Visitor v4 = new Visitor("004", 4, museum);

        Thread th1 = new Thread(v1);
        Thread th2 = new Thread(v2);
        Thread th3 = new Thread(v3);
        Thread th4 = new Thread(v4);

        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }
}

class Museum {

    // Count total number of visitors
    public static Counter totalVisitors;

    // Count total number of visitors (same as totalVisitors) - used for ticket ID
    public static Counter totalTickets;

    // World time
    public static Time worldTime;

    // Not more than 100 visitors are allowed in the museum at one time.
    private final int currentVisitorsLimit;

    // The ticketing system refuses the purchase when the daily limit of visitors
    // (900) has been reached
    // private final int dailyVisitorsLimit;

    // Museum name
    private final String name;

    // Check if open or closed
    private boolean status;

    // Count current number of visitors
    public Counter visitorCount;

    // Ticket purchase time
    private long purchaseTime;

    // TODO: Add open and close time

    /*
     * The museum has 2 entrances – South Entrance (SE) and North Entrance (NE); and
     * two exits – East Exit (EE) and West Exit (WE).
     */
    public Entrance northEntrance;
    public Entrance southEntrance;
    public Exit eastExit;
    public Exit westExit;

    Random random;

    // Constructor
    public Museum(String name, int currentVisitorsLimit) {
        this.name = name;
        this.currentVisitorsLimit = currentVisitorsLimit;

        this.status = true;
        this.visitorCount = new Counter();

        totalTickets = new Counter();
        totalVisitors = new Counter();

        this.random = new Random();

        this.northEntrance = new Entrance("NE", this);
        this.southEntrance = new Entrance("SE", this);
        this.eastExit = new Exit("EE", this);
        this.westExit = new Exit("WE", this);
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCurrentVisitorsLimit() {
        return currentVisitorsLimit;
    }

    public String getName() {
        return name;
    }

    public synchronized void purchaseTicket(Visitor visitor) throws InterruptedException {
        String ticketsList = "";

        // Case 1: If number of tickets > 1
        if (visitor.getNoOfTickets() > 1 && visitor.getNoOfTickets() <= 4) {

            /*
             * Assuming that entrance and exit has been determined during ticket purchase
             */
            int selectedEntrance = random.nextInt(2);
            int selectedExit = random.nextInt(2);

            Thread[] visitorThread = new Thread[visitor.getNoOfTickets()];

            for (int i = 0; i < visitor.getNoOfTickets(); i++) {

                // Assumption: Buy tickets = confirm entering museum
                totalTickets.increase();

                // ! Format of TicketID needs to be revised
                String ticketID = "T" + totalTickets.getNumber();
                if (i < visitor.getNoOfTickets() - 1) {
                    ticketsList += ticketID + ", ";

                } else {
                    ticketsList += ticketID;
                }

                visitorThread[i] = new Thread(
                        new Ticket(ticketID, selectedEntrance, selectedExit, visitor.museum, visitor));
            }

            System.out.println(Thread.currentThread().getName() + ":    Tickets " + ticketsList + " sold");
            visitor.visitorTime.purchaseTime(visitor.getNoOfTickets());

            for (int i = 0; i < visitorThread.length; i++) {
                visitorThread[i].start();
            }

        }

        // Case 2: If number of tickets == 1
        else if (visitor.getNoOfTickets() == 1) {

            /*
             * Assuming that entrance and exit has been determined during ticket purchase
             */
            int selectedEntrance = random.nextInt(2);
            int selectedExit = random.nextInt(2);

            Thread[] visitorThread = new Thread[visitor.getNoOfTickets()];

            totalTickets.increase();
            // Format of TicketID needs to be revisited
            String ticketID = "T" + totalTickets.getNumber();

            /*
             * Each visitor stays in the museum for 50-150 minutes. The duration of stay
             * will be randomly assigned to the visitor when the visitor is entering the
             * museum.
             */

            visitorThread[0] = new Thread(
                    new Ticket(ticketID, selectedEntrance, selectedExit, visitor.museum, visitor));

            System.out.println(Thread.currentThread().getName() + ":    Ticket " + ticketID + " sold");

            visitorThread[0].start();
        }
    }

    /*
     * Level of thread granularity – Use a thread per entrance/exit
     */
    public synchronized void enterMuseum(Ticket ticket) {
        /*
         * Each visitor randomly uses a turnstile at either South Entrance or North
         * Entrance to enter the museum.
         */

        if (ticket.getSelectedEntrance() == 0) {
            northEntrance.entry(ticket);
            // (new Thread(northEntrance)).start();
        } else {
            southEntrance.entry(ticket);
            // (new Thread(southEntrance)).start();
        }
    }

    /*
     * Level of thread granularity – Use a thread per entrance/exit
     */
    public synchronized void exitMuseum(Ticket ticket) {
        /*
         * After the duration of stay is over, the visitor randomly uses a turnstile at
         * either East Exit or West Exit to leave the museum.
         */
        if (ticket.getSelectedExit() == 0) {
            eastExit.exit(ticket);
            // (new Thread(eastExit)).start();
        } else {
            westExit.exit(ticket);
            // (new Thread(westExit)).start();
        }
    }

}

class Visitor implements Runnable {
    private final String visitorID;
    private final int noOfTickets;
    Time visitorTime;
    Museum museum;
    Random random;

    public Visitor(String visitorID, int noOfTickets, Museum museum) {
        this.visitorID = visitorID;
        this.noOfTickets = noOfTickets;
        this.museum = museum;
        this.random = new Random();
        this.visitorTime = new Time();
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public String getVisitorID() {
        return visitorID;
    }

    public long getVisitorTime() {
        return this.visitorTime.getCurrentTime();
    }

    @Override
    public void run() {
        try {
            museum.purchaseTicket(this);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Ticket implements Runnable {
    private final String ticketID;
    Museum museum;
    Visitor visitor;

    // Entrance and Exit
    private final int selectedEntrance;
    private final int selectedExit;

    public Ticket(String ticketID, int selectedEntrance, int selectedExit, Museum museum, Visitor visitor) {
        this.ticketID = ticketID;
        this.museum = museum;
        this.visitor = visitor;
        this.selectedEntrance = selectedEntrance;
        this.selectedExit = selectedExit;
    }

    public String getTicketID() {
        return ticketID;
    }

    public int getSelectedExit() {
        return selectedExit;
    }

    public int getSelectedEntrance() {
        return selectedEntrance;
    }

    @Override
    public void run() {
        try {
            museum.enterMuseum(this);
            // ! Duration of sleep = duration of stay for visitor
            Thread.sleep(1000);
            museum.exitMuseum(this);
        } catch (Exception ex) {
            Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Entrance {
    private final String entranceName;
    Museum museum;
    Turnstile[] turnstile = new Turnstile[4];
    Random random;
    int turnstileInUse;

    public Entrance(String entranceName, Museum museum) {
        this.entranceName = entranceName;
        this.museum = museum;
        this.random = new Random();

        for (int i = 0; i < 4; i++) {
            String turnstileID = this.entranceName + "T" + (i + 1);
            turnstile[i] = new Turnstile(turnstileID, museum);
        }
    }

    public Museum getMuseum() {
        return museum;
    }

    public String getEntranceName() {
        return entranceName;
    }

    public synchronized void entry(Ticket ticket) {
        int selected_turnstile;

        do {
            selected_turnstile = random.nextInt(4);

            if (turnstileInUse == selected_turnstile) {
                System.out.println(Thread.currentThread().getName() + ":    " + entranceName + "T"
                        + (selected_turnstile + 1) + " in use.");
            }

        } while (turnstileInUse == selected_turnstile);
        turnstile[selected_turnstile].entry(ticket.getTicketID());
        turnstileInUse = selected_turnstile;
    }
}

class Exit {
    private final String exitName;
    Museum museum;
    Turnstile[] turnstile = new Turnstile[4];
    Random random;
    int turnstileInUse;

    public Exit(String exitName, Museum museum) {
        this.exitName = exitName;
        this.museum = museum;
        this.random = new Random();

        for (int i = 0; i < 4; i++) {
            String turnstileID = this.exitName + "T" + (i + 1);
            turnstile[i] = new Turnstile(turnstileID, museum);
        }
    }

    public Museum getMuseum() {
        return museum;
    }

    public String getExitName() {
        return exitName;
    }

    public synchronized void exit(Ticket ticket) {
        int selected_turnstile;

        do {
            selected_turnstile = random.nextInt(4);

            if (turnstileInUse == selected_turnstile) {
                System.out.println(Thread.currentThread().getName() + ":    " + exitName + "T"
                        + (selected_turnstile + 1) + " in use.");
            }

        } while (turnstileInUse == selected_turnstile);
        turnstile[selected_turnstile].exit(ticket.getTicketID());
        turnstileInUse = selected_turnstile;
    }
}

class Turnstile {
    private final String turnstileID;
    Museum museum;

    public Turnstile(String turnstileID, Museum museum) {
        this.turnstileID = turnstileID;
        this.museum = museum;
    }

    public String getTurnstileID() {
        return turnstileID;
    }

    public synchronized void entry(String ticketID) {
        Museum.totalVisitors.increase();
        museum.visitorCount.increase();
        System.out.println(Thread.currentThread().getName() + ":    Ticket " + ticketID + " entered through Turnstile "
                + this.turnstileID + "; Visitors count = " + museum.visitorCount.getNumber());
    }

    public synchronized void exit(String ticketID) {
        museum.visitorCount.decrease();
        System.out.println(Thread.currentThread().getName() + ":    Ticket " + ticketID + " exited through Turnstile "
                + this.turnstileID + "; Visitors count = " + museum.visitorCount.getNumber());
    }
}

class Counter {
    private int count;

    Counter() {
        count = 0;
    }

    public synchronized void increase() {
        count++;
    }

    public synchronized void decrease() {
        count--;
    }

    public int getNumber() {
        return count;
    }
}

class TicketingSystem implements Runnable {

    private ArrayList<String[]> readList;
    private String filename;

    public TicketingSystem(String filename) {
        this.filename = filename;
    }

    public void readFile() throws FileNotFoundException, IOException {
        String thisLine = null;
        BufferedReader br = new BufferedReader(new FileReader(this.filename));
        this.readList = new ArrayList<>();

        // Create read list
        while ((thisLine = br.readLine()) != null) {
            String line = thisLine;
            String[] container = line.trim().split(";");
            this.readList.add(container);
        }
        br.close();
    }

    public ArrayList<String[]> getVisitorList() {
        return this.readList;
    }

    @Override
    public void run() {
        try {
            readFile();
        } catch (IOException ex) {
            Logger.getLogger(TicketingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class Time {
    private long sysStartTime;
    private long sysCurrTime;
    private long currentTime;
    private long duration;
    public long startTime = 8000;

    public Time() {
        this.sysStartTime = System.currentTimeMillis();
    }

    public void calcRealtime(long sysStartTime, long sysCurrTime, long startTime) {

        long timeElapse = sysCurrTime - sysStartTime;
        this.duration = timeElapse;

        // System.out.println("Time elapsed: " + timeElapse);

        long hr1 = (timeElapse / 600);
        long min1 = timeElapse - (hr1 * 600);

        long hr2 = startTime / 1000;
        long min2 = startTime % 1000;

        long hr3 = (hr1 + hr2) * 1000;
        long min3 = min1 + min2;

        if (min3 >= 600) {
            long hr4 = (min3 / 600) * 1000;
            long min4 = min3 - ((hr4 / 1000) * 600);
            long sum = (hr3 + hr4 + min4) / 10;
            this.currentTime = sum;
        } else {
            this.currentTime = (hr3 + min3) / 10;
        }
        this.startTime = this.currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void purchaseTime(int no_ticket) throws InterruptedException {
        // System.out.println("Start time: " + this.startTime);
        this.sysStartTime = System.currentTimeMillis();

        int randomSubsequentPurchase = (int) ((Math.random() * (4 - 1)) + 1) * 10;
        TimeUnit.MILLISECONDS.sleep(randomSubsequentPurchase);

        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, this.startTime);
    }

    public void entryTime() throws InterruptedException {
        this.sysStartTime = System.currentTimeMillis();
        /*
         * Each visitor stays in the museum for 50-150 minutes. The duration of stay
         * will be randomly assigned to the visitor when the visitor is entering the
         * museum.
         */
        int randomDuration = (int) ((Math.random() * (150 - 50)) + 50) * 10;

        TimeUnit.MILLISECONDS.sleep(randomDuration);
        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, this.startTime);
    }

    public long getDuration() {
        return duration;
    }
}