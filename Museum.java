
import java.util.Random;
import java.util.concurrent.*;
import javafx.application.Platform;

public class Museum {

    // Count total number of visitors
    public static Counter totalVisitors;

    // Count total number of visitors (same as totalVisitors) - used for ticket ID
    public static Counter totalTickets;

    // World time
    public static Time worldTime;

    // The ticketing system refuses the purchase when the daily limit of visitors
    // (900) has been reached
    private final int dailyVisitorsLimit;
    private boolean dailyVisitorsLimitFlag;

    // Not more than 100 visitors are allowed in the museum at one time.
    private Semaphore currentVisitorsLimit;
    private int currentIntVisitorsLimit;

    // Museum name
    private final String name;

    // Check if open or closed
    private boolean status;

    // Count current number of visitors
    public static Counter visitorCount;

    /*
     * First request to purchase tickets will be made at 8.00 a.m.
     */
    private long museumTicketOpenTime = 800;
    private long museumTicketCloseTime = 1700;
    private long museumOpenTime = 900;
    private long museumCloseTime = 1800;
    // Acting as flag for print statement of museum ticket closure
    private boolean ticketsCloseFlag;

    /*
     * The museum has 2 entrances – South Entrance (SE) and North Entrance (NE); and
     * two exits – East Exit (EE) and West Exit (WE).
     */
    public Entrance northEntrance;
    public Entrance southEntrance;
    public Exit eastExit;
    public Exit westExit;

    GUIController controller;

    Random random;

    // Constructor
    public Museum(String name, int currentVisitorsLimit, int dailyVisitorsLimit, GUIController controller) {
        this.name = name;
        this.dailyVisitorsLimit = dailyVisitorsLimit;
        this.setDailyVisitorsLimitFlag(false);
        this.setTicketsCloseFlag(false);
        this.currentVisitorsLimit = new Semaphore(currentVisitorsLimit);
        this.currentIntVisitorsLimit = currentVisitorsLimit;

        this.status = false;
        visitorCount = new Counter();
        totalTickets = new Counter();
        totalVisitors = new Counter();
        
        worldTime = new Time(this);
        // Thread timerThread = new Thread(worldTime);
        // timerThread.start();

        this.random = new Random();
        this.controller = controller;

        this.northEntrance = new Entrance("NE", this);
        this.southEntrance = new Entrance("SE", this);
        this.eastExit = new Exit("EE", this);
        this.westExit = new Exit("WE", this);

        System.out.println(
                "\n############################################## MUSEUM TICKETS OPEN ##############################################\n");

        Platform.runLater(() -> {
            controller.ticketOpen();
        });
    }

    public boolean isTicketsCloseFlag() {
        return ticketsCloseFlag;
    }

    public void setTicketsCloseFlag(boolean ticketsCloseFlag) {
        this.ticketsCloseFlag = ticketsCloseFlag;
    }

    public boolean isDailyVisitorsLimitFlag() {
        return dailyVisitorsLimitFlag;
    }

    public void setDailyVisitorsLimitFlag(boolean dailyVisitorsLimitFlag) {
        this.dailyVisitorsLimitFlag = dailyVisitorsLimitFlag;
    }

    public int getDailyVisitorsLimit() {
        return dailyVisitorsLimit;
    }

    public Semaphore getCurrentVisitorsLimit() {
        return currentVisitorsLimit;
    }

    public int getIntCurrentVisitorsLimit() {
        return currentIntVisitorsLimit;
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

    public String getName() {
        return name;
    }

    public void purchaseTicket(Visitor visitor) throws InterruptedException {

        String ticketsList = "";

        // Case 1: If number of tickets > 1
        if (visitor.getNoOfTickets() > 1 && visitor.getNoOfTickets() <= 4) {

            /*
             * Each visitor randomly uses a turnstile at either South Entrance or North
             * Entrance to enter the museum. After the duration of stay is over, the visitor
             * randomly uses a turnstile at either East Exit or West Exit to leave the
             * museum (assuming that entrance and exit has been determined during ticket
             * purchase)
             */
            int selectedEntrance = random.nextInt(2);
            int selectedExit = random.nextInt(2);

            Ticket[] visitorTickets = new Ticket[visitor.getNoOfTickets()];
            Thread[] ticketThread = new Thread[visitor.getNoOfTickets()];

            for (int i = 0; i < visitor.getNoOfTickets(); i++) {

                // Assumption: Buy tickets = confirm entering museum
                totalTickets.increase();

                /*
                 * Every ticket has an ID in the form of T****, where **** are running numbers
                 * from 0001 to 9999. Example of TicketID format: Tickets T0001, T0002 sold.
                 * There is also a timestamp of purchase on each ticket
                 */
                String ticketID = String.format("T%04d", totalTickets.getNumber());
                if (i < visitor.getNoOfTickets() - 1) {
                    ticketsList += ticketID + ", ";
                } else {
                    ticketsList += ticketID;
                }

                visitorTickets[i] = new Ticket(currentVisitorsLimit, ticketID, selectedEntrance, selectedExit,
                        visitor.museum, visitor);
                ticketThread[i] = new Thread(visitorTickets[i]);
            }

            /*
             * There is also a timestamp of purchase on each ticket. 4. Visitors enter the
             * museum based on the timestamps on their tickets, i.e., visitors who purchased
             * their tickets earlier enter the museum before those purchase their tickets
             * later
             */
            visitor.visitorTime.purchaseTime();
            visitor.visitorTime.setVisitDuration();

            System.out.println(Museum.worldTime.getFormattedCurrentTime() + " - Tickets " + ticketsList + " sold");
            String text = ticketsList;

            Platform.runLater(() -> {
                // controller.ticketOpen();
                controller.appendTicketsSold(Museum.worldTime.getFormattedCurrentTime() + " hrs - " + text);
            });

            for (int i = 0; i < ticketThread.length; i++) {
                ticketThread[i].start();
            }

        } // Case 2: If number of tickets == 1
        else if (visitor.getNoOfTickets() == 1) {

            /*
             * Each visitor randomly uses a turnstile at either South Entrance or North
             * Entrance to enter the museum. After the duration of stay is over, the visitor
             * randomly uses a turnstile at either East Exit or West Exit to leave the
             * museum (assuming that entrance and exit has been determined during ticket
             * purchase)
             */
            int selectedEntrance = random.nextInt(2);
            int selectedExit = random.nextInt(2);

            Thread[] ticketThread = new Thread[visitor.getNoOfTickets()];

            totalTickets.increase();

            /*
             * Every ticket has an ID in the form of T****, where **** are running numbers
             * from 0001 to 9999. Example of TicketID format: Tickets T0001, T0002 sold.
             * There is also a timestamp of purchase on each ticket
             */
            String ticketID = String.format("T%04d", totalTickets.getNumber());

            ticketThread[0] = new Thread(new Ticket(currentVisitorsLimit, ticketID, selectedEntrance, selectedExit,
                    visitor.museum, visitor));

            /*
             * There is also a timestamp of purchase on each ticket. 4. Visitors enter the
             * museum based on the timestamps on their tickets, i.e., visitors who purchased
             * their tickets earlier enter the museum before those purchase their tickets
             * later
             */
            visitor.visitorTime.purchaseTime();
            visitor.visitorTime.setVisitDuration();

            System.out.println(Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticketID + " sold");

            Platform.runLater(() -> {
                controller.appendTicketsSold(Museum.worldTime.getFormattedCurrentTime() + " hrs - " + ticketID);
            });

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
