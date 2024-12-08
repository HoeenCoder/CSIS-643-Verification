package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;

/**
 * Abstract Class Presenter
 * Contains common concrete methods and variables used by all presenters.
 *
 * @author Andrew Werner
 */
public abstract class Presenter implements IPresenter {
    private final PID id;
    private final IDatabase database;
    private IWindow window;

    /**
     * Sets up instance variables related to this abstract superclass.
     * @param id the presenter's unique ID.
     * @param database a reference to the model via IDatabase.
     */
    public Presenter(PID id, IDatabase database) {
        this.id = id;
        this.database = database;
        this.window = null; // Delayed setup
    }

    public final void setWindow(IWindow window) {
        if (this.window != null) {
            throw new IllegalStateException("Window already set on presenter.");
        }

        this.window = window;
    }

    /**
     * Allows a presenter to access the model through interface IDatabase.
     * Only intended to be called within the presenter subclass.
     * @return a reference to the model via interface IDatabase.
     */
    protected final IDatabase getDatabase() {
        return this.database;
    }

    /**
     * Allows a presenter to access the window through interface IWindow.
     * Only intended to be called within the presenter subclass.
     * @return a reference to the window via interface IWindow.
     */
    protected final IWindow getWindow() {
        return window;
    }

    public final PID getID() {
        return this.id;
    }

    public final void changeView(PID id, Object... args) {
        this.window.displayView(id, args);
    }
}
