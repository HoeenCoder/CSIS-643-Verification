package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;

/**
 * Interface IPlayerPresenter
 * Defines public methods specific to the player page's presenter.
 *
 * @author Andrew Werner
 */
public interface IPlayerPresenter extends IPresenter {
    /**
     * Prompt this presenter to obtain new information for the view based on
     * the filter options provided.
     * @param player the player to fetch information about.
     * @param option the name search string to use when querying the model.
     */
    void updateFilters(IPlayer player, String option);

    /**
     * Prompt the presenter to have a player's verification details updated in the model.
     * @param player the player to update.
     * @param status the player's new verification status.
     * @param note the player's new verification note.
     * @return human-readable string of text indicating the result.
     */
    String updateVerification(IPlayer player, VerificationStatus status, String note);
}
