package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.presenter.IPresenter;
import edu.liberty.andrewwerner.iplverification.model.IDataObject;

import javax.swing.JPanel;
import java.util.ArrayList;

/**
 * Interface IWidgetFactory
 * Constructs widgets. Widgets are small GUI components that are intended to be
 * created and placed into a scrolling list.
 *
 * @author Andrew Werner
 */
public interface IWidgetFactory {
    /**
     * Builds a set of widgets from a provided list of data objects.
     * @param presenter the presenter each widget can refer to when any of its buttons are clicked.
     * @param data The list of data objects to create widgets from.
     * @param displayParents most data objects have parent data objects.
     *                       Optionally a widget can display references to these parents.
     * @return a JPanel containing all the widgets created from the data objects.
     * @param <T> A subclass of interface IDataObject.
     */
    <T extends IDataObject> JPanel buildWidgets(IPresenter presenter, ArrayList<T> data, boolean displayParents);
}
