package edu.liberty.andrewwerner.iplverification.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class Database
 * Handles the retrieval and updating of data in the system's database.
 *
 * @author Andrew Werner
 */
public final class Database implements IDatabase {
    private final APIConnector api;
    private final Connection connection;

    /**
     * Create a new database object. Will initiate a connection to the system's database.
     * Only one instance of this class needs to exist at any given time.
     * @param api the bracket website API connector for obtaining remote information.
     */
    public Database(APIConnector api) {
        this.api = api;
        try {
            connection = DriverManager.getConnection("jdbc:derby:verification",
                "veriftool", "csis643");
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database!", e);
        }
    }

    @Override
    public ArrayList<IEvent> searchEvents(String query) {
        try (
            PreparedStatement statement = prepare("SELECT event_id FROM events " +
                "WHERE LOWER(name) LIKE ?",
                query.trim().toLowerCase() + "%");
            ResultSet rows = statement.executeQuery();
        ) {
            ArrayList<IDataKey> eventIds = new ArrayList<>();

            while(rows.next()) {
                eventIds.add(new DataKey(rows.getString("event_id"), null, null));
            }

            return new ArrayList<>(this.getEvents(eventIds).values());
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for events: ", e);
        }
    }

    @Override
    public ArrayList<ITeam> searchTeams(String query) {
        try (
            PreparedStatement statement = prepare("SELECT team_id, event_id FROM eventTeams " +
                "WHERE LOWER(name) LIKE ?",
                query.trim().toLowerCase() + "%");
            ResultSet rows = statement.executeQuery();
        ) {
            ArrayList<IDataKey> teamIds = new ArrayList<>();

            while(rows.next()) {
                teamIds.add(new DataKey(
                        rows.getString("event_id"),
                        rows.getString("team_id"),
                        null));
            }

            return new ArrayList<>(this.getTeams(teamIds).values());
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for teams: ", e);
        }
    }

    @Override
    public ArrayList<IPlayer> searchPlayers(String query) {
        try (
            PreparedStatement statement = prepare("SELECT player_id, team_id, event_id FROM rosters " +
                "WHERE LOWER(name) LIKE ?",
                query.trim().toLowerCase() + "%");
            ResultSet rows = statement.executeQuery();
        ) {
            ArrayList<IDataKey> playerIds = new ArrayList<>();

            while(rows.next()) {
                playerIds.add(new DataKey(
                        rows.getString("event_id"),
                        rows.getString("team_id"),
                        rows.getString("player_id")));
            }

            return new ArrayList<>(this.getPlayers(playerIds).values());
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching for players: ", e);
        }
    }

    @Override
    public ArrayList<ITeam> getEventTeams(IEvent event, String query, VerificationStatus status, boolean includeDropped) {
        String sql = "SELECT team_id FROM eventTeams WHERE event_id = ? AND LOWER(name) LIKE ?";
        ArrayList<Object> args = new ArrayList<>();
        args.add(Integer.parseInt(event.getId()));
        args.add(query + "%");

        if (!includeDropped) {
            sql += " AND dropped = 0";
        }

        if (status != null) {
            sql += " AND verification_status = ?";
            args.add(status.getId());
        }

        try (
            PreparedStatement statement = prepare(sql, args.toArray(new Object[0]));
            ResultSet rows = statement.executeQuery();
        ) {
            ArrayList<IDataKey> keys = new ArrayList<>();
            while (rows.next()) {
                keys.add(new DataKey(event.getId(), rows.getString("team_id"), null));
            }

            return new ArrayList<>(this.getTeams(keys).values());
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting an event's teams: ", e);
        }
    }

    @Override
    public ArrayList<IPlayer> getTeamMembers(ITeam team) {
        ArrayList<IDataKey> key = new ArrayList<>();
        key.add(team.getFullKey());
        return new ArrayList<>(this.getPlayers(key).values());
    }

