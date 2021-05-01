public class Turnstile {
    private final String turnstileID;
    Museum museum;
    boolean turnstileStatus;

    public Turnstile(String turnstileID, Museum museum) {
        this.turnstileID = turnstileID;
        this.museum = museum;
        turnstileStatus = false;
    }

    public String getTurnstileID() {
        return turnstileID;
    }

    public synchronized void entry(Ticket ticket) throws InterruptedException {
        turnstileStatus = true;
        Museum.totalVisitors.increase();
        museum.visitorCount.increase();
        // System.out.println(Thread.currentThread().getName() + ":\t" +
        // Museum.worldTime.getFormattedCurrentTime()
        // + " - Ticket " + ticketID + " entered through Turnstile " + this.turnstileID
        // + "; Visitors count = "
        // + museum.visitorCount.getNumber());

        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " entered through Turnstile " + this.turnstileID
                + ". Staying for " + ticket.visitor.visitorTime.getVisitDuration() + " minutes");

        ticket.setEntryStatus(true);
        ticket.visitor.increaseTicketsEnteredCount();

        // if (ticket.visitor.getTicketsEnteredCount() == ticket.visitor.getNoOfTickets()) {
        //     System.out.println(ticket.visitor.getVisitorID() + " has entered");
        //     ticket.visitor.setEntryStatus(true);
        //     notifyAll();
        // }

        ticket.visitor.visitorTime.purchaseTime(ticket.visitor.getNoOfTickets());
        turnstileStatus = false;
    }

    public synchronized void exit(Ticket ticket) {
        museum.visitorCount.decrease();
        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " exited through Turnstile " + this.turnstileID
                + "; Visitors count = " + museum.visitorCount.getNumber());
    }

    public synchronized boolean getTurnstileStatus() {
        return turnstileStatus;
    }
}
