package edu.liberty.andrewwerner.iplverification.model;

/**
 * Interface IPlayer
 * Describes a player and provides methods that can be used anywhere in the system.
 * A player is an individual who is on a team's roster which is participating in an event.
 *
 * @author Andrew Werner
 */
public interface IPlayer extends IDataObject, Comparable<IPlayer> {
    /**
     * Determines if the player has dropped from the team this object is associated with.
     * @return true if the player dropped, false if not.
     */
    boolean isDropped();

    /**
     * Get the team this player is associated with.
     * @return the team this player is associated with.
     */
    ITeam getTeam();

    /**
     * Get this player's verification status.
     * If the player has a status of "unverified" and their team doesn't, this method will return
     * the team's verification status.
     * @return this player's verification status.
     */
    VerificationStatus getVerificationStatus();

    /**
     * Get this player's verification note.
     * The verification note explains the reasoning behind their verification status.
     * This does NOT inherit from the player's team if not set unlike the verification status.
     * @return this player's verification note.
     */
    String getVerificationNote();
}
