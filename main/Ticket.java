import java.util.logging.Level;
import java.util.logging.Logger;

public class Ticket implements Runnable {
    private final String ticketID;
    // Entrance and Exit
    private final int selectedEntrance;
    private final int selectedExit;

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
            // while (Museum.worldTime.getCurrentTime() < 900) {
            // }
            museum.enterMuseum(this);
            // Duration of sleep = duration of stay for visitor
            // Thread.sleep(this.visitor.visitorTime.getVisitDurationInMillis());
            while (Museum.worldTime.getCurrentTime() < museum.getMuseumCloseTime() && Museum.worldTime.getCurrentTime() < this.visitor.visitorTime.getExitTime() ) {
            }
            museum.exitMuseum(this);
        } catch (Exception ex) {
            Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}