package edu.liberty.andrewwerner.iplverification.model;

import java.awt.Color;

/**
 * Enumeration VerificationStatus
 * Represents the different verification states a player or team can be in at any point.
 * These include:
 * Unverified: This player or team has not been verified yet.
 * Eligible: This player or team is permitted to play in this event.
 * NoData: There isn't enough data on this player or team to verify at this time.
 * Questionable: This player or team has questionable results that may or may not make them ineligible.
 * Ineligible: This player or team is NOT eligible to play in this event and will be removed if they have not already been.
 *
 * @author Andrew Werner
 */
public enum VerificationStatus {
    Unverified(0, "Unverified", new Color(255,255,255)), // #ffffff
    Eligible(1, "Eligible", new Color(52,168,83)), // #34A853
    NoData(2, "No Data", new Color(251,188,4)), // #FBBC04
    Questionable(3, "Questionable", new Color(255,109,1)), // #FF6D01
    Ineligible(4, "Ineligible", new Color(234,67,53)); // #EA4335

    private final int id;
    private final String displayName;
    private final Color color;

    /**
     * Sets up the various VerificationStatus objects.
     * @param id ID code for each status, used in the database.
     * @param displayName human-readable display name for each status.
     * @param color color of each status colored indicator.
     */
    VerificationStatus(int id, String displayName, Color color) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
    }

    /**
     * Get the status ID code.
     * @return the status ID code.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the status human-readable display name.
     * @return the status human-readable display name.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Get the status color indicator.
     * @return the status color indicator.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Converts an ID number into a verification status object.
     * @param id the id number to convert.
     * @return the resulting verification status.
     */
    public static VerificationStatus idToStatus(int id) {
        for (VerificationStatus status : VerificationStatus.values()) {
            if (status.getId() == id) {
                return status;
            }
        }

        throw new IllegalArgumentException("Tried to convert invalid ID \"" + id + "\" into a VerificationStatus instance.");
    }
}
