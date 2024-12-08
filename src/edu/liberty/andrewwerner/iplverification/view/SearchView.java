package edu.liberty.andrewwerner.iplverification.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import edu.liberty.andrewwerner.iplverification.model.IDataObject;
import edu.liberty.andrewwerner.iplverification.presenter.ISearchPresenter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Class SearchView
 * Generates and maintains the GUI for the search page.
 *
 * @author Andrew Werner
 */
public final class SearchView implements ISearchView {
    private JPanel contentPanel;
    private JTextField searchField;
    private JButton updateFiltersButton;
    private JButton importEventsButton;
    private JLabel importResponse;
    private JScrollPane resultsPanel;
    private JRadioButton eventsRadioButton;
    private JRadioButton teamsRadioButton;
    private JRadioButton playersRadioButton;

    private final ButtonGroup radioGroup;
    private ISearchPresenter presenter;
    private final IWidgetFactory factory;

    /**
     * Creates an object of class SearchView
     * @param factory the widget factory to use for making widgets.
     */
    public SearchView(IWidgetFactory factory) {
        this.presenter = null;
        this.factory = factory;

        this.radioGroup = new ButtonGroup();
        this.radioGroup.add(eventsRadioButton);
        this.radioGroup.add(teamsRadioButton);
        this.radioGroup.add(playersRadioButton);

        // Event listeners
        this.updateFiltersButton.addActionListener(e -> this.presenter.updateFilters(this.getFilters(false)));
    }

    @Override
    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    @Override
    public void setPresenter(ISearchPresenter presenter) {
        if (this.presenter != null) {
            throw new IllegalStateException("Search Presenter already set!");
        }

        this.presenter = presenter;
    }

    @Override
    public ISearchFilterOptions getFilters(boolean reset) {
        if (reset) {
            // Reset all filters to defaults
            this.searchField.setText("");
            this.radioGroup.setSelected(this.eventsRadioButton.getModel(), true);
        }

        ISearchFilterOptions.SearchType type;
        if (this.eventsRadioButton.isSelected()) {
            type = ISearchFilterOptions.SearchType.EVENT;
        } else if (this.teamsRadioButton.isSelected()) {
            type = ISearchFilterOptions.SearchType.TEAM;
        } else {
            // Can't select none
            type = ISearchFilterOptions.SearchType.PLAYER;
        }

        return new SearchFilterOptions(this.searchField.getText(), type);
    }

    @Override
    public void updateView(ArrayList<? extends IDataObject> data) {
        // Disabled stubbed API system
        this.importEventsButton.setEnabled(false);
        this.importResponse.setText("Importing of events is not available.");

        JPanel wrapper = this.factory.buildWidgets(this.presenter, data, true);
        this.resultsPanel.setViewportView(wrapper);
        this.resultsPanel.getVerticalScrollBar().setUnitIncrement(10);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(5, 4, new Insets(10, 10, 10, 10), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Verification Tool");
        contentPanel.add(label1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultsPanel = new JScrollPane();
        contentPanel.add(resultsPanel, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        resultsPanel.setViewportView(panel1);
        final JLabel label2 = new JLabel();
        label2.setText("Loading, Please Wait...");
        panel1.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchField = new JTextField();
        contentPanel.add(searchField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        updateFiltersButton = new JButton();
        updateFiltersButton.setText("Update Filters");
        contentPanel.add(updateFiltersButton, new GridConstraints(2, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importEventsButton = new JButton();
        importEventsButton.setText("Import Events");
        contentPanel.add(importEventsButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importResponse = new JLabel();
        importResponse.setText("");
        contentPanel.add(importResponse, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Search by Name:");
        contentPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eventsRadioButton = new JRadioButton();
        eventsRadioButton.setSelected(true);
        eventsRadioButton.setText("Show Events");
        contentPanel.add(eventsRadioButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        teamsRadioButton = new JRadioButton();
        teamsRadioButton.setText("Show Teams");
        contentPanel.add(teamsRadioButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        playersRadioButton = new JRadioButton();
        playersRadioButton.setText("Show Players");
        contentPanel.add(playersRadioButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
