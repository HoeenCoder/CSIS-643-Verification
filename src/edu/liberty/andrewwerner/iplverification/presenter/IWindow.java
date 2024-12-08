package edu.liberty.andrewwerner.iplverification.presenter;

import javax.swing.JPanel;

/**
 * Interface IWindow
 * Serves as a wrapper around a JFrame which is the user's viewport into the system.
 * Also serves as a central registry for presenters and makes it easy to swap between views.
 *
 * @author Andrew Werner
 */
public interface IWindow {
    /**
     * Registers a new presenter with this view.
     * The presenter must have a PID that has not already been registered.
     * @param presenter the presenter to register.
     */
    void registerPresenter(IPresenter presenter);

    /**
     * Change the displayed view to the one managed by the presenter specified.
     * @param id the PID of the presenter to swap control to.
     * @param args Any arguments the presenter requires to update its view. Type checking and casting likely required.
     */
    void displayView(IPresenter.PID id, Object... args);

    /**
     * Update the window's associated JFrame with the content provided.
     * Calling this method replaces the JFrame's current contents.
     * The JFrame will not be displayed to the user until the first time this method is called.
     * @param content the GUI content to display to the user.
     */
    void updateDisplay(JPanel content);
}
