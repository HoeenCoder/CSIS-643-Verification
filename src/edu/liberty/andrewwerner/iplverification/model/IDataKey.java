package edu.liberty.andrewwerner.iplverification.model;

/**
 * Interface IDataKey
 * Represents a full key used by the database.
 * Keys consist of three sections in the form of E:T:P and at least one must be filled.
 * Primary keys (representing 1 row in the applicable table in the database) are E::, E:T:, and E:T:P keys.
 *
 * @author Andrew Werner
 */
public interface IDataKey {
    /**
     * Get the key's event ID.
     * @return the key's event ID.
     */
    String getEventId();

    /**
     * Gets the key's team ID.
     * @return the key's team ID.
     */
    String getTeamId();

    /**
     * Gets the key's player ID.
     * @return the key's player ID.
     */
    String getPlayerId();

    /**
     * Get the full key in the form of E:T:P.
     * For example: 1:2:3, 1::, :2:
     * @return the full key.
     */
    String getFullId();

    /**
     * Get the key's format.
     * Empty values are blank, filled values have a character (E = event, T = team, P = player).
     * For example: E:T:P, E::, :T:
     * @return the key's format.
     */
    String getIdFormat();

    /**
     * Determines if the provided key and this key have the same format.
     * See also: IDataKey.getIDFormat
     * @param key the key to compare with.
     * @return true if they keys have the same format, false if not.
     */
    boolean hasMatchingFormat(IDataKey key);

    /**
     * Determines if this key is a primary key.
     * A primary key has one of the following format: E::, E:T:, E:T:P.
     * These formats contain the information required to extract a single row from
     * the applicable database table (are a primary key for that table).
     * See also: IDataKey.getIDFormat
     * @return true if the key is primary, false if not.
     */
    boolean isPrimaryKey();

    /**
     * Elevates a primary key to the next level.
     * Specifically player -> team (E:T:P -> E:T:), team -> event (E:T: -> E::),
     * or player -> event (E:T:P -> E::).
     * @param level "TEAM" or "EVENT" depending on the desired level of the new key.
     * @return a new IDataKey primary key at the level specified.
     * @throws IllegalStateException if the key is not a primary key.
     */
    IDataKey elevatePrimaryKeyTo(String level);
}
