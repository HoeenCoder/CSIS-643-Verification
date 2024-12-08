package edu.liberty.andrewwerner.iplverification.presenter;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Class Window
 * Serves as a wrapper around a JFrame which is the user's viewport into the system.
 * Also serves as a central registry for presenters and makes it easy to swap between views.
 *
 * @author Andrew Werner
 */
public final class Window implements IWindow {
    private final ArrayList<IPresenter> presenters;
    private JFrame window = null;

    /**
     * Creates a new instance of class Window.
     * Only one window object should need to exist at any point.
     */
    public Window() {
        this.presenters = new ArrayList<>();
    }

    @Override
    public void registerPresenter(IPresenter presenter) {
        for (IPresenter existingPresenter : this.presenters) {
            if (existingPresenter.getID() == presenter.getID()) {
                // No duplicates allowed
                throw new IllegalStateException("Duplicate presenter IDs found: " + presenter.getID());
            }
        }

        presenter.setWindow(this);
        this.presenters.add(presenter);
    }

    @Override
    public void displayView(IPresenter.PID id, Object... args) {
        for (IPresenter presenter : presenters) {
            if (presenter.getID() == id) {
                // Correct presenter found, invoke it
                presenter.loadDisplay(args);
                return;
            }
        }

        throw new IllegalStateException("Unable to swap views, presenter with ID \"" + id + "\" not found!");
    }

    @Override
    public void updateDisplay(JPanel content) {
        if (this.window == null) {
            this.window = new JFrame("IPL Verification System");
            this.window.setContentPane(content);
            this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.window.setSize(700, 650);
            this.window.setVisible(true);
        } else {
            this.window.setContentPane(content);
            this.window.revalidate();
            this.window.repaint();
        }
    }
}
