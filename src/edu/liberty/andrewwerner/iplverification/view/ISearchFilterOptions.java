package edu.liberty.andrewwerner.iplverification.view;

/**
 * Interface ISearchFilterOptions
 * Bundles the filter options from the search view into one transportable object.
 *
 * @author Andrew Werner
 */
public interface ISearchFilterOptions {
    /**
     * Enumeration SearchType
     * Describes what kind of search is being performed, for events, teams, or players.
     *
     * @author Andrew Werner
     */
    enum SearchType {EVENT, TEAM, PLAYER}

    /**
     * Get the user's search query.
     * This query is compared to the name of the objects being searched for.
     * If the name starts with this, its kept.
     * @return the user's search query.
     */
    String getQuery();

    /**
     * Returns what kind of object the user want to search for.
     * Events, teams, or players.
     * @return the kind of object the user wants to search for.
     */
    ISearchFilterOptions.SearchType getSearchType();
}
