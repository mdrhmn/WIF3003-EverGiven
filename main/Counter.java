
public class Counter {
    private int count;

    Counter() {
        count = 0;
    }

    public synchronized void increase() {
        count++;
    }

    public synchronized void decrease() {
        count--;
    }

    public int getNumber() {
        return count;
    }
}