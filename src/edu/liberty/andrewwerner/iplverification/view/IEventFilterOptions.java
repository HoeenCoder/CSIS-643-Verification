package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.VerificationStatus;

/**
 * Interface IEventFilterOptions
 * Bundles the filter options from the event view into one transportable object.
 *
 * @author Andrew Werner
 */
public interface IEventFilterOptions {
    /**
     * Get the user's search query.
     * This query is compared to the name of the objects being searched for.
     * If the name starts with this, its kept.
     * @return the user's search query.
     */
    String getQuery();

    /**
     * Gets what verification status all teams should have after filtering.
     * @return what verification status all teams should have after filtering.
     */
    VerificationStatus getVerificationQuery();
}
