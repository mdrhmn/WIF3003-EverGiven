/**
 * The Counter class is mainly used for increment/decrement operations related
 * to visitors and tickets.
 */

public class Counter {
    private int count;

    Counter() {
        count = 0;
    }

    /**
     * Synchronized increment method
     */
    public synchronized void increase() {
        count++;
    }

    /**
     * Synchronized decrement method
     */
    public synchronized void decrease() {
        count--;
    }

    /**
     * Retrieve count value (getter)
     */
    public int getNumber() {
        return count;
    }

    /**
     * Set count value (setter)
     */
    public void setNumber(int number) {
        this.count = number;
    }
}