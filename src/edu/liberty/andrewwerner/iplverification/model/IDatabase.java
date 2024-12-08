package edu.liberty.andrewwerner.iplverification.model;

import java.util.ArrayList;

/**
 * Interface IDatabase
 * Handles the retrieval and updating of data in the system's database.
 *
 * @author Andrew Werner
 */
public interface IDatabase {
    /**
     * Search for events based on a provided (but optional) query.
     * @param query the query to search with. Will match any event names that start with this value.
     * @return a list of events that matched the query.
     */
    ArrayList<IEvent> searchEvents(String query);

    /**
     * Search for teams based on a provided (but optional) query.
     * @param query the query to search with. Will match any team names that start with this value.
     * @return a list of teams that matched the query.
     */
    ArrayList<ITeam> searchTeams(String query);

    /**
     * Search for players based on a provided (but optional) query.
     * @param query the query to search with. Will match any players names that start with this value.
     * @return a list of players that matched the query.
     */
    ArrayList<IPlayer> searchPlayers(String query);

    /**
     * Get all teams associated with a specific event.
     * Optionally you can filter the list of teams based on name, verification status, and if they dropped.
     * @param event the event to get teams for.
     * @param query the query to search with. Will match any teams names that start with this value.
     * @param status if not null, only teams that have the provided verification status will be returned.
     * @param includeDropped if true, dropped teams will be included.
     * @return a list of teams that meet the filters provided.
     */
    ArrayList<ITeam> getEventTeams(IEvent event, String query, VerificationStatus status, boolean includeDropped);

    /**
     * Gets a list of players associated with a team.
     * Remember that teams are associated with an event. So the returned players will be
     * on a specific instance of the team that participated in a specific event.
     * @param team the team to get players from.
     * @return a list of players on that team.
     */
    ArrayList<IPlayer> getTeamMembers(ITeam team);

    /**
     * Gets a list of player objects representing a given player's participation
     * in events during the last 3 months.
     * @param player The player to base the list on.
     * @return the list of player objects.
     */
    ArrayList<IPlayer> getRecentParticipationFor(IPlayer player);

    /**
     * Load the roster of the provided list of teams.
     * This isn't done by default to avoid unintentional recursion.
     * @param teams list of teams to load rosters for.
     */
    void populateRosters(ArrayList<ITeam> teams);

    /**
     * Update a team's verification details in both the database and team object itself.
     * @param team the team to update.
     * @param status new verification status.
     * @param note new verification note.
     * @return human-readable string of text indicating the result.
     */
    String updateTeamVerification(ITeam team, VerificationStatus status, String note);

    /**
     * Update a player's verification details in both the database and player object itself.
     * @param player the player to update.
     * @param status new verification status.
     * @param note new verification note.
     * @return human-readable string of text indicating the result.
     */
    String updatePlayerVerification(IPlayer player, VerificationStatus status, String note);

    /**
     * Updates an event in the database with new information from the API.
     * This will update associated teams and players.
     * NOTICE: This method is currently not implemented (is a stub) as
     * the API system is not implemented at this time.
     * @param event the event to fetch registrants for.
     * @return a string describing the result (error, success message).
     */
    String fetchEventRegistrants(IEvent event);

    /**
     * Updates the database with event information from the API.
     * This includes updating existing event info, and adding new ones.
     * NOTICE: this method is currently not implemented (is a stub) as
     * the API system is not implemented at this time.
     * @return a string describing the result (error, success message).
     */
    String fetchNewEvents();
}
