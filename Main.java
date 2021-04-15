// 
class Museum {
    private static int totalVisitors;
    private int currentVisitors;

    // The museum receives not more than 900 visitors per day
    private int dailyVisitorsLimit;

    // Not more than 100 visitors are allowed in the museum at one time.
    private int currentVisitorsLimit;

    private int openTime;
    private int closeTime;

    private Ticket museumTickets;
    private Time time;
    private Visitor visitors;
    private Turnstile turnstiles;
    private Gateway gateways;
}

class Ticket {
    // Every ticket has an ID in the form of T****, where **** are running numbers
    // from 0001 to 9999.
    private String ticketID;

    // Tickets will be sold from 8.00 a.m. to 5.00 p.m. daily.
    // First request to purchase tickets will be made at 8.00 a.m
    private int ticketOpenTime;
    private int ticketCloseTime;

    // Each purchase will be for 1-4 tickets
    private int minTicket;
    private int maxTicket;

    // Default is false
    private boolean isActive;

    // TODO Subsequent purchase will be made every 1-4 minutes.
    
    // Visitors enter the museum based on the timestamps on their tickets, i.e. visitors who purchased
    // their tickets earlier enter the museum before those purchase their tickets later.
    // If tickets bought before 9:00AM = everyone enters at 9:00AM
    // Else everyone will enter museum at same time they bought the ticket
    private Time ticketTimestamp;
    
}

class Time {
    // TODO Generate timestamps for ticket or visitor time
    // e.g. 0800 Tickets T0001, T0002 sold
    
    // TODO Check duration
}

// Runnable
class Visitor {

    // Each visitor stays in the museum for 50-150 minutes. The duration of stay will be randomly
    // assigned to the visitor when the visitor is entering the museum.
    private int durationOfStay;
    private int enterTime;
    private int exitTime;

    // 1 visitor = 1 ticket, 1 purchase can have 1-4 tickets
    private Ticket ticket;
    private Gateway entrance;
    private Gateway exit;

    // TODO 1 method to handle entrance (randomly determining which entrance and turnstile to use) - relate to Gateway's TODO
}

// Decide the level of thread granularity – e.g. whether to use a thread per
// entrance/exit or per turnstile.
// Runnable
class Gateway {
    // South Entrance (SE) and North Entrance (NE); and two exits –
    // East Exit (EE) and West Exit (WE).
    
    // Entrance or Exit
    private String gatewayType;
    private String gatewayName;

    // Generate 4 turnstiles for each Gateway object
    private Turnstile turnstiles;

    // TODO Figure out how to link Visitor entering/exit to Gateway
}

// Runnable
class Turnstile {
    // e.g. Ticket T0001 entered through Turnstile SET1
    private String turnstileID;
    private Gateway gateway;

    // Update number of visitors in museum (entering/exit)
    // updateVisitors()

    // run() method
}