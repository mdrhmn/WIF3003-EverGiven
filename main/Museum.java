import java.util.Random;
import java.util.concurrent.locks.*;

public class Museum {

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

    private long museumTicketOpenTime = 800;
    private long museumTicketCloseTime = 1700;
    private long museumOpenTime = 900;
    private long museumCloseTime = 1800;

    /*
     * The museum has 2 entrances – South Entrance (SE) and North Entrance (NE); and
     * two exits – East Exit (EE) and West Exit (WE).
     */
    public Entrance northEntrance;
    public Entrance southEntrance;
    public Exit eastExit;
    public Exit westExit;

    Random random;

    public static Lock lock = new ReentrantLock();

    // Constructor
    public Museum(String name, int currentVisitorsLimit) {
        this.name = name;
        this.currentVisitorsLimit = currentVisitorsLimit;

        this.status = true;
        this.visitorCount = new Counter();

        totalTickets = new Counter();
        totalVisitors = new Counter();
        worldTime = new Time(this);

        this.random = new Random();

        this.northEntrance = new Entrance("NE", this);
        this.southEntrance = new Entrance("SE", this);
        this.eastExit = new Exit("EE", this);
        this.westExit = new Exit("WE", this);
    }

    public long getMuseumOpenTime() {
        return museumOpenTime;
    }

    public long getMuseumCloseTime() {
        return museumCloseTime;
    }

    public long getMuseumTicketOpenTime() {
        return museumTicketOpenTime;
    }

    public long getMuseumTicketCloseTime() {
        return museumTicketCloseTime;
    }

    public long getMuseumOpenTimeInMillis() {
        return museumOpenTime * 10;
    }

    public long getMuseumCloseTimeInMillis() {
        return museumCloseTime * 10;
    }

    public long getMuseumTicketOpenTimeInMillis() {
        return museumTicketOpenTime * 10;
    }

    public long getMuseumTicketCloseTimeInMillis() {
        return museumTicketCloseTime * 10;
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

            Ticket[] visitorTickets = new Ticket[visitor.getNoOfTickets()];
            Thread[] ticketThread = new Thread[visitor.getNoOfTickets()];

            for (int i = 0; i < visitor.getNoOfTickets(); i++) {

                // Assumption: Buy tickets = confirm entering museum
                totalTickets.increase();

                /*
                 * Example of TicketID format: Tickets T0001, T0002 sold
                 */
                String ticketID = String.format("T%04d", totalTickets.getNumber());
                if (i < visitor.getNoOfTickets() - 1) {
                    ticketsList += ticketID + ", ";
                } else {
                    ticketsList += ticketID;
                }

                visitorTickets[i] = new Ticket(ticketID, selectedEntrance, selectedExit, visitor.museum, visitor);
                ticketThread[i] = new Thread(visitorTickets[i]);
            }

            // Saves entry time and duration of visitor
            visitor.visitorTime.purchaseTime();
            visitor.visitorTime.entryTime();
            System.out.println(Thread.currentThread().getName() + ":\t" + visitor.visitorTime.getPurchaseTime()
                    + " - Tickets " + ticketsList + " sold");

            for (int i = 0; i < ticketThread.length; i++) {
                ticketThread[i].start();
            }

        }

        // Case 2: If number of tickets == 1
        else if (visitor.getNoOfTickets() == 1) {

            /*
             * Assuming that entrance and exit has been determined during ticket purchase
             */
            int selectedEntrance = random.nextInt(2);
            int selectedExit = random.nextInt(2);

            Thread[] ticketThread = new Thread[visitor.getNoOfTickets()];

            totalTickets.increase();

            /*
             * Example of TicketID format: Tickets T0001, T0002 sold
             */
            String ticketID = String.format("T%04d", totalTickets.getNumber());

            /*
             * Each visitor stays in the museum for 50-150 minutes. The duration of stay
             * will be randomly assigned to the visitor when the visitor is entering the
             * museum.
             */
            ticketThread[0] = new Thread(new Ticket(ticketID, selectedEntrance, selectedExit, visitor.museum, visitor));

            visitor.visitorTime.purchaseTime();
            visitor.visitorTime.entryTime();
            System.out.println(Thread.currentThread().getName() + ":\t" + visitor.visitorTime.getPurchaseTime()
                    + " - Ticket " + ticketID + " sold");

            ticketThread[0].start();
        }
    }

    /*
     * Level of thread granularity – Use a thread per entrance/exit
     */
    public void enterMuseum(Ticket ticket) throws InterruptedException {

        /*
         * Each visitor randomly uses a turnstile at either South Entrance or North
         * Entrance to enter the museum.
         */
        if (ticket.getSelectedEntrance() == 0) {
            northEntrance.entry(ticket);
        } else {
            southEntrance.entry(ticket);
        }
    }

    /*
     * Level of thread granularity – Use a thread per entrance/exit
     */
    public void exitMuseum(Ticket ticket) throws InterruptedException {
        /*
         * After the duration of stay is over, the visitor randomly uses a turnstile at
         * either East Exit or West Exit to leave the museum.
         */
        if (ticket.getSelectedExit() == 0) {
            eastExit.exit(ticket);
        } else {
            westExit.exit(ticket);
        }
    }

}