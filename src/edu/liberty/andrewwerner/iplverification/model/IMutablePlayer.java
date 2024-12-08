package edu.liberty.andrewwerner.iplverification.model;

/**
 * Interface IMutablePlayer
 * An interface only usable in package modal that adds additional
 * functionality to interface IPlayer for mutating an IPlayer.
 * The reason for this is to prevent presenter or view package
 * classes from causing the objects to go out of sync with the database.
 *
 * @author Andrew Werner
 */
interface IMutablePlayer extends IPlayer {
    /**
     * Update this player's verification status and note. Can only be used within package modal.
     * @param status the new verification status for the player.
     * @param note the new verification notes for the player, replacing the old one.
     */
    void updateVerificationDetails(VerificationStatus status, String note);
}
