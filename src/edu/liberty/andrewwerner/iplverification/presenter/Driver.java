package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.view.*;

import javax.swing.*;

/**
 * Class Driver
 * Responsible for setting up the program and GUI when the program is started.
 *
 * @author Andrew Werner
 */
public final class Driver {
    /**
     * Set the program up and displays the window to the user.
     * @param args unused command line arguments.
     */
    public static void main(String[] args) {
        try {
            // Model
            IDatabase db = new Database(new APIConnectorStub());

            // View
            IWidgetFactory factory = new WidgetFactory();
            ISearchView searchView = new SearchView(factory);
            IEventView eventView = new EventView(factory);
            ITeamView teamView = new TeamView(factory);
            IPlayerView playerView = new PlayerView(factory);

            // Presenter
            IWindow window = new Window();
            window.registerPresenter(new SearchPresenter(db, searchView));
            window.registerPresenter(new EventPresenter(db, eventView));
            window.registerPresenter(new TeamPresenter(db, teamView));
            window.registerPresenter(new PlayerPresenter(db, playerView));

            // Display window to user
            window.displayView(IPresenter.PID.Search);
        } catch (Exception e) {
            // Display startup error to user.
            String message = String.format("An error occurred during startup:%n%n%s%n%n%s", e, e.getCause());

            JOptionPane.showMessageDialog(null, message,
                "IPL Verification System", JOptionPane.ERROR_MESSAGE);
        }
    }
}
