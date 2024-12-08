package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.view.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class EventPresenter
 * The presenter for the EventView.
 *
 * @author Andrew Werner
 */
public final class EventPresenter extends Presenter implements IEventPresenter {
    private final IEventView view;

    /**
     * Creates a new EventPresenter object.
     * Only one instance of this class needs to exist at any given time.
     * @param database a reference to the model via IDatabase.
     * @param view the event view this presenter manages.
     */
    public EventPresenter(IDatabase database, IEventView view) {
        super(PID.Event, database);
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDisplay(Object... args) {
        // Validate arguments
        if (args.length != 1) {
            throw new IllegalArgumentException("When loading event display, expected 1 argument and got " + args.length);
        }

        IEvent event;
        if (!(args[0] instanceof IEvent)) {
            throw new IllegalArgumentException("When loading event display, did not receive an instance of IEvent.");
        } else {
            event = (IEvent) args[0];
        }

        IEventFilterOptions filters = this.view.getFilters(true);

        ArrayList<ITeam> teams = this.getDatabase().getEventTeams(event,
                filters.getQuery(), filters.getVerificationQuery(), false);
        this.getDatabase().populateRosters(teams);
        Collections.sort(teams);

        this.view.updateView(event, teams);
        this.getWindow().updateDisplay(this.view.getContentPanel());
    }

    @Override
    public void updateFilters(IEvent event, IEventFilterOptions options) {
        ArrayList<ITeam> teams = this.getDatabase().getEventTeams(event,
                options.getQuery(), options.getVerificationQuery(), false);
        this.getDatabase().populateRosters(teams);
        Collections.sort(teams);

        this.view.updateView(event, teams);
        this.getWindow().updateDisplay(this.view.getContentPanel());
    }

    /**
     * Called by the view to request the database update the registrants for a given event.
     * This method is currently a stub as the API system is not implemented.
     * @param event the event to update.
     * @return a string describing what happened.
     */
    @Override
    public String importTeams(IEvent event) {
        return this.getDatabase().fetchEventRegistrants(event);
    }
}
