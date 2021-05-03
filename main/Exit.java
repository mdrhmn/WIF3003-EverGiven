import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.locks.*;

public class Exit {
    private final String exitName;
    Museum museum;
    Turnstile[] turnstile = new Turnstile[4];
    Random random;
    int turnstileInUse;
    public static Lock lock = new ReentrantLock(); // Create a lock
    private Condition active = lock.newCondition();

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

    public void exit(Ticket ticket) throws InterruptedException {

        lock.lock();
        int selected_turnstile = -1;
        Integer[] turnstileNum = { 0, 1, 2, 3 };
        // Randomise turnstile order
        Collections.shuffle(Arrays.asList(turnstileNum));

        for (int i = 0; i < 4; i++) {
            selected_turnstile = turnstileNum[i];
            // when the selected turnstile is not in use
            if (!turnstile[selected_turnstile].getTurnstileStatus()) {
                turnstile[selected_turnstile].setTurnstileStatus(true);
                turnstile[selected_turnstile].exit(ticket);
                while (ticket.visitor.ticketsExitCount.getNumber() != ticket.visitor.getNoOfTickets()) {
                    active.await();
                }
                active.signalAll();
                turnstile[selected_turnstile].setTurnstileStatus(false);
                break;
            } // when all turnstiles occupied and there are more tickets to exit
            else if (i == 3 && turnstile[selected_turnstile].getTurnstileStatus()) {
                active.signalAll();
                turnstile[selected_turnstile].setTurnstileStatus(false);
                i = 0;
            }
        }
        lock.unlock();
    }
}