/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Platform;

public class Turnstile {
    private final String turnstileID;
    Museum museum;
    GUIController controller;
    boolean turnstileStatus;

    public Turnstile(String turnstileID, Museum museum, GUIController controller) {
        this.turnstileID = turnstileID;
        this.museum = museum;
        this.controller = controller;
        turnstileStatus = false;
    }

    public String getTurnstileID() {
        return turnstileID;
    }

    public void entry(Ticket ticket) throws InterruptedException {
        Museum.totalVisitors.increase();
        Museum.visitorCount.increase();

        System.out.println(Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticket.getTicketID()
                + " entered through Turnstile " + this.turnstileID + ". Staying for "
                + ticket.visitor.visitorTime.getVisitDuration() + " minutes; Current visitors count = "
                + Museum.visitorCount.getNumber());

        String text = Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticket.getTicketID()
                + " entered through Turnstile " + this.turnstileID + ". Staying for "
                + ticket.visitor.visitorTime.getVisitDuration() + " minutes; Current visitors count = "
                + Museum.visitorCount.getNumber();

        ticket.ticketTime.setExitTime(ticket.visitor.visitorTime.getVisitDuration(),
                ticket.ticketTime.getLongEntryTime());
        ticket.setEntryStatus(true);
        ticket.visitor.ticketsEnteredCount.increase();

        Platform.runLater(() -> {
            controller.displayText(text);
            controller.visitorEnter();
        });
    }

    public void exit(Ticket ticket) {
        Museum.visitorCount.decrease();

        System.out.println(Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticket.getTicketID()
                + " exited through Turnstile " + this.turnstileID + "; Visitors count = "
                + Museum.visitorCount.getNumber() + "; Total visitors count = " + Museum.totalVisitors.getNumber());

        String text = Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticket.getTicketID()
                + " exited through Turnstile " + this.turnstileID + "; Visitors count = "
                + Museum.visitorCount.getNumber() + "; Total visitors count = " + Museum.totalVisitors.getNumber();
        
        
        ticket.setEntryStatus(false);
        ticket.visitor.ticketsExitCount.increase();

        Platform.runLater(() -> {
            controller.displayText(text);
            controller.visitorExit();
        });
    }

    public boolean getTurnstileStatus() {
        return turnstileStatus;
    }

    public void setTurnstileStatus(boolean turnstileStatus) {
        this.turnstileStatus = turnstileStatus;
    }
}
