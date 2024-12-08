package edu.liberty.andrewwerner.iplverification.view;

import javax.swing.*;

/**
 * Interface IWidget
 * A Widget, which is a small GUI component intended to be placed in a scrolling list.
 *
 * @author Andrew Werner
 */
public interface IWidget {
    /**
     * Get this widget's content panel.
     * The content panel contains the entire widget's contents.
     * @return the widget's content panel.
     */
    JPanel getContentPanel();
}
