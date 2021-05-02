import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

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

        int selected_turnstile = -1;
        // ! Need to revisit by using Thread locks, conditions etc.

        Integer[] turnstileNum = { 0, 1, 2, 3 };
        Collections.shuffle(Arrays.asList(turnstileNum));
        // System.out.println(Arrays.toString(turnstileNum));

        for (int i = 0; i < 4; i++) {
            selected_turnstile = turnstileNum[i];
            if (!turnstile[selected_turnstile].getTurnstileStatus()) {
                turnstile[selected_turnstile].entry(ticket);
                break;
            }
            System.out.println(Thread.currentThread().getName() + ":\tTicket " + ticket.getTicketID()
                    + " cannot enter through " + entranceName + "T" + (selected_turnstile + 1) + " as it is in use.");
        }
    }
}
