package edu.liberty.andrewwerner.iplverification.model;

import java.util.Arrays;

/**
 * Class DataKey
 * Represents a full key used by the database.
 * Keys consist of three sections in the form of E:T:P and at least one must be filled.
 * Primary keys (representing 1 row in the applicable table in the database) are E::, E:T:, and E:T:P keys.
 *
 * @author Andrew Werner
 */
final class DataKey implements IDataKey {
    private static final String[] primaryKeyFormats = {"E::", "E:T:", "E:T:P"};
    private final String eventId;
    private final String teamId;
    private final String playerId;
    private final String fullId;
    private final String idFormat;

    public DataKey(String eventId, String teamId, String playerId) {
        this.eventId = eventId == null ? "" : eventId;
        this.teamId = teamId == null ? "" : teamId;
        this.playerId = playerId == null ? "" : playerId;

        if (this.eventId.equals(this.teamId) && this.eventId.equals(this.playerId) && this.eventId.equals("")) {
            throw new IllegalArgumentException("Error creating DataKey: All three IDs cannot be empty.");
        }

        this.idFormat = String.format("%s:%s:%s",
                this.eventId.equals("") ? "" : "E",
                this.teamId.equals("") ? "" : "T",
                this.playerId.equals("") ? "" : "P");
        this.fullId = String.format("%s:%s:%s",
                this.eventId, this.teamId, this.playerId);
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getTeamId() {
        return teamId;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public String getFullId() {
        return fullId;
    }

    @Override
    public String getIdFormat() {
        return this.idFormat;
    }

    @Override
    public boolean hasMatchingFormat(IDataKey key) {
        return this.getIdFormat().equals(key.getIdFormat());
    }

    @Override
    public boolean isPrimaryKey() {
        return Arrays.stream(primaryKeyFormats).anyMatch(f -> f.equals(this.getIdFormat()));
    }

    @Override
    public IDataKey elevatePrimaryKeyTo(String level) {
        if (!this.isPrimaryKey()) {
            throw new IllegalStateException("Cannot elevate a non-primary key");
        }

        final String format = this.getIdFormat();
        if (level.equals("TEAM")) {
            if (format.contains("P")) {
                return new DataKey(this.getEventId(), this.getTeamId(), null);
            } else {
                // Current object is OK, either is TEAM or EVENT
                return this;
            }
        } else if (level.equals("EVENT")) {
            if (format.contains("T") || format.contains("P")) {
                return new DataKey(this.getEventId(), null, null);
            } else {
                // Current object is OK
                return this;
            }
        } else {
            // Invalid level
            throw new IllegalArgumentException("Invalid level \"" + level + "\".");
        }
    }

    @Override
    public String toString() {
        return this.getFullId();
    }
}
