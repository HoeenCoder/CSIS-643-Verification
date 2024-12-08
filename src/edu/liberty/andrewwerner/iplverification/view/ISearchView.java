package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.IDataObject;
import edu.liberty.andrewwerner.iplverification.presenter.ISearchPresenter;

import java.util.ArrayList;

/**
 * Interface ISearchView
 * Defines public methods specific to the search view.
 *
 * @author Andrew Werner
 */
public interface ISearchView extends IView {
    /**
     * Links a ISearchPresenter to this view.
     * Not handled in the constructor as both objects need to refer to each other.
     * Not defined in IView because the type of presenter must be enforced correctly.
     * @param presenter the ISearchPresenter to link this view to.
     */
    void setPresenter(ISearchPresenter presenter);

    /**
     * Obtain the current set of filters for this view or the default set if reset is true.
     * @param reset if true, the filter options in the view are reset to the default values.
     * @return the filter options for this view.
     */
    ISearchFilterOptions getFilters(boolean reset);

    /**
     * Update this view to display information using the provided arguments.
     * Not defined in IView because arguments taken will vary from view to view.
     * @param entities a list of IDataObjects (all the same subtype) to display.
     */
    void updateView(ArrayList<? extends IDataObject> entities);
}
