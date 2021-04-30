import java.util.Random;

public class Entrance {
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

    public synchronized void entry(Ticket ticket) throws InterruptedException {
        int selected_turnstile;
        //! Need to revisit by using Thread locks, conditions etc.
        do {
            selected_turnstile = random.nextInt(4);

            if (turnstileInUse == selected_turnstile) {
                // System.out.println(Thread.currentThread().getName() + ":\tTicket " + ticket.getTicketID()
                //         + " cannot enter through " + entranceName + "T" + (selected_turnstile + 1)
                //         + " as it is in use.");
            }

        } while (turnstileInUse == selected_turnstile);
        turnstile[selected_turnstile].entry(ticket);
        turnstileInUse = selected_turnstile;
    }
}
