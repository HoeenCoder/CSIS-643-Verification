package edu.liberty.andrewwerner.iplverification.model;

import java.util.ArrayList;

/**
 * Class APIConnectorStub
 * Stubs this system's ability to connect to a remote API and obtain tournament data.
 * This was designed but will not be implemented in this project as per the design created in week 4.
 */
public final class APIConnectorStub implements APIConnector {
    @Override
    public ArrayList<IEvent> fetchEvents() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<ITeam> fetchParticipants(String remoteEventId) {
        return new ArrayList<>();
    }
}
