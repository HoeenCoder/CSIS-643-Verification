package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.presenter.IEventPresenter;

import java.util.ArrayList;

/**
 * Interface IEventView
 * Defines public methods specific to the event view.
 *
 * @author Andrew Werner
 */
public interface IEventView extends IView {
    /**
     * Links a IEventPresenter to this view.
     * Not handled in the constructor as both objects need to refer to each other.
     * Not defined in IView because the type of presenter must be enforced correctly.
     * @param presenter the IEventPresenter to link this view to.
     */
    void setPresenter(IEventPresenter presenter);

    /**
     * Obtain the current set of filters for this view or the default set if reset is true.
     * @param reset if true, the filter options in the view are reset to the default values.
     * @return the filter options for this view.
     */
    IEventFilterOptions getFilters(boolean reset);

    /**
     * Update this view to display information using the provided arguments.
     * Not defined in IView because arguments taken will vary from view to view.
     * @param event the event that the view should feature.
     * @param teams the teams registered for the event.
     */
    void updateView(IEvent event, ArrayList<ITeam> teams);
}
