package edu.liberty.andrewwerner.iplverification.presenter;

/**
 * Interface IPresenter
 * Lays out the framework common to all presenters.
 * Presenters act as the middleman between views and the model and are responsible
 * for handling requests from both and presenting views to the user.
 *
 * @author Andrew Werner
 */
public interface IPresenter {
    /**
     * Enumeration PID
     * Represents a unique ID for a specific presenter.
     * There is one for each presenter.
     *
     * @author Andrew Werner
     */
    enum PID {Search, Event, Team, Player}

    /**
     * Gets this presenter's unique ID.
     * @return this presenter's unique ID.
     */
    PID getID();

    /**
     * Sets this presenter's window reference.
     * A presenter's window reference starts as null and is set when
     * the presenter is registered with the window.
     * @param window the window this presenter should use.
     */
    void setWindow(IWindow window);

    /**
     * Prompt this presenter to take any steps required to prepare its view
     * and then display it in its window.
     * @param args Any arguments the presenter requires to update its view. Type checking and casting likely required.
     */
    void loadDisplay(Object... args);

    /**
     * Prompt this presenter to hand control over to another presenter.
     * @param id the PID of the presenter that should be swapped to.
     * @param args arguments that the new presenter will require to update its view.
     */
    void changeView(PID id, Object... args);
}
