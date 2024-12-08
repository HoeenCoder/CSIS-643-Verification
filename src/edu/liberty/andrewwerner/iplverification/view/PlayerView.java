package edu.liberty.andrewwerner.iplverification.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import edu.liberty.andrewwerner.iplverification.model.IPlayer;
import edu.liberty.andrewwerner.iplverification.model.VerificationStatus;
import edu.liberty.andrewwerner.iplverification.presenter.IPlayerPresenter;
import edu.liberty.andrewwerner.iplverification.presenter.IPresenter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Class PlayerView
 * Generates and maintains the GUI for the player page.
 *
 * @author Andrew Werner
 */
public final class PlayerView implements IPlayerView {
    private JLabel playerName;
    private JScrollPane resultsPanel;
    private JPanel contentPanel;
    private JLabel playerURL;
    private JLabel teamName;
    private JComboBox<VerificationStatus> verifStatusDropdown;
    private JTextArea verificationNotes;
    private JButton goToTeamButton;
    private JButton goToSearchButton;
    private JTextField searchField;
    private JButton filterButton;
    private JLabel eventName;
    private JButton goToEventButton;
    private JButton saveVerificationChangesButton;
    private JLabel verificationUpdateLabel;

    private final IWidgetFactory factory;
    private IPlayerPresenter presenter;
    private IPlayer player;
    private URLHandler urlHandler;
    private static final String PLACEHOLDER_TEXT = "Search by event name...";

    /**
     * Creates an object of class PlayerView
     * @param factory the widget factory to use for making widgets.
     */
    public PlayerView(IWidgetFactory factory) {
        this.factory = factory;

        // One-time loads
        this.searchField.setForeground(Color.GRAY);
        this.searchField.setText(PLACEHOLDER_TEXT);
        for (VerificationStatus status : VerificationStatus.values()) {
            this.verifStatusDropdown.addItem(status);
        }

        // Event listeners
        this.searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(PLACEHOLDER_TEXT)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(PLACEHOLDER_TEXT);
                }
            }
        });

        this.goToSearchButton.addActionListener(e -> this.presenter.changeView(IPresenter.PID.Search));
        this.goToEventButton.addActionListener(e -> this.presenter.changeView(IPresenter.PID.Event, this.player.getTeam().getEvent()));
        this.goToTeamButton.addActionListener(e -> this.presenter.changeView(IPresenter.PID.Team, this.player.getTeam()));
        this.filterButton.addActionListener(e -> this.presenter.updateFilters(this.player, this.getFilters(false)));
        this.saveVerificationChangesButton.addActionListener(e -> {
            VerificationStatus newStatus = this.verifStatusDropdown.getItemAt(this.verifStatusDropdown.getSelectedIndex());
            String newNote = this.verificationNotes.getText();
            if (this.player.getVerificationStatus() == newStatus && this.player.getVerificationNote().equals(newNote)) {
                this.verificationUpdateLabel.setText("No changes to save.");
                return;
            }

            this.saveVerificationChangesButton.setEnabled(false);
            this.verificationUpdateLabel.setText("Saving Changes...");

            String result = this.presenter.updateVerification(this.player, newStatus, newNote);

            this.verificationUpdateLabel.setText(result);
            this.saveVerificationChangesButton.setEnabled(true);
        });
    }

    @Override
    public void setPresenter(IPlayerPresenter presenter) {
        if (this.presenter != null) {
            throw new IllegalStateException("Player Presenter already set!");
        }

        this.presenter = presenter;
    }

    @Override
    public String getFilters(boolean reset) {
        if (reset) {
            this.searchField.setText(PLACEHOLDER_TEXT);
        }

        String query = this.searchField.getText();
        if (query.equals(PLACEHOLDER_TEXT)) {
            query = "";
        }

        return query;
    }

    @Override
    public void updateView(IPlayer player, ArrayList<IPlayer> participation) {
        this.player = player;
        this.playerName.setText(this.player.getName());
        if (this.urlHandler == null) {
            this.urlHandler = new URLHandler(this.playerURL, this.player.getBracketSiteURL());
        } else {
            this.urlHandler.updateLabel(this.player.getBracketSiteURL());
        }
        this.teamName.setText("Team: " + this.player.getTeam().getName());
        this.eventName.setText("Event: " + this.player.getTeam().getEvent().getName());

        this.verifStatusDropdown.setSelectedItem(this.player.getVerificationStatus());
        this.verificationNotes.setText(this.player.getVerificationNote());
        this.verificationUpdateLabel.setText("");

        JPanel wrapper = this.factory.buildWidgets(this.presenter, participation, true);
        this.resultsPanel.setViewportView(wrapper);
        this.resultsPanel.getVerticalScrollBar().setUnitIncrement(10);
    }

    @Override
    public JPanel getContentPanel() {
        return this.contentPanel;
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
        contentPanel.setLayout(new GridLayoutManager(13, 2, new Insets(10, 10, 10, 10), -1, -1));
        playerName = new JLabel();
        Font playerNameFont = this.$$$getFont$$$(null, Font.BOLD, 16, playerName.getFont());
        if (playerNameFont != null) playerName.setFont(playerNameFont);
        playerName.setText("[Player Name]");
        contentPanel.add(playerName, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resultsPanel = new JScrollPane();
        contentPanel.add(resultsPanel, new GridConstraints(12, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        playerURL = new JLabel();
        playerURL.setText("[Player URL]");
        contentPanel.add(playerURL, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        teamName = new JLabel();
        teamName.setText("[Team Name]");
        contentPanel.add(teamName, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        contentPanel.add(separator1, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Eligibility: ");
        contentPanel.add(label1, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Verification Notes:");
        contentPanel.add(label2, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        verifStatusDropdown = new JComboBox();
        contentPanel.add(verifStatusDropdown, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        verificationNotes = new JTextArea();
        verificationNotes.setLineWrap(true);
        contentPanel.add(verificationNotes, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        goToTeamButton = new JButton();
        goToTeamButton.setText("Back to Team");
        contentPanel.add(goToTeamButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goToSearchButton = new JButton();
        goToSearchButton.setText("Go Back to Main Menu");
        contentPanel.add(goToSearchButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        contentPanel.add(separator2, new GridConstraints(9, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        searchField = new JTextField();
        contentPanel.add(searchField, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        filterButton = new JButton();
        filterButton.setText("Filter Events");
        contentPanel.add(filterButton, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eventName = new JLabel();
        eventName.setText("[Event Name]");
        contentPanel.add(eventName, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goToEventButton = new JButton();
        goToEventButton.setText("Back to Event");
        contentPanel.add(goToEventButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveVerificationChangesButton = new JButton();
        saveVerificationChangesButton.setText("Save Verification Changes");
        contentPanel.add(saveVerificationChangesButton, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        verificationUpdateLabel = new JLabel();
        verificationUpdateLabel.setText("");
        contentPanel.add(verificationUpdateLabel, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Recent Participation");
        contentPanel.add(label3, new GridConstraints(10, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Player Verification Status");
        contentPanel.add(label4, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
