package edu.liberty.andrewwerner.iplverification.view;

import javax.swing.JPanel;

/**
 * Interface IView
 * Defines methods that are common among all views.
 *
 * @author Andrew Werner
 */
public interface IView {
    /**
     * Get this view's content panel.
     * The content panel contains the entire GUI and is intended
     * to be loaded into the window.
     * @return the view's content panel.
     */
    JPanel getContentPanel();
}
