import javafx.application.Platform;

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

    public Time(Museum museum) {
        this.sysStartTime = System.currentTimeMillis();
        this.museum = museum;
    }

    /**
     * Return visitor entry time in time format
     */
    public String getEntryTime() {
        String normalisedTime = String.format("%04d", this.entryTime);
        return normalisedTime;
    }

    /**
     * Return visitor entry time in system time format (millisecond)
     */
    public long getLongEntryTime() {
        return this.entryTime;
    }

    /**
     * Return visitor purchase time in time format
     */
    public String getPurchaseTime() {
        String normalisedTime = String.format("%04d", this.purchaseTime);
        return normalisedTime;
    }

    /**
     * Return visitor purchase time in system time format(millisecond)
     */
    public long getLongPurchaseTime() {
        return this.purchaseTime;
    }

    /**
     * Return visitor purchase duration
     */
    public long getPurchaseDuration() {
        return this.purchaseDuration / 10;
    }

    /**
     * Return visitor visit duration
     */
    public long getVisitDuration() {
        return this.visitDuration / 10;
    }

    /**
     * Return visitor purchase duration in millisecond
     */
    public long getPurchaseDurationInMillis() {
        return this.purchaseDuration;
    }

    /**
     * Return visitor visit duration in millisecond
     */
    public long getVisitDurationInMillis() {
        return this.visitDuration;
    }

    /**
     * Return program start time in time format
     */
    public long getSysStartTime() {
        return this.sysStartTime;
    }

    /**
     * Return program start time in system time format
     */
    public void setSysStartTime(long sysStartTime) {
        this.sysStartTime = sysStartTime;
    }

    /**
     * Return program system current time format
     */
    public long getSysCurrTime() {
        return this.sysCurrTime;
    }

    /**
     * Return normalised current time
     */
    public String getFormattedCurrentTime() {
        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        String normalisedTime = String.format("%04d", this.currentTime);
        return normalisedTime;
    }

    /**
     * Return current time in time format
     */
    public long getCurrentTime() {
        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        return this.currentTime;
    }

    /**
     * Calculate the current time in time format by separate hour and minute, adding
     * together and combine them back
     */
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
        /**
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
        /**
         * Each visitor stays in the museum for 50-150 minutes. The duration of stay
         * will be randomly assigned to the visitor when the visitor is entering the
         * museum.
         */

        this.sysCurrTime = System.currentTimeMillis();
        calcRealtime(this.sysStartTime, this.sysCurrTime, museum.getMuseumTicketOpenTimeInMillis());
        if (this.currentTime < museum.getMuseumOpenTime()) {
            this.entryTime = museum.getMuseumOpenTime();
        } else {
            this.entryTime = this.currentTime;
        }
    }

    /**
     * Set visitor visit duration
     */
    public void setVisitDuration() {
        int randomDuration = (int) ((Math.random() * (150 - 50)) + 50) * 10;
        this.visitDuration = randomDuration;
    }

    /**
     * Calculate the exit time for the visitor with same method applied on
     * calcRealtime()
     */
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

    /**
     * Return visitor exit time
     */
    public long getExitTime() {
        return this.exitTime;
    }

    @Override
    public void run() {

        /**
         * Museum time starts at 8
         */
        int starting_hour = 8;

        /**
         * Hours initialized from 8 to 18 to represent the simulation operating hours
         * from 0800 until 1800.
         */
        for (int hours = starting_hour; hours <= 18; hours++) {

            /**
             * If hours reached 18, break the loop to stop the timer. The number of minutes
             * may slightly exceed 00 when the timer stopped.
             */
            if (hours == 18)
                break;
            else {

                /**
                 * Minutes values are initialized from 0 to 9
                 */
                for (int min = 0; min <= 59; min++) {
                    try {

                        /**
                         * If hours is at 9, change museum status to Museum Open
                         */
                        if (hours == 9 && min == 0) {
                            Platform.runLater(() -> {
                                museum.controller.museumOpen();
                            });
                        }
                        /**
                         * If hours is at 17, change museum status to Ticket Closed
                         */
                        else if (hours == 17 && min == 0) {
                            Platform.runLater(() -> {
                                museum.controller.ticketClosed();
                            });
                        }
                        /**
                         * If hours is at 18, change museum status to Museum Closed
                         */
                        else if (hours == 18 && min == 0) {
                            Platform.runLater(() -> {
                                museum.controller.museumClosed();
                            });
                        }

                        /**
                         * Use Platform.runLater() to update GUI
                         */
                        Platform.runLater(() -> {
                            museum.controller.setTime(Museum.worldTime.getFormattedCurrentTime());
                        });

                        /**
                         * Update Museum Status when current visitor is equal to hourly limits.
                         */
                        if (museum.controller.getMuseumStatus() == "MUSEUM OPEN"
                                || museum.controller.getMuseumStatus() == "MUSEUM FULL") {
                            if (museum.controller.getCurrentVisitor() < museum.controller.getHourlyVisitorLimit()) {
                                Platform.runLater(() -> {
                                    museum.controller.museumOpen();
                                });
                            } else {
                                Platform.runLater(() -> {
                                    museum.controller.museumFull();
                                });
                            }
                        }

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }
}