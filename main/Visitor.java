import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Visitor implements Runnable {
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
        this.visitorTime = new Time(museum);
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
            museum.purchaseTicket(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}