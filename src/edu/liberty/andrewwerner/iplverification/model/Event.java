package edu.liberty.andrewwerner.iplverification.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Class Event
 * Represents an event (tournament).
 *
 * @author Andrew Werner
 */
public final class Event implements IEvent {
    private final String eventId;
    private final String remoteId;
    private final String name;
    private final String url;
    private final ZonedDateTime startDate;
    private final int teamCount;
    private final IDataKey dataKey;

    /**
     * Creates a new Event object. Can only be done within the model package.
     * @param eventId internal ID of the event.
     * @param remoteId remote API's event ID.
     * @param name event's name.
     * @param url URL to open the event on the remote bracket site.
     * @param startDate Date and Time the event starts.
     * @param teamCount Number of teams currently registered.
     */
    Event(String eventId, String remoteId, String name, String url, ZonedDateTime startDate, int teamCount) {
        this.eventId = eventId;
        this.remoteId = remoteId;
        this.name = name;
        this.url = url;
        this.startDate = startDate;
        this.teamCount = teamCount;
        this.dataKey = new DataKey(eventId, null, null);
    }

    @Override
    public String getId() {
        return this.eventId;
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
    public int getNumTeams() {
        return this.teamCount;
    }

    @Override
    public String getStartDateString() {
        return this.startDate.format(
            DateTimeFormatter.ofPattern("eeee, MMMM dd uuuu 'at' hh:mm a z")
                .withZone(TimeZone.getDefault().toZoneId())
        );
    }

    @Override
    public ZonedDateTime getStartDateTime() {
        return this.startDate;
    }

    @Override
    public int compareTo(IEvent otherEvent) {
        // Events are listed from newest to oldest
        int timeResult = otherEvent.getStartDateTime().compareTo(this.getStartDateTime());
        if (timeResult != 0) {
            return timeResult;
        }

        // In the event two events start at the same time, sort alphabetically
        return this.getName().compareTo(otherEvent.getName());
    }
}
