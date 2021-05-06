import java.util.Random;
import java.util.concurrent.locks.*;

public class Visitor implements Runnable {
    private final String visitorID;
    private final int noOfTickets;
    private boolean hasEntered;
    public Counter ticketsEnteredCount;
    public Counter ticketsExitCount;
    Time visitorTime;
    Museum museum;
    Random random;

    public static Lock lock = new ReentrantLock();

    public Visitor(String visitorID, int noOfTickets, Museum museum) {
        this.visitorID = visitorID;
        this.noOfTickets = noOfTickets;
        this.museum = museum;
        this.hasEntered = false;
        this.ticketsEnteredCount = new Counter();
        this.ticketsExitCount = new Counter();
        this.random = new Random();
        this.visitorTime = new Time(museum);
    }

    public boolean getEntryStatus() {
        return hasEntered;
    }

    public void setEntryStatus(boolean hasEntered) {
        this.hasEntered = hasEntered;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public String getVisitorID() {
        return visitorID;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            /*
             * Tickets cannot be purchased after 5:00 p.m. (Tickets will be sold from 8.00
             * a.m. to 5.00 p.m. daily). Tickets cannot be purchased if daily limit of
             * visitors has been reached (The museum receives not more than 900 visitors per
             * day)
             */
            if (Museum.worldTime.getCurrentTime() > museum.getMuseumTicketCloseTime()
                    || Museum.totalTickets.getNumber() + this.getNoOfTickets() > museum.getDailyVisitorsLimit()) {

                if (Museum.worldTime.getCurrentTime() > museum.getMuseumTicketCloseTime()) {
                    if (!museum.isTicketsCloseFlag()) {
                        System.out.println(
                                "\n############################################## MUSEUM TICKETS CLOSED ##############################################\n");
                        // System.out.println(
                        // Museum.worldTime.getFormattedCurrentTime() + " - Museum will no longer sell
                        // tickets");
                        museum.setTicketsCloseFlag(true);
                    }
                } else if (Museum.totalTickets.getNumber() + this.getNoOfTickets() > museum.getDailyVisitorsLimit()) {
                    if (!museum.isDailyVisitorsLimitFlag()) {
                        int totalVisitors = Museum.totalTickets.getNumber() + 1;
                        if (totalVisitors != 500) {
                            System.out.println(Museum.worldTime.getFormattedCurrentTime()
                                    + " - Number of purchased tickets exceed daily visitors limit (" + Museum.totalTickets.getNumber()
                                    + "). Tickets are no longer available for purchase.");
                        } else {
                            System.out.println(Museum.worldTime.getFormattedCurrentTime()
                                    + " - Number of purchased tickets exceed daily visitors limit (" + totalVisitors
                                    + "). Tickets are no longer available for purchase after T0"
                                    + museum.getDailyVisitorsLimit() + ".");
                        }
                        museum.setDailyVisitorsLimitFlag(true);
                    }
                }

                Thread.currentThread().interrupt();
            } else {
                museum.purchaseTicket(this);
                /*
                 * Subsequent purchase will be made every 1-4 minutes
                 */
                Thread.sleep(this.visitorTime.getPurchaseDurationInMillis());
            }
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}