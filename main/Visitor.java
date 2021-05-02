import java.util.Random;
import java.util.concurrent.locks.*;

public class Visitor implements Runnable {
    private final String visitorID;
    private final int noOfTickets;
    private boolean hasEntered;
    private int ticketsEnteredCount;
    
    Time visitorTime;
    Museum museum;
    Random random;
    public static Lock lock = new ReentrantLock(); // Create a lock

    public Visitor(String visitorID, int noOfTickets, Museum museum) {
        this.visitorID = visitorID;
        this.noOfTickets = noOfTickets;
        this.museum = museum;
        this.hasEntered = false;
        this.ticketsEnteredCount = 0;
        this.random = new Random();
        this.visitorTime = new Time(museum);
    }

    public int getTicketsEnteredCount() {
        return ticketsEnteredCount;
    }

    public void setTicketsEnteredCount(int ticketsEnteredCount) {
        this.ticketsEnteredCount = ticketsEnteredCount;
    }

    public void increaseTicketsEnteredCount() {
        this.ticketsEnteredCount++;
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
            museum.purchaseTicket(this);
            Thread.sleep(50);
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}