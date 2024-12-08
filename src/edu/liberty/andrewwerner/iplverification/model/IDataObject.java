package edu.liberty.andrewwerner.iplverification.model;

/**
 * Interface IDataObject
 * Root interface of the verification system's data object hierarchy.
 * Specifies common methods all data objects have.
 *
 * @author Andrew Werner
 */
public interface IDataObject {
    /**
     * Get the verification system ID for this object.
     * @return the verification system ID for this object.
     */
    String getId();

    /**
     * Get the remote bracket site's ID for this object.
     * @return the remote bracket site's ID for this object.
     */
    String getRemoteId();

    /**
     * Get the object's name.
     * @return the object's name.
     */
    String getName();

    /**
     * Get the URL for this object.
     * URLs point to the location on the bracket site the information was obtained from.
     * @return the URL for this object.
     */
    String getBracketSiteURL();

    /**
     * Returns the full data key for this object.
     * @return the full data key for this object.
     */
    IDataKey getFullKey();
}
