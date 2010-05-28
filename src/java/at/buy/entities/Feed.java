/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.entities;

import java.util.Date;

import at.buy.api.APIException;
import at.buy.api.BuyatAPIClient;

/**
 * Represents a buy.at feed.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class Feed extends at.buy.entities.Entity {

  /**
   * Unique identifier.
   */
  private int feedID;
  
  private String feedName;
  
  /**
   * ID of programme this feed belongs to
   */
  private int programmeID;
  
  /**
   * Name of programme this feed belongs to
   */
  private String programmeName;

  /**
   * Name of programme this feed belongs to
   */
  private String programmeURL;

  /**
   * The programme object this feed belongs to
   */
  private Programme programme;

  /**
   * The number of products in the feed
   */
  private int numberOfProducts;
  
  /**
   * The last date this feed was updated
   */
  private Date lastUpdated;
  
  /**
   * The client used to create this product
   */
  private BuyatAPIClient client;
  
  /**
   * @param feedID
   * @param feedName
   * @param programmeID
   * @param programmeName
   * @param numberOfProducts
   * @param lastUpdated
   * @param client
   */
  public Feed(int feedID, String feedName, int programmeID,
      String programmeName, String programmeURL, int numberOfProducts, 
      Date lastUpdated, BuyatAPIClient client) {
    this.feedID = feedID;
    this.feedName = feedName;
    this.programmeID = programmeID;
    this.programmeName = programmeName;
    this.programmeURL = programmeURL;
    this.numberOfProducts = numberOfProducts;
    this.lastUpdated = lastUpdated;
    this.client = client;
  }

  /**
   * @return the feedID
   */
  public int getFeedID() {
    return feedID;
  }

  /**
   * @return the feedName
   */
  public String getFeedName() {
    return feedName;
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
   * @return the programmeName
   */
  public String getProgrammeURL() {
    return programmeURL;
  }

  /**
   * @return the numberOfProducts
   */
  public int getNumberOfProducts() {
    return numberOfProducts;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated() {
    return lastUpdated;
  }

  /**
   * Get the programme associated with this product.
   *
   * @return The programme associated with this product.
   * @throws APIException if the api call fails
   */
  public Programme getProgramme() throws APIException {
    if(null == this.programme) {
      this.programme = this.client.getProgrammeInfo(this.programmeID);
    }
    return this.programme;
  }

}
