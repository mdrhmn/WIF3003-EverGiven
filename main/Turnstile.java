package main;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Platform;

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

    /**
     * Method to handle ticket entry at turnstile
     */
    public void entry(Ticket ticket) throws InterruptedException {

        /**
         * Increase number of total and current visitors
         */
        Museum.totalVisitors.increase();
        Museum.visitorCount.increase();

        System.out.println(Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticket.getTicketID()
                + " entered through Turnstile " + this.turnstileID + ". Staying for "
                + ticket.visitor.visitorTime.getVisitDuration() + " minutes; Current visitors count = "
                + Museum.visitorCount.getNumber());

        /**
         * Set ticket exit time and entry status
         */
        ticket.ticketTime.setExitTime(ticket.visitor.visitorTime.getVisitDuration(),
                ticket.ticketTime.getLongEntryTime());
        ticket.setEntryStatus(true);
        ticket.visitor.ticketsEnteredCount.increase();

        /**
         * Update counters in GUI
         */
        Platform.runLater(() -> {
            museum.controller.increaseEntrance(this.turnstileID);
            museum.controller.dequeueList(ticket.getTicketID());
            museum.controller.appendTicketsEntry(
                    Museum.worldTime.getFormattedCurrentTime() + " hrs - " + ticket.getTicketID() + " ["
                            + this.turnstileID + "] (" + ticket.visitor.visitorTime.getVisitDuration() + " mins)");
            museum.controller.visitorEnter();
        });
    }

    /**
     * Method to handle ticket entry at turnstile
     */
    public void exit(Ticket ticket) {
        Museum.visitorCount.decrease();

        System.out.println(Museum.worldTime.getFormattedCurrentTime() + " - Ticket " + ticket.getTicketID()
                + " exited through Turnstile " + this.turnstileID + "; Visitors count = "
                + Museum.visitorCount.getNumber() + "; Total visitors count = " + Museum.totalVisitors.getNumber());

        ticket.setEntryStatus(false);
        ticket.visitor.ticketsExitCount.increase();

        Platform.runLater(() -> {
            museum.controller.increaseExit(this.turnstileID);
            museum.controller.appendTicketsExit(Museum.worldTime.getFormattedCurrentTime() + " hrs - "
                    + ticket.getTicketID() + " [" + this.turnstileID + "]");
            museum.controller.visitorExit();
        });
    }

    public boolean getTurnstileStatus() {
        return turnstileStatus;
    }

    public void setTurnstileStatus(boolean turnstileStatus) {
        this.turnstileStatus = turnstileStatus;
    }
}
