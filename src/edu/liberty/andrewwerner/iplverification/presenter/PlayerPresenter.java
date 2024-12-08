package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.view.IPlayerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class PlayerPresenter
 * The presenter for the PlayerView.
 *
 * @author Andrew Werner
 */
public final class PlayerPresenter extends Presenter implements IPlayerPresenter {
    private final IPlayerView view;

    /**
     * Creates a new PlayerPresenter object.
     * Only one instance of this class needs to exist at any given time.
     * @param database a reference to the model via IDatabase.
     * @param view the player view this presenter manages.
     */
    public PlayerPresenter(IDatabase database, IPlayerView view) {
        super(PID.Player, database);
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadDisplay(Object... args) {
        // Validate arguments
        if (args.length != 1) {
            throw new IllegalArgumentException("When loading player display, expected 1 argument and got " + args.length);
        }

        IPlayer player;
        if (!(args[0] instanceof IPlayer)) {
            throw new IllegalArgumentException("When loading player display, did not receive an instance of IPlayer.");
        } else {
            player = (IPlayer) args[0];
        }

        this.updateFilters(player, this.view.getFilters(true));
    }

    @Override
    public void updateFilters(IPlayer player, String option) {
        ArrayList<IPlayer> participation = this.getDatabase().getRecentParticipationFor(player).stream()
                .filter(p -> p.getTeam().getEvent().getName().toLowerCase().startsWith(option.toLowerCase()))
                .sorted().collect(Collectors.toCollection(ArrayList::new));

        this.view.updateView(player, participation);
        this.getWindow().updateDisplay(this.view.getContentPanel());
    }

    @Override
    public String updateVerification(IPlayer player, VerificationStatus status, String note) {
        String response = this.getDatabase().updatePlayerVerification(player, status, note);

        // Reload page with new values.
        this.updateFilters(player, this.view.getFilters(false));
        return response;
    }
}