    @Override
    public ArrayList<IPlayer> getRecentParticipationFor(IPlayer player) {
        ArrayList<IDataKey> key = new ArrayList<>();
        key.add(new DataKey(null, null, player.getId()));
        // getPrimaryKeys called by getPlayers will expand the ::P key provided into many E:T:P keys
        // and will result in a variety of objects being returned.
        ZonedDateTime cutoff = LocalDateTime.now().minusMonths(3).atZone(TimeZone.getDefault().toZoneId());
        return this.getPlayers(key).values().stream()
                // Remove instances that are > 3 months old
                .filter(p -> p.getTeam().getEvent().getStartDateTime().isAfter(cutoff))
                // Don't include the provided player
                .filter(p -> !p.getFullKey().getFullId().equals(player.getFullKey().getFullId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void populateRosters(ArrayList<ITeam> teams) {
        ArrayList<IPlayer> teamMembers = new ArrayList<>(
                this.getPlayers(teams.stream()
                .map(ITeam::getFullKey)
                .collect(Collectors.toCollection(ArrayList::new))).values()
        );

        ArrayList<String> teamIds = teams.stream().map(ITeam::getFullKey).map(IDataKey::getFullId)
                .collect(Collectors.toCollection(ArrayList::new));

        for (IPlayer player : teamMembers) {
            int index = teamIds.indexOf(player.getTeam().getFullKey().getFullId());
            if (index < 0) {
                // Should never happen
                throw new IllegalStateException("Negative index when populating rosters");
            }

            // Safe cast, all ITeam instances are also IMutableTeam instances
            ((IMutableTeam) teams.get(index)).addToRoster(player);
        }

        // Return value not required, objects updated
    }

    @Override
    public String updateTeamVerification(ITeam team, VerificationStatus status, String note) {
        this.setAutoCommit(false);
        try (
            PreparedStatement statement = prepare("UPDATE eventTeams SET verification_status = ?, "
                + "verification_note = ? WHERE event_id = ? AND team_id = ?",
                status.getId(), note,
                team.getEvent().getId(), team.getId());
        ) {
            statement.executeUpdate();

            // Update team object, safe cast, all ITeams are IMutableTeams
            ((IMutableTeam) team).updateVerificationDetails(status, note);

            return "Changes saved!";
        } catch (SQLException e) {
            this.rollbackTransaction();
            return "An error occurred, changes not saved.";
        } finally {
            this.setAutoCommit(true);
        }
    }

    @Override
    public String updatePlayerVerification(IPlayer player, VerificationStatus status, String note) {
        this.setAutoCommit(false);
        try (
            PreparedStatement statement = prepare("UPDATE rosters SET verification_status = ?, "
                + "verification_note = ? WHERE event_id = ? AND team_id = ? AND player_id = ?",
                status.getId(), note,
                player.getTeam().getEvent().getId(), player.getTeam().getId(), player.getId());
        ) {
            statement.executeUpdate();

            // Update player object, safe cast, all IPlayers are IMutablePlayers
            ((IMutablePlayer) player).updateVerificationDetails(status, note);

            return "Changes saved!";
        } catch (SQLException e) {
            this.rollbackTransaction();
            return "An error occurred, changes not saved.";
        } finally {
            this.setAutoCommit(true);
        }
    }

    @Override
    public String fetchEventRegistrants(IEvent event) {
        int count = this.api.fetchParticipants(event.getRemoteId()).size();
        return String.format("Updated %d teams.", count);
    }

    @Override
    public String fetchNewEvents() {
        int count = this.api.fetchEvents().size();
        return String.format("Added %d events.", count);
    }

    /*
     * Helper Methods
     */

    /**
     * Converts a string containing an SQL statement and arguments into a
     * PreparedStatement that is ready to execute.
     * @param sql the sql statement string.
     * @param args arguments of any type in the order that they should replace the placeholders in the statement.
     * @return a PreparedStatement that is ready to execute.
     * @param <T> the supertype of all provided arguments, typically Object.
     */
    @SafeVarargs
    private final <T> PreparedStatement prepare(String sql, T... args) {
        try {
            PreparedStatement p = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                p.setObject(i + 1, args[i]);
            }

            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error while preparing statement: ", e);
        }
    }

    /**
     * Obtains a map of events from a list of data keys.
     * The method will expand the data keys into primary keys if needed.
     * @param ids An array list of data keys.
     * @return A map where the keys are full primary keys and the values are event objects.
     */
    private Map<String, IEvent> getEvents(ArrayList<IDataKey> ids) {
        // Expand keys into a consistent format (primary keys) if they aren't already
        // This also validates that the keys are consistent
        ids = getPrimaryKeys(ids);

        if (ids.isEmpty()) {
            // No values to get
            return new HashMap<>();
        }

        String keyType = this.getCompositeSQLKey(ids.get(0));
        String sql = String.format("SELECT * FROM events " +
            "WHERE %s IN (%s)", keyType,
            String.join(", ", Collections.nCopies(ids.size(), "?")));

        try (
                PreparedStatement statement = prepare(sql, ids.stream().map(IDataKey::getFullId).toArray());
                ResultSet rows = statement.executeQuery();
        ) {
            Map<String, IEvent> events = new HashMap<>();

            while (rows.next()) {
                String compositeEventId = rows.getString("event_id") + "::";

                events.put(compositeEventId, new Event(
                    rows.getString("event_id"),
                    rows.getString("remote_id"),
                    rows.getString("name"),
                    rows.getString("url"),
                    // Times stored in the DB are in UTC, LocalDateTime is timezone-less.
                    // Mark it as UTC so it converts properly later.
                    rows.getTimestamp("startDate").toLocalDateTime().atZone(ZoneId.of("UTC")),
                    rows.getInt("teamCount")
                ));
            }

            return events;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting events: ", e);
        }
    }

    /**
     * Obtains a map of teams from a list of data keys.
     * The method will expand the data keys into primary keys if needed.
     * @param ids An array list of data keys.
     * @return A map where the keys are full primary keys and the values are team objects.
     */
    private Map<String, ITeam> getTeams(ArrayList<IDataKey> ids) {
        // Expand keys into a consistent format (primary keys) if they aren't already
        // This also validates that the keys are consistent
        ids = getPrimaryKeys(ids);

        if (ids.isEmpty()) {
            // No values to get
            return new HashMap<>();
        }

        String keyType = this.getCompositeSQLKey(ids.get(0), "et", "t", "");
        String sql = String.format("SELECT * FROM eventTeams et INNER JOIN teams t ON et.team_id = t.team_id " +
            "WHERE %s IN (%s)", keyType,
            String.join(", ", Collections.nCopies(ids.size(), "?")));

        HashSet<String> distinctHelper = new HashSet<>();
        Map<String, IEvent> events = getEvents(ids.stream()
                .map(k -> k.elevatePrimaryKeyTo("EVENT"))
                .filter(k -> distinctHelper.add(k.getFullId()))
                .collect(Collectors.toCollection(ArrayList::new)));

        try (
                PreparedStatement statement = prepare(sql, ids.stream().map(IDataKey::getFullId).toArray());
                ResultSet rows = statement.executeQuery();
        ) {
            Map<String, ITeam> teams = new HashMap<>();

            while (rows.next()) {
                String compositeTeamId = rows.getString("event_id") + ":" + rows.getString("team_id") + ":";

                teams.put(compositeTeamId, new Team(
                    rows.getString("team_id"),
                    rows.getString("remote_id"),
                    rows.getString("name"),
                    rows.getString("url"),
                    rows.getInt("reg_order"),
                    rows.getInt("dropped") != 0,
                    events.get(rows.getString("event_id") + "::"),
                    VerificationStatus.idToStatus(rows.getInt("verification_status")),
                    rows.getString("verification_note")
                ));
            }

            return teams;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting teams: ", e);
        }
    }

    /**
     * Obtains a map of players from a list of data keys.
     * The method will expand the data keys into primary keys if needed.
     * @param ids An array list of data keys.
     * @return A map where the keys are full primary keys and the values are player objects.
     */
    private Map<String, IPlayer> getPlayers(ArrayList<IDataKey> ids) {
        // Expand keys into a consistent format (primary keys) if they aren't already
        // This also validates that the keys are consistent
        ids = getPrimaryKeys(ids);

        if (ids.isEmpty()) {
            // No values to get
            return new HashMap<>();
        }

        String keyType = this.getCompositeSQLKey(ids.get(0), "r", "r", "r");
        String sql = String.format("SELECT * FROM rosters r INNER JOIN players p ON r.player_id = p.player_id " +
            "WHERE %s IN (%s)", keyType,
            String.join(", ", Collections.nCopies(ids.size(), "?")));

        HashSet<String> distinctHelper = new HashSet<>();
        Map<String, ITeam> teams = getTeams(ids.stream()
                .map(k -> k.elevatePrimaryKeyTo("TEAM"))
                .filter(k -> distinctHelper.add(k.getFullId()))
                .collect(Collectors.toCollection(ArrayList::new)));

        try (
            PreparedStatement statement = prepare(sql, ids.stream().map(IDataKey::getFullId).toArray());
            ResultSet rows = statement.executeQuery();
        ) {
            Map<String, IPlayer> players = new HashMap<>();

            while (rows.next()) {
                String compositeTeamId = rows.getString("event_id") + ":" + rows.getString("team_id") + ":";
                String compositePlayerId = rows.getString("event_id") + ":" +
                        rows.getString("team_id") + ":" +
                        rows.getString("player_id");

                players.put(compositePlayerId, new Player(
                    rows.getString("player_id"),
                    rows.getString("remote_id"),
                    rows.getString("url"),
                    rows.getString("name"),
                    teams.get(compositeTeamId),
                    VerificationStatus.idToStatus(rows.getInt("verification_status")),
                    rows.getString("verification_note"),
                    rows.getInt("dropped") != 0
                ));
            }

            return players;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting players: ", e);
        }
    }

    /**
     * Converts a list of consistent (same format) data keys into
     * a list of data keys that are considered primary keys.
     * Primary keys have the format E::, E:T:, or E:T:P.
     * @param keys the data objects to validate and convert.
     * @return a list of consistent primary key data objects.
     */
    private ArrayList<IDataKey> getPrimaryKeys(ArrayList<IDataKey> keys) {
        if (keys.isEmpty()) {
            return keys;
        }

        // Validate all keys are of a consistent type
        IDataKey sampleKey = keys.get(0);
        if (keys.stream().anyMatch(k -> !k.hasMatchingFormat(sampleKey))) {
            throw new IllegalArgumentException("Mixed data keys provided.");
        }

        // Determine if we already have a list of primary keys
        if (sampleKey.isPrimaryKey()) {
            // A query wouldn't get anything new, just return what we have.
            return keys;
        }

        boolean includesPlayerIds = sampleKey.getIdFormat().contains("P");
        String sql = String.format("SELECT event_id, team_id%s FROM %s WHERE %s IN (%s)",
                includesPlayerIds ? ", player_id" : "", // 1. keys to get
                includesPlayerIds ? "rosters" : "eventTeams", // 2. table to get keys from
                this.getCompositeSQLKey(sampleKey), // 3. composite key
                String.join(", ", Collections.nCopies(keys.size(), "?"))); // 4. One ? per value to get

        try (
                PreparedStatement statement = prepare(sql, keys.stream().map(IDataKey::getFullId).toArray());
                ResultSet rows = statement.executeQuery();
        ) {
            ArrayList<IDataKey> primaryKeys = new ArrayList<>();

            while (rows.next()) {
                String playerId = null;
                try {
                    playerId = rows.getString("player_id");
                } catch (SQLException ignored) {} // If the column isn't present, keep it as null

                primaryKeys.add(new DataKey(rows.getString("event_id"),
                        rows.getString("team_id"),
                        playerId));
            }

            return primaryKeys;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting primary keys: ", e);
        }
    }

    /**
     * Converts a data key into the necessary SQL statement to fetch similar keys from the database.
     * Returned strings are in the form of "s:s:s" where s is a statement like TRIM(STR(event_id)) or empty.
     * @param key a data key
     * @return a string containing an SQL statement usable in a WHILE statement to fetch similar keys.
     */
    private String getCompositeSQLKey(IDataKey key) {
        return this.getCompositeSQLKey(key, "", "", "");
    }

    /**
     * Converts a data key into the necessary SQL statement to fetch similar keys from the database.
     * Returned strings are in the form of "s:s:s" where s is a statement like TRIM(STR(event_id)) or empty.
     * @param key a data key
     * @param eventPrefix prefix used for the table where event_id will come from in the SQL query.
     * @param teamPrefix prefix used for the table where team_id will come from in the SQL query.
     * @param playerPrefix prefix used for the table where player_id will come from in the SQL query.
     * @return a string containing an SQL statement usable in a WHILE statement to fetch similar keys.
     */
    private String getCompositeSQLKey(IDataKey key, String eventPrefix, String teamPrefix, String playerPrefix) {
        String format = key.getIdFormat();
        String keyType = "";

        if (format.contains("E")) {
            if (eventPrefix.length() > 0) {
                eventPrefix += ".";
            }

            keyType += "TRIM(CAST(" + eventPrefix + "event_id AS CHAR(10))) || ':";
        } else {
            keyType += "':";
        }

        if (format.contains("T")) {
            if (teamPrefix.length() > 0) {
                teamPrefix += ".";
            }

            keyType += "' || TRIM(CAST(" + teamPrefix + "team_id AS CHAR(10))) || ':";
        } else {
            keyType += ":";
        }

        if (format.contains("P")) {
            if (playerPrefix.length() > 0) {
                playerPrefix += ".";
            }

            keyType += "' || TRIM(CAST(" + playerPrefix + "player_id AS CHAR(10)))";
        } else {
            keyType += "'";
        }

        return keyType;
    }

    /**
     * Enable or disable autocommit, handles SQLExceptions.
     * @param status true to enable, false to disable.
     */
    private void setAutoCommit(boolean status) {
        try {
            this.connection.setAutoCommit(status);
        } catch (SQLException e) {
            throw new RuntimeException("Error while setting auto commit status: ", e);
        }
    }

    /**
     * Rollback any ongoing transaction and handle any SQLExceptions.
     */
    private void rollbackTransaction() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Error while rolling back transaction: ", e);
        }
    }
}
