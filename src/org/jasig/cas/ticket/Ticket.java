package org.jasig.cas.ticket;

import java.io.Serializable;

/**
 * Interface for the generic concept of a ticket
 */
public interface Ticket extends Serializable {

    /**
     * Method to retrieve the id.
     * 
     * @return the id
     */
    String getId();

    /**
     * Determines if the ticket is expired. Most common implementations might
     * collaborate with <i>ExpirationPolicy </i> strategy.
     * 
     * @see org.jasig.cas.ticket.ExpirationPolicy
     */
    boolean isExpired();

    /**
     * Method to retrive the TicketGrantingTicket that granted this ticket.
     * 
     * @return the ticket or null if it has no parent
     */
    TicketGrantingTicket getGrantingTicket();

    /**
     * Method to return the time the Ticket was created.
     * 
     * @return the time the ticket was created.
     */
    long getCreationTime();
    
    /**
     * Returns the number of times this ticket was used.
     * @return
     */
    int getCountOfUses();
    
}
