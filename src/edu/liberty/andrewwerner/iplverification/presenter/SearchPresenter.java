package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.view.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class SearchPresenter
 * The presenter for the SearchView.
 *
 * @author Andrew Werner
 */
public final class SearchPresenter extends Presenter implements ISearchPresenter {
    private final ISearchView view;

    /**
     * Creates a new SearchPresenter object.
     * Only one instance of this class needs to exist at any given time.
     * @param database a reference to the model via IDatabase.
     * @param view the search view this presenter manages.
     */
    public SearchPresenter(IDatabase database, ISearchView view) {
        super(PID.Search, database);
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadDisplay(Object... args) {
        // Validate
        if (args.length != 0) {
            throw new IllegalArgumentException("When loading search display, expected 0 arguments and got " + args.length);
        }

        ISearchFilterOptions filters = this.view.getFilters(true);
        ArrayList<? extends IDataObject> dataObjects = this.getDataObjects(filters);

        this.view.updateView(dataObjects);
        this.getWindow().updateDisplay(this.view.getContentPanel());
    }

    @Override
    public void updateFilters(ISearchFilterOptions options) {
        ArrayList<? extends IDataObject> dataObjects = this.getDataObjects(options);

        this.view.updateView(dataObjects);
        this.getWindow().updateDisplay(this.view.getContentPanel());
    }

    /**
     * Called by the view to request the database update its event list.
     * This method is currently a stub as the API system is not implemented.
     * @return a string describing what happened.
     */
    @Override
    public String importEvents() {
        return this.getDatabase().fetchNewEvents();
    }

    /*
     * Helper methods
     */

    /**
     * Retrieves a list of events, teams, or players from the database and processes them.
     * @param filters filter options as specified by the user, used to determine what to retrieve.
     * @return A list of IDataObjects that fit the specified filters.
     */
    private ArrayList<? extends IDataObject> getDataObjects(ISearchFilterOptions filters) {
        if (filters.getSearchType() == ISearchFilterOptions.SearchType.EVENT) {
            ArrayList<IEvent> events = this.getDatabase().searchEvents(filters.getQuery());
            Collections.sort(events);
            return events;
        } else if (filters.getSearchType() == ISearchFilterOptions.SearchType.TEAM) {
            ArrayList<ITeam> teams = this.getDatabase().searchTeams(filters.getQuery());
            this.getDatabase().populateRosters(teams);
            Collections.sort(teams);
            return teams;
        } else {
            ArrayList<IPlayer> players = this.getDatabase().searchPlayers(filters.getQuery());
            Collections.sort(players);
            return players;
        }
    }
}
