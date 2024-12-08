package edu.liberty.andrewwerner.iplverification.model;

import java.time.ZonedDateTime;

/**
 * Interface IEvent
 * Describes an event and provides methods that can be used anywhere in the system.
 * An event is something teams of players can sign up for and participate in (a tournament).
 *
 * @author Andrew Werner
 */
public interface IEvent extends IDataObject, Comparable<IEvent> {
    /**
     * Get the number of teams registered for this event.
     * @return the number of teams registered for this event.
     */
    int getNumTeams();

    /**
     * Get the starting date and time of the event as a human-readable string.
     * @return the starting date and time of the event as a human-readable string.
     */
    String getStartDateString();

    /**
     * Get the starting date and time of the event as a ZonedDateTime instance.
     * @return the starting date and time of the event as a ZonedDateTime instance.
     */
    ZonedDateTime getStartDateTime();
}
