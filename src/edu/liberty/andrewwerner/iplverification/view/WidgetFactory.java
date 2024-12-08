package edu.liberty.andrewwerner.iplverification.view;

import edu.liberty.andrewwerner.iplverification.model.*;
import edu.liberty.andrewwerner.iplverification.presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class WidgetFactory
 * Creates JPanels containing widgets.
 *
 * @author Andrew Werner
 */
public final class WidgetFactory implements IWidgetFactory {
    private static final int MAXIMUM_RESULTS = 128; // Most events do not exceed this number of teams

    /**
     * Create widgets from a list of data objects.
     * @param presenter the presenter data objects can refer to when buttons are clicked.
     * @param data the list of data objects.
     * @param displayParents should parent information for teams and players be shown?
     * @return a JPanel containing IWidgets.
     * @param <T> type IDataObject or subclasses.
     */
    @Override
    public <T extends IDataObject> JPanel buildWidgets(IPresenter presenter, ArrayList<T> data, boolean displayParents) {
        JPanel wrapper = new JPanel();
        if (data.isEmpty()) {
            wrapper.setLayout(new GridLayout(1, 1));
            wrapper.add(new NoResultsWidget().getContentPanel());
            return wrapper;
        }

        // Max + 1 so the "too many results" widget can fit.
        wrapper.setLayout(new GridLayout(Math.min(data.size(), MAXIMUM_RESULTS + 1), 1));

        for (int i = 0; i < Math.min(data.size(), MAXIMUM_RESULTS); i++) {
            // Make an object based on subclass type
            wrapper.add(this.makeWidget(presenter, data.get(i), displayParents).getContentPanel());
        }

        if (data.size() > MAXIMUM_RESULTS) {
            wrapper.add(new MoreResultsWidget(data.size() - MAXIMUM_RESULTS).getContentPanel());
        }

        return wrapper;
    }

    /*
     * Helper Methods
     */

    /**
     * Create a widget from a generic IDataObject.
     * instanceOf checks are used to determine what kind of widget to return.
     * @param presenter the presenter all data widgets require.
     * @param dataObject the data object to make the widget from/
     * @param displayParents should parent object data be shown in the widget?
     * @return an object implementing IWidget
     */
    private IWidget makeWidget(IPresenter presenter, IDataObject dataObject, boolean displayParents) {
        if (dataObject instanceof IEvent) {
            return new EventWidget(presenter, (IEvent) dataObject);
        } else if (dataObject instanceof ITeam) {
            return new TeamWidget(presenter, (ITeam) dataObject, displayParents);
        } else if (dataObject instanceof IPlayer) {
            return new PlayerWidget(presenter, (IPlayer) dataObject, displayParents);
        } else {
            // Should never happen, but I want a loud message if it does!
            throw new IllegalArgumentException("Unhandled widget type!");
        }
    }
}
