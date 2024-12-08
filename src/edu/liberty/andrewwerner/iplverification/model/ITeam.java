package edu.liberty.andrewwerner.iplverification.model;

import java.util.ArrayList;

/**
 * Interface ITeam
 * Describes a team and provides methods that can be used anywhere in the system.
 * A team is a group of players who are participating in a specific event.
 *
 * @author Andrew Werner
 */
public interface ITeam extends IDataObject, Comparable<ITeam> {
    /**
     * Get this team's registration order number.
     * The first team to register is 1, then 2, etc.
     * @return this team's registration order number.
     */
    int getRegistrationOrder();

    /**
     * Determines if the team has dropped from the event this object is associated with.
     * @return true if the team dropped, false if not.
     */
    boolean isDropped();

    /**
     * Get the event this team is associated with (has signed up for).
     * @return the event this team is associated with.
     */
    IEvent getEvent();

    /**
     * Get this team's verification status.
     * @return this team's verification status.
     */
    VerificationStatus getVerificationStatus();

    /**
     * Get this team's verification note.
     * The verification note explains the reasoning behind their verification status.
     * @return this team's verification note.
     */
    String getVerificationNote();

    /**
     * Get this team's roster as an ArrayList if IPlayer objects.
     * This may also return null if the roster has not been loaded.
     * Rosters can be loaded via a request to IDatabase.populateRosters().
     * @return this team's roster or null.
     */
    ArrayList<IPlayer> getRoster();
}
