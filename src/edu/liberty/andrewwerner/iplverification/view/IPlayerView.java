package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.IPlayer;
import edu.liberty.andrewwerner.iplverification.presenter.IPlayerPresenter;

import java.util.ArrayList;

/**
 * Interface IPlayerView
 * Defines public methods specific to the player view.
 *
 * @author Andrew Werner
 */
public interface IPlayerView extends IView {
    /**
     * Links a IPlayerPresenter to this view.
     * Not handled in the constructor as both objects need to refer to each other.
     * Not defined in IView because the type of presenter must be enforced correctly.
     * @param presenter the IPlayerPresenter to link this view to.
     */
    void setPresenter(IPlayerPresenter presenter);

    /**
     * Obtain the current filter for this view or the default if reset is true.
     * @param reset if true, the filter in the view is reset to the default value.
     * @return the filter option for this view.
     */
    String getFilters(boolean reset);

    /**
     * Update this view to display information using the provided arguments.
     * Not defined in IView because arguments taken will vary from view to view.
     * @param player the player to feature in the view.
     * @param participation recent participation of the player in other events and/or on other teams.
     */
    void updateView(IPlayer player, ArrayList<IPlayer> participation);
}
