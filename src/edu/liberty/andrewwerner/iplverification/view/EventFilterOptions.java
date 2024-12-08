package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.VerificationStatus;

/**
 * Class EventFilterOptions
 * Bundles the filter options from the event view into one transportable object.
 *
 * @author Andrew Werner
 */
public final class EventFilterOptions implements IEventFilterOptions {
    private final String query;
    private final VerificationStatus status;

    /**
     * Creates an object of class EventFilterOptions.
     * @param query the user's search query.
     * @param status what verification status all teams should have after filtering.
     */
    public EventFilterOptions(String query, VerificationStatus status) {
        this.query = query;
        this.status = status;
    }

    @Override
    public String getQuery() {
        return this.query;
    }

    @Override
    public VerificationStatus getVerificationQuery() {
        return this.status;
    }
}
