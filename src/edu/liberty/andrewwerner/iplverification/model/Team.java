package edu.liberty.andrewwerner.iplverification.model;

import java.util.ArrayList;

/**
 * Class Team
 * Represents a team registered for an event.
 *
 * @author Andrew Werner
 */
public final class Team implements IMutableTeam {
    private final String teamId;
    private final String remoteId;
    private final String name;
    private final String url;
    private final int regOrder;
    private final boolean dropped;
    private final IEvent event;
    private VerificationStatus verificationStatus;
    private String verificationNote;
    private final IDataKey dataKey;
    private ArrayList<IPlayer> roster;

    /**
     * Creates a new Team object. Can only be done within the model package.
     * @param teamId internal ID of the team.
     * @param remoteId remote API's team ID.
     * @param name team's name.
     * @param url URL to open the team's page on the remote bracket site.
     * @param dropped Did this team drop from the event after registering?
     * @param event The event this team is associated with.
     * @param verificationStatus The team's verification status.
     * @param verificationNote The team's verification note.
     */
    Team(String teamId, String remoteId, String name, String url, int regOrder, boolean dropped,
         IEvent event, VerificationStatus verificationStatus, String verificationNote) {
        this.teamId = teamId;
        this.remoteId = remoteId;
        this.name = name;
        this.url = url;
        this.regOrder = regOrder;
        this.dropped = dropped;
        this.event = event;
        this.verificationStatus = verificationStatus;
        this.verificationNote = verificationNote;
        this.dataKey = new DataKey(this.getEvent().getId(), teamId, null);
        this.roster = null;
    }

    @Override
    public String getId() {
        return this.teamId;
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
    public int getRegistrationOrder() {
        return this.regOrder;
    }

    @Override
    public boolean isDropped() {
        return this.dropped;
    }

    @Override
    public IEvent getEvent() {
        return this.event;
    }

    @Override
    public VerificationStatus getVerificationStatus() {
        return this.verificationStatus;
    }

    @Override
    public String getVerificationNote() {
        return this.verificationNote;
    }

    @Override
    public ArrayList<IPlayer> getRoster() {
        return this.roster;
    }

    @Override
    public void addToRoster(IPlayer player) {
        if (this.roster == null) {
            this.roster = new ArrayList<>();
        }

        this.roster.add(player);
    }

    @Override
    public void updateVerificationDetails(VerificationStatus status, String note) {
        this.verificationStatus = status;
        this.verificationNote = note;
    }

    @Override
    public int compareTo(ITeam otherTeam) {
        IEvent thisEvent = this.getEvent();
        IEvent otherEvent = otherTeam.getEvent();
        if (!thisEvent.getId().equals(otherEvent.getId())) {
            // Event compare
            return thisEvent.compareTo(otherEvent);
        }

        // Team compare
        return this.getRegistrationOrder() - otherTeam.getRegistrationOrder();
    }
}
