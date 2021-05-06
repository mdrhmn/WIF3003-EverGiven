import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;

public class Ticket implements Runnable {
    private final String ticketID;
    // Entrance and Exit
    private final int selectedEntrance;
    private final int selectedExit;
    private boolean hasEntered;

    private static Lock lock = new ReentrantLock();
    private Condition isOpen = lock.newCondition();
    private Condition isClose = lock.newCondition();
    private Semaphore currentVisitorsLimit;
    // Time ticketTime;
    Museum museum;
    Visitor visitor;

    public Ticket(Semaphore currentVisitorsLimit, String ticketID, int selectedEntrance, int selectedExit,
            Museum museum, Visitor visitor) {
        this.currentVisitorsLimit = currentVisitorsLimit;
        this.ticketID = ticketID;
        this.museum = museum;
        this.visitor = visitor;
        this.hasEntered = false;
        this.selectedEntrance = selectedEntrance;
        this.selectedExit = selectedExit;
        // this.ticketTime = new Time(museum);
        // this.ticketTime.setSysStartTime(this.visitor.visitorTime.getSysStartTime());
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
                /*
                 * Tickets purchased before museum open time (9:00 a.m.) will have to wait until
                 * museum has opened for entry
                 */
                while (Museum.worldTime.getCurrentTime() < museum.getMuseumOpenTime()) {
                    isOpen.await(10, TimeUnit.MILLISECONDS);
                }
                isOpen.signalAll();
            } finally {
                lock.unlock();
            }

            if (currentVisitorsLimit.hasQueuedThreads()) {
                System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                        + " - Current museum capacity is full. " + ticketID + " will have to queue for entry.");
            }
            currentVisitorsLimit.acquire();
            visitor.visitorTime.entryTime();
            // this.ticketTime.entryTime();
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

            // try {
            // lock.lock();
            // while (Museum.worldTime.getCurrentTime() < museum.getMuseumCloseTime()
            // && Museum.worldTime.getCurrentTime() < this.ticketTime.getExitTime()) {
            // isClose.await(10, TimeUnit.MILLISECONDS);
            // }
            // isClose.signalAll();
            // } finally {
            // lock.unlock();
            // }

            museum.exitMuseum(this);
            currentVisitorsLimit.release();

        } catch (Exception ex) {
            Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}