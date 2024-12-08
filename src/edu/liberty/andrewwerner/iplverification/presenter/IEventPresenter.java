package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.IEvent;
import edu.liberty.andrewwerner.iplverification.view.IEventFilterOptions;

/**
 * Interface IEventPresenter
 * Defines public methods specific to the event page's presenter.
 *
 * @author Andrew Werner
 */
public interface IEventPresenter extends IPresenter {
    /**
     * Prompt this presenter to obtain new information for the view based on
     * the filter options provided.
     * @param event the event to fetch information about.
     * @param options filter options to use when querying the model for data.
     */
    void updateFilters(IEvent event, IEventFilterOptions options);

    /**
     * Triggers an import to update this event from the remote API.
     * The presenter itself cannot do this, so it will contact the model to do this.
     * API imports are currently stubbed in this project and will not actually import anything.
     * @param event the event to update.
     * @return a string describing the result of the import (error, success message).
     */
    String importTeams(IEvent event);
}
