
import java.util.concurrent.locks.*;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

/**
 * The Entrance class is used for handling ticket entry into museum
 */
public class Entrance {
    private final String entranceName;
    Museum museum;
    Turnstile[] turnstile = new Turnstile[4];
    Random random;
    int turnstileInUse;

    private static Lock lock = new ReentrantLock();
    private Condition occupied = lock.newCondition();

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

    /**
     * Method to handle ticket entry by randomly selecting which entrance turnstile
     * to use
     */
    public void entry(Ticket ticket) throws InterruptedException {

        lock.lock();
        int selected_turnstile = -1;

        Integer[] turnstileNum = { 0, 1, 2, 3 };
        Collections.shuffle(Arrays.asList(turnstileNum));

        for (int i = 0; i < 4; i++) {
            selected_turnstile = turnstileNum[i];

            /**
             * If selected turnstile is not in use
             */
            if (!turnstile[selected_turnstile].getTurnstileStatus()) {
                /**
                 * Set turnstile status to true to allow ticket to enter
                 */
                turnstile[selected_turnstile].setTurnstileStatus(true);
                turnstile[selected_turnstile].entry(ticket);

                /**
                 * Wait for all tickets of Visitor to come in
                 */
                while (ticket.visitor.ticketsEnteredCount.getNumber() != ticket.visitor.getNoOfTickets()) {
                    occupied.await();
                }

                /**
                 * Signal all threads that turnstile is open for other tickets to use
                 */
                occupied.signalAll();
                turnstile[selected_turnstile].setTurnstileStatus(false);
                break;
            }

            /**
             * If all turnstiles are occupied but there are more tickets to enter
             */
            else if (i == 3 && turnstile[selected_turnstile].getTurnstileStatus()) {
                occupied.signalAll();
                turnstile[selected_turnstile].setTurnstileStatus(false);
                i = 0;
            } else {
                occupied.signalAll();
            }
        }

        lock.unlock();
    }
}
