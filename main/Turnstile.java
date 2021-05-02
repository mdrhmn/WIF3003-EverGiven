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

    public void entry(Ticket ticket) throws InterruptedException {
        Museum.totalVisitors.increase();
        museum.visitorCount.increase();
        System.out.println(Thread.currentThread().getName() + ":\t" + ticket.visitor.visitorTime.getPurchaseTime()
                + " - Ticket " + ticket.getTicketID() + " entered through Turnstile " + this.turnstileID
                + ". Staying for " + ticket.visitor.visitorTime.getVisitDuration() + " minutes");
        // System.out.println(Museum.worldTime.getFormattedCurrentTime());
        ticket.visitor.visitorTime.setExitTime(ticket.visitor.visitorTime.getVisitDuration(), ticket.visitor.visitorTime.getLongPurchaseTime());
        ticket.setEntryStatus(true);
        ticket.visitor.ticketsEnteredCount.increase();
    }

    public synchronized void exit(Ticket ticket) {
        museum.visitorCount.decrease();

        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " exited through Turnstile " + this.turnstileID
                + "; Visitors count = " + museum.visitorCount.getNumber());
        // System.out.println(Museum.worldTime.getFormattedCurrentTime());
        ticket.setEntryStatus(false);
        ticket.visitor.ticketsExitCount.increase();
    }

    public boolean getTurnstileStatus() {
        return turnstileStatus;
    }

    public void setTurnstileStatus(boolean turnstileStatus) {
        this.turnstileStatus = turnstileStatus;
    }
}
