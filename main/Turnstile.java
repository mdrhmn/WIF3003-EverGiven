public class Turnstile {
    private final String turnstileID;
    Museum museum;

    public Turnstile(String turnstileID, Museum museum) {
        this.turnstileID = turnstileID;
        this.museum = museum;
    }

    public String getTurnstileID() {
        return turnstileID;
    }

    public synchronized void entry(Ticket ticket) throws InterruptedException {
        Museum.totalVisitors.increase();
        museum.visitorCount.increase();
        // System.out.println(Thread.currentThread().getName() + ":\t" +
        // Museum.worldTime.getFormattedCurrentTime()
        // + " - Ticket " + ticketID + " entered through Turnstile " + this.turnstileID
        // + "; Visitors count = "
        // + museum.visitorCount.getNumber());
        ticket.visitor.visitorTime.entryTime();

        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " entered through Turnstile " + this.turnstileID
                + ". Staying for " + ticket.visitor.visitorTime.getVisitDuration() + " minutes");

        ticket.visitor.visitorTime.purchaseTime(ticket.visitor.getNoOfTickets());
    }

    public synchronized void exit(Ticket ticket) {
        museum.visitorCount.decrease();
        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " exited through Turnstile " + this.turnstileID
                + "; Visitors count = " + museum.visitorCount.getNumber());
    }
}
