package edu.liberty.andrewwerner.iplverification.model;

/**
 * Interface IMutableTeam
 * An interface only usable in package modal that adds additional
 * functionality to interface ITeam for mutating an ITeam.
 * The reason for this is to prevent presenter or view package
 * classes from causing the objects to go out of sync with the database.
 *
 * @author Andrew Werner
 */
interface IMutableTeam extends ITeam {
    /**
     * Adds a player to the team's roster. Can only be used within package modal.
     * Will convert the roster from null if it currently is null.
     * @param player the player to add.
     */
    void addToRoster(IPlayer player);
    /**
     * Update this team's verification status and note. Can only be used within package modal.
     * @param status the new verification status for the team.
     * @param note the new verification notes for the team, replacing the old one.
     */
    void updateVerificationDetails(VerificationStatus status, String note);
}
