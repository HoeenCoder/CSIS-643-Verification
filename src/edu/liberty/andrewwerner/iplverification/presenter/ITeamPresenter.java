package edu.liberty.andrewwerner.iplverification.presenter;

import edu.liberty.andrewwerner.iplverification.model.*;

/**
 * Interface ITeamPresenter
 * Defines public methods specific to the team page's presenter.
 *
 * @author Andrew Werner
 */
public interface ITeamPresenter extends IPresenter {
    /**
     * Prompt the presenter to have a team's verification details updated in the model.
     * @param team the team to update.
     * @param status the team's new verification status.
     * @param note the team's new verification note.
     * @return human-readable string of text indicating the result.
     */
    String updateVerification(ITeam team, VerificationStatus status, String note);
}
