import javafx.application.Platform;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Time implements Runnable {
    private long sysStartTime;
    private long sysCurrTime;
    private long currentTime;
    private long purchaseTime;
    private long purchaseDuration;
    private long visitDuration;
    private long entryTime;
    private long exitTime;

    Museum museum;
    // GUIController controller;

    public Time(Museum museum) {
        this.sysStartTime = System.currentTimeMillis();
        this.museum = museum;
    }

    public String getEntryTime() {
        String normalisedTime = String.format("%04d", this.entryTime);
        return normalisedTime;
    }

    public long getLongEntryTime() {
        return this.entryTime;
    }

    public String getPurchaseTime() {
        String normalisedTime = String.format("%04d", this.purchaseTime);
        return normalisedTime;
    }

    public long getLongPurchaseTime() {
        return this.purchaseTime;
    }

    public long getPurchaseDuration() {
        return this.purchaseDuration / 10;
    }

    public long getVisitDuration() {
        return this.visitDuration / 10;
    }

    public long getPurchaseDurationInMillis() {
        return this.purchaseDuration;
    }

    public long getVisitDurationInMillis() {
        return this.visitDuration;
    }

    public long getSysStartTime() {
        return this.sysStartTime;
    }

    public void setSysStartTime(long sysStartTime) {
        this.sysStartTime = sysStartTime;
    }

    public long getSysCurrTime() {
        return this.sysCurrTime;
    }

    public String getFormattedCurrentTime() {
        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        String normalisedTime = String.format("%04d", this.currentTime);
        return normalisedTime;
    }

    public long getCurrentTime() {
        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        return this.currentTime;
    }

    public void calcRealtime(long sysStartTime, long sysCurrTime, long startTime) {
        // startTime = purchase time, enter museum
        // currentTime = System current time but normalised
        long timeElapsed = sysCurrTime - sysStartTime;
        timeElapsed = timeElapsed / 50;
        long hr1 = (timeElapsed / 600);
        long min1 = timeElapsed - (hr1 * 600);

        long hr2 = startTime / 1000;
        long min2 = startTime % 1000;

        long hr3 = (hr1 + hr2) * 1000;
        long min3 = min1 + min2;

        if (min3 >= 600) {
            long hr4 = (min3 / 600) * 1000;
            long min4 = min3 - ((hr4 / 1000) * 600);
            long sum = (hr3 + hr4 + min4) / 10;
            this.currentTime = sum;
        } else {
            this.currentTime = (hr3 + min3) / 10;
        }
    }

    /*
     * First request to purchase tickets will be made at 8.00 a.m.
     */
    public void purchaseTime() throws InterruptedException {
        /*
         * Subsequent purchase will be made every 1-4 minutes. Each purchase will be for
         * 1-4 tickets
         */
        int randomSubsequentPurchase = (int) ((Math.random() * (4 - 1)) + 1) * 10;
        this.purchaseDuration = randomSubsequentPurchase;

        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        this.purchaseTime = this.currentTime;
    }

    public void entryTime() throws InterruptedException {
        /*
         * Each visitor stays in the museum for 50-150 minutes. The duration of stay
         * will be randomly assigned to the visitor when the visitor is entering the
         * museum.
         */
        // int randomDuration = (int) ((Math.random() * (150 - 50)) + 50) * 10;
        // this.visitDuration = randomDuration;

        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        if (this.currentTime < museum.getMuseumOpenTime()) {
            this.entryTime = museum.getMuseumOpenTime();
        } else {
            this.entryTime = this.currentTime;
        }
    }

    public void setVisitDuration() {
        int randomDuration = (int) ((Math.random() * (150 - 50)) + 50) * 10;
        this.visitDuration = randomDuration;
    }

    public void setExitTime(long duration, long startTime) {
        long hr1 = (duration / 60);
        long min1 = duration - (hr1 * 60);

        long hr2 = startTime / 100;
        long min2 = startTime % 100;

        long hr3 = (hr1 + hr2) * 100;
        long min3 = min1 + min2;

        if (min3 >= 60) {
            long hr4 = (min3 / 60) * 100;
            long min4 = min3 - ((hr4 / 100) * 60);
            long sum = hr3 + hr4 + min4;
            this.exitTime = sum;
        } else {
            this.exitTime = hr3 + min3;
        }
    }

    public long getExitTime() {
        return this.exitTime;
    }

    @Override
    public void run() {

        int num = 8;// Initialize integer num with 8 because the ticket selling start at 0800.

        // Initialize integer hours that starting from 8 to 18 which represent
        // the museum start selling ticket from 0800 and the museum closed at 1800.
        for (int hours = num; hours <= 18; hours++) {

            if (hours == 18)
                break;
            else {
                // Initialize integer min starting from 0 to 59 representing minutes in
                // clock minutes start from 00 to 59.
                for (int min = 0; min <= 59; min++) {

                    try {

                        if (hours == 9 && min == 0) {// The time museum open at 0900
                            museum.controller.museumOpen();
                        } else if (hours == 17 && min == 0) {// The time museum about to close at
                            museum.controller.ticketClosed();
                        } else if (hours == 18 && min == 0) {// The time museum close at 1800
                            museum.controller.museumClosed();
                        }

                        Platform.runLater(() -> {
                            museum.controller.setTime(Museum.worldTime.getFormattedCurrentTime());
                        });

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }
}