import java.util.Random;

public class Exit {
    private final String exitName;
    Museum museum;
    Turnstile[] turnstile = new Turnstile[4];
    Random random;
    int turnstileInUse;

    public Exit(String exitName, Museum museum) {
        this.exitName = exitName;
        this.museum = museum;
        this.random = new Random();

        for (int i = 0; i < 4; i++) {
            String turnstileID = this.exitName + "T" + (i + 1);
            turnstile[i] = new Turnstile(turnstileID, museum);
        }
    }

    public Museum getMuseum() {
        return museum;
    }

    public String getExitName() {
        return exitName;
    }

    public synchronized void exit(Ticket ticket) {
        int selected_turnstile;

        do {
            selected_turnstile = random.nextInt(4);
            // ! Need to revisit by using Thread locks, conditions etc.
            if (turnstileInUse == selected_turnstile) {
                // System.out.println(Thread.currentThread().getName() + ":\tTicket " +
                // ticket.getTicketID()
                // + " cannot enter through " + exitName + "T" + (selected_turnstile + 1) + " as
                // it is in use.");
            }

        } while (turnstileInUse == selected_turnstile);
        turnstile[selected_turnstile].exit(ticket);
        turnstileInUse = selected_turnstile;
    }
}