package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.view.ITeamView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class TeamPresenter
 * The presenter for the TeamView.
 *
 * @author Andrew Werner
 */
public final class TeamPresenter extends Presenter implements ITeamPresenter {
    private final ITeamView view;

    /**
     * Creates a new TeamPresenter object.
     * Only one instance of this class needs to exist at any given time.
     * @param database a reference to the model via IDatabase.
     * @param view the team view this presenter manages.
     */
    public TeamPresenter(IDatabase database, ITeamView view) {
        super(PID.Team, database);
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadDisplay(Object... args) {
        // Validate arguments
        if (args.length != 1) {
            throw new IllegalArgumentException("When loading team display, expected 1 argument and got " + args.length);
        }

        ITeam team;
        if (!(args[0] instanceof ITeam)) {
            throw new IllegalArgumentException("When loading team display, did not receive an instance of ITeam.");
        } else {
            team = (ITeam) args[0];
        }

        ArrayList<IPlayer> players = this.getDatabase().getTeamMembers(team);
        Collections.sort(players);

        this.view.updateView(team, players);
        this.getWindow().updateDisplay(this.view.getContentPanel());
    }

    @Override
    public String updateVerification(ITeam team, VerificationStatus status, String note) {
        String response = this.getDatabase().updateTeamVerification(team, status, note);

        // Reload page with new values.
        this.loadDisplay(team);
        return response;
    }
}
