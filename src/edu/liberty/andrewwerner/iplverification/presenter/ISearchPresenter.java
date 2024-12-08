package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.view.ISearchFilterOptions;

/**
 * Interface ISearchPresenter
 * Defines public methods specific to the search page's presenter.
 *
 * @author Andrew Werner
 */
public interface ISearchPresenter extends IPresenter {
    /**
     * Prompt this presenter to obtain new information for the view based on
     * the filter options provided.
     * @param options filter options to use when querying the model for data.
     */
    void updateFilters(ISearchFilterOptions options);

    /**
     * Triggers an import of new events from the remote API.
     * The presenter itself cannot do this, so it will contact the model to do this.
     * API imports are currently stubbed in this project and will not actually import anything.
     * @return a string describing the result of the import (error, success message).
     */
    String importEvents();
}
