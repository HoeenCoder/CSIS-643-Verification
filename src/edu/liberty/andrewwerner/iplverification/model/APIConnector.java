package edu.liberty.andrewwerner.iplverification.model;

import java.util.ArrayList;

/**
 * Interface APIConnector
 * Describes the required methods that all APIConnector classes must implement.
 *
 * @author Andrew Werner
 */
interface APIConnector {
    /**
     * Requests events from the remote API and returns a list of events found.
     * @return a list of found events with no IDs.
     */
    ArrayList<IEvent> fetchEvents();

    /**
     * Requests teams (and players) for a specific event from the remote API and returns a list of found teams.
     * Players will be included on the team's roster.
     * @param remoteEventId the remote ID of the event to get info for.
     * @return a list of found teams (containing players) both with no IDs.
     */
    ArrayList<ITeam> fetchParticipants(String remoteEventId);
}
