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
        Museum.visitorCount.increase();

        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " entered through Turnstile " + this.turnstileID
                + ". Staying for " + ticket.visitor.visitorTime.getVisitDuration() + " minutes; Visitors count = "
                + Museum.visitorCount.getNumber());

        ticket.visitor.visitorTime.setExitTime(ticket.visitor.visitorTime.getVisitDuration(),
                ticket.visitor.visitorTime.getLongEntryTime());

        // System.out.println(
        // Thread.currentThread().getName() + ":\t" +
        // Museum.worldTime.getFormattedCurrentTime() + " - Ticket "
        // + ticket.getTicketID() + " - Purchase Time : " +
        // ticket.visitor.visitorTime.getPurchaseTime());
        // System.out.println(Thread.currentThread().getName() + ":\t" +
        // Museum.worldTime.getFormattedCurrentTime()
        // + " - Ticket " + ticket.getTicketID() + " - Exit Time : " +
        // ticket.visitor.visitorTime.getExitTime());

        ticket.setEntryStatus(true);
        ticket.visitor.ticketsEnteredCount.increase();
    }

    public void exit(Ticket ticket) {
        Museum.visitorCount.decrease();

        System.out.println(Thread.currentThread().getName() + ":\t" + Museum.worldTime.getFormattedCurrentTime()
                + " - Ticket " + ticket.getTicketID() + " exited through Turnstile " + this.turnstileID
                + "; Visitors count = " + Museum.visitorCount.getNumber() + "; Total visitors count = "
                + Museum.totalVisitors.getNumber());

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
