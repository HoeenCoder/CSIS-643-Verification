package edu.liberty.andrewwerner.iplverification.view;

/**
 * Class SearchFilterOptions
 * Bundles the filter options from the search view into one transportable object.
 *
 * @author Andrew Werner
 */
public final class SearchFilterOptions implements ISearchFilterOptions {
    private final String query;
    private final ISearchFilterOptions.SearchType searchType;

    /**
     * Creates a new object of class SearchFilterOptions.
     * @param query the user's search query.
     * @param type Returns what kind of object the user want to search for. Events, teams, or players.
     */
    public SearchFilterOptions(String query, ISearchFilterOptions.SearchType type) {
        this.query = query;
        this.searchType = type;
    }

    @Override
    public String getQuery() {
        return this.query;
    }

    @Override
    public ISearchFilterOptions.SearchType getSearchType() {
        return this.searchType;
    }
}
