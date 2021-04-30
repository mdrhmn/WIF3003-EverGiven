import java.util.logging.Level;
import java.util.logging.Logger;

public class Ticket implements Runnable {
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