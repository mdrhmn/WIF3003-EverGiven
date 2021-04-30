public class Main {
    public static void main(String[] args) {
        // Need to consider dailyVisitorsLimit;
        Museum museum = new Museum("Kuching Museum", 10);
        // Museum m = new Museum(100, 100, timer);
        /*
         * 1. Visitor class = handles purchasing of Tickets 2. Ticket class = handles
         * entry
         */
        // String visitorID, int noOfTickets, Museum museum
        Visitor v1 = new Visitor("001", 2, museum);
        Visitor v2 = new Visitor("002", 3, museum);
        Visitor v3 = new Visitor("003", 1, museum);
        Visitor v4 = new Visitor("004", 4, museum);

        Thread th1 = new Thread(v1);
        Thread th2 = new Thread(v2);
        Thread th3 = new Thread(v3);
        Thread th4 = new Thread(v4);

        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }
}
