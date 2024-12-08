package edu.liberty.andrewwerner.iplverification.model;

/**
 * Class Player
 * Represents a player on a team's roster that is registered for an event.
 *
 * @author Andrew Werner
 */
public final class Player implements IMutablePlayer {
    private final String playerId;
    private final IDataKey dataKey;
    private final String remoteId;
    private final String url;
    private final String name;
    private final ITeam team;
    private VerificationStatus verificationStatus;
    private String verificationNote;
    private final boolean dropped;

    /**
     * Creates a new Player object. Can only be done within the model package.
     * @param playerId internal ID of the player.
     * @param remoteId remote API's player ID.
     * @param url URL to open the player's page on the remote bracket site.
     * @param name player's name.
     * @param team the team (and therefore event) this player is playing for during the associated event.
     * @param verificationStatus The player's verification status.
     * @param verificationNote The player's verification note.
     * @param dropped Did this player drop from their team's roster after registering?
     */
    Player(String playerId, String remoteId, String url, String name,
           ITeam team, VerificationStatus verificationStatus, String verificationNote, boolean dropped) {
        this.playerId = playerId;
        this.remoteId = remoteId;
        this.url = url;
        this.name = name;
        this.team = team;
        this.verificationStatus = verificationStatus;
        this.verificationNote = verificationNote;
        this.dropped = dropped;
        this.dataKey = new DataKey(this.getTeam().getEvent().getId(), this.getTeam().getId(), playerId);
    }

    @Override
    public String getId() {
        return this.playerId;
    }

    @Override
    public IDataKey getFullKey() {
        return this.dataKey;
    }

    @Override
    public String getRemoteId() {
        return this.remoteId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBracketSiteURL() {
        return this.url;
    }

    @Override
    public boolean isDropped() {
        return this.dropped;
    }

    @Override
    public ITeam getTeam() {
        return this.team;
    }

    @Override
    public VerificationStatus getVerificationStatus() {
        if (this.verificationStatus.equals(VerificationStatus.Unverified) &&
                !this.team.getVerificationStatus().equals(VerificationStatus.Unverified)) {
            // Team status overrides players unless player's is explicitly set.
            return this.team.getVerificationStatus();
        }

        return this.verificationStatus;
    }

    @Override
    public String getVerificationNote() {
        return this.verificationNote;
    }

    @Override
    public void updateVerificationDetails(VerificationStatus status, String note) {
        this.verificationStatus = status;
        this.verificationNote = note;
    }

    @Override
    public int compareTo(IPlayer otherPlayer) {
        ITeam thisTeam = this.getTeam();
        ITeam otherTeam = otherPlayer.getTeam();
        if (!this.getId().equals(otherPlayer.getId()) &&
            thisTeam.getId().equals(otherTeam.getId()) &&
            thisTeam.getEvent().getId().equals(otherTeam.getEvent().getId())
        ) {
            //Team & Event match, but no player match
            // Sort alphabetically
            return this.getName().compareTo(otherPlayer.getName());
        }

        // All 7 other cases are handled by team sort or event sort (which is handled by team sort)
        return thisTeam.compareTo(otherTeam);
    }
}
