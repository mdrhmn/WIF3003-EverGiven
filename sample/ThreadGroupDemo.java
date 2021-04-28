import java.util.Random;

public class ThreadGroupDemo implements Runnable {
    public void run() {
        int selected_entrance = new Random().nextInt(2);
        if (selected_entrance == 0) {
            System.out.println("North Entrance");
        } else {
            System.out.println("South Entrance");
        }
    }

    public static void main(String[] args) {
        ThreadGroupDemo runnable = new ThreadGroupDemo();
        ThreadGroup tg1 = new ThreadGroup("Parent ThreadGroup");

        Thread t1 = new Thread(tg1, runnable, "one");
        t1.start();
        Thread t2 = new Thread(tg1, runnable, "two");
        t2.start();
        Thread t3 = new Thread(tg1, runnable, "three");
        t3.start();

        // System.out.println("Thread Group Name: " + tg1.getName());
        // tg1.list();

    }
}