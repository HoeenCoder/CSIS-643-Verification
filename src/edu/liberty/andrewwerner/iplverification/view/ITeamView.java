package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.presenter.ITeamPresenter;

import java.util.ArrayList;

/**
 * Interface ITeamView
 * Defines public methods specific to the team view.
 *
 * @author Andrew Werner
 */
public interface ITeamView extends IView {
    /**
     * Links a ITeamPresenter to this view.
     * Not handled in the constructor as both objects need to refer to each other.
     * Not defined in IView because the type of presenter must be enforced correctly.
     * @param presenter the ITeamPresenter to link this view to.
     */
    void setPresenter(ITeamPresenter presenter);

    /**
     * Update this view to display information using the provided arguments.
     * Not defined in IView because arguments taken will vary from view to view.
     * @param team the team to feature in the view.
     * @param players the players on the team's roster.
     */
    void updateView(ITeam team, ArrayList<IPlayer> players);
}
