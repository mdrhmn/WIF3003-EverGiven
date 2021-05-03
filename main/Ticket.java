import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class Ticket implements Runnable {
    private final String ticketID;
    // Entrance and Exit
    private final int selectedEntrance;
    private final int selectedExit;

    public static Lock lock = new ReentrantLock(); // Create a lock
    private Condition isOpen = lock.newCondition();
    private Condition isClose = lock.newCondition();

    private boolean hasEntered;
    Museum museum;
    Visitor visitor;

    public Ticket(String ticketID, int selectedEntrance, int selectedExit, Museum museum, Visitor visitor) {
        this.ticketID = ticketID;
        this.museum = museum;
        this.visitor = visitor;
        this.hasEntered = false;
        this.selectedEntrance = selectedEntrance;
        this.selectedExit = selectedExit;
    }

    public boolean getEntryStatus() {
        return hasEntered;
    }

    public void setEntryStatus(boolean hasEntered) {
        this.hasEntered = hasEntered;
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
            try {
                lock.lock();
                while (Museum.worldTime.getCurrentTime() < museum.getMuseumOpenTime()) {
                    // System.out.println(Museum.worldTime.getCurrentTime());
                    isOpen.await(10, TimeUnit.MILLISECONDS);
                }
                isOpen.signalAll();
            } finally {
                lock.unlock();
            }

            museum.enterMuseum(this);

            try {
                lock.lock();
                while (Museum.worldTime.getCurrentTime() < museum.getMuseumCloseTime()
                        && Museum.worldTime.getCurrentTime() < this.visitor.visitorTime.getExitTime()) {
                    isClose.await(10, TimeUnit.MILLISECONDS);
                }
                isClose.signalAll();
            } finally {
                lock.unlock();
            }

            museum.exitMuseum(this);

        } catch (Exception ex) {
            Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}