package edu.liberty.andrewwerner.iplverification.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class URLHandler
 * Manages a JLabel so that it acts like a hyperlink.
 *
 * @author Andrew Werner
 */
public final class URLHandler extends MouseAdapter {
    private URI url;
    private String text;
    private final JLabel label;

    /**
     * Creates a new object of class URLHandler.
     * @param label the label to treat as a hyperlink.
     * @param url the url the label should link to, and the label's text.
     */
    public URLHandler(JLabel label, String url) {
        this(label, url, url);
    }

    /**
     * Creates a new object of class URLHandler.
     * @param label the label to treat as a hyperlink.
     * @param url the url the label should link to.
     * @param text the label's text.
     */
    public URLHandler(JLabel label, String url, String text) {
        this.label = label;
        this.updateLabel(url, text);
        this.label.addMouseListener(this);
    }

    /**
     * Update the label's URL and text to be the same value.
     * @param url the new URL for the label.
     */
    public void updateLabel(String url) {
        this.updateLabel(url, url);
    }

    /**
     * Update the label's URL and text.
     * @param url the new URL for the label.
     * @param text the new text for the label.
     */
    public void updateLabel(String url, String text) {
        this.text = String.format("<HTML><U>%s</U></HTML>", text);
        try {
            this.url = new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }

        this.label.setText(this.text);
        this.label.setForeground(new Color(0,0,230));
        this.label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * An event listener that opens the URL when this label is clicked.
     * If the URL cannot be opened, the label is updated to indicate the failure.
     * @param event
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(this.url);
            } catch (IOException e) {
                this.label.setText(this.text + " ERR: Can't open URL.");
                this.label.setForeground(new Color(0,0,0));
                e.printStackTrace();
            }
        } else {
            this.label.setText(this.text + " ERR: Can't open URL.");
            this.label.setForeground(new Color(0,0,0));
            System.err.println("Desktop not supported when attempting to open URL!");
        }
    }
}
