/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.entities;

/**
 * Represents a buy.at programme.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class Programme extends at.buy.entities.Entity {

  /**
   * Unique ID of the programme.
   */
  private int programmeID;

  /**
   * Name of the programme.
   */
  private String programmeName;

  /**
   * Tracking URL for the programme.
   */
  private String programmeURL;

  /**
   * Whether or not this programme has feeds.
   */
  private boolean hasFeed;

  /**
   * @param programmeID
   * @param programmeName
   * @param programmeURL
   * @param hasFeed
   */
  public Programme(int programmeID, String programmeName, String programmeURL,
      boolean hasFeed) {
    this.programmeID = programmeID;
    this.programmeName = programmeName;
    this.programmeURL = programmeURL;
    this.hasFeed = hasFeed;
  }

  /**
   * @return the programmeID
   */
  public int getProgrammeID() {
    return programmeID;
  }

  /**
   * @return the programmeName
   */
  public String getProgrammeName() {
    return programmeName;
  }

  /**
   * @return the programmeURL
   */
  public String getProgrammeURL() {
    return programmeURL;
  }

  /**
   * @return the hasFeed
   */
  public boolean hasFeed() {
    return hasFeed;
  }
  
}