/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.entities;

import at.buy.api.APIException;
import at.buy.api.BuyatAPIClient;

/**
 * Represents a product from a buy.at feed.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class Product extends at.buy.entities.Entity {

  /**
   * Product ID
   */
  private int productID;
  
  /**
   * SKU
   */
  private String productSKU;
  
  /**
   * Tracking deeplink URL.
   */
  private String productURL;

  /**
   * Product name.
   */
  private String productName;

  /**
   * Brand name.
   */
  private String brandName;

  /**
   * Product description.
   */
  private String description;

  /**
   * Price of the product.
   */
  private float onlinePrice;

  /**
   * Three-letter currency code.
   */
  private String currency;

  /**
   * HTML-safe currency symbol.
   */
  private String currencySymbol;

  /**
   * URL of the product image.
   */
  private String imageURL;

  /**
   * ID of programme this product belongs to
   */
  private int programmeID;
  
  /**
   * Name of programme this product belongs to
   */
  private String programmeName;

  /**
   * URL of programme this product belongs to
   */
  private String programmeURL;

  /**
   * ID of level 1 category this product belongs to
   */
  private int level1CategoryID;
  
  /**
   * Name of level 1 category this product belongs to
   */
  private String level1CategoryName;

  /**
   * ID of level 2 category this product belongs to
   */
  private int level2CategoryID;
  
  /**
   * Name of level 2 category this product belongs to
   */
  private String level2CategoryName;

  /**
   * ID of feed this product belongs to
   */
  private int feedID;
  
  /**
   * Name of feed this product belongs to
   */
  private String feedName;

  /**
   * The programme object this product belongs to
   */
  private Programme programme;
  
  /**
   * The feed object this product belongs to
   */
  private Feed feed;
  
  /**
   * The client used to create this product
   */
  private BuyatAPIClient client;
  
  /**
   * Create a product object
   * @param productID
   * @param productURL
   * @param productName
   * @param brandName
   * @param description
   * @param onlinePrice
   * @param currency
   * @param currencySymbol
   * @param imageURL
   * @param programmeID
   * @param programmeName
   * @param level1CategoryID
   * @param level1CategoryName
   * @param level2CategoryID
   * @param level2CategoryName
   * @param feedID
   * @param feedName
   * @param client
   */
  public Product(int productID, String productSKU, String productURL, String productName,
      String brandName, String description, float onlinePrice, String currency,
      String currencySymbol, String imageURL, int programmeID,
      String programmeName, String programmeURL, int level1CategoryID, 
      String level1CategoryName, int level2CategoryID, String level2CategoryName, 
      int feedID, String feedName, BuyatAPIClient client) {
    this.productID = productID;
    this.productSKU = productSKU;
    this.productURL = productURL;
    this.productName = productName;
    this.brandName = brandName;
    this.description = description;
    this.onlinePrice = onlinePrice;
    this.currency = currency;
    this.currencySymbol = currencySymbol;
    this.imageURL = imageURL;
    this.programmeID = programmeID;
    this.programmeName = programmeName;
    this.programmeURL = programmeURL;
    this.level1CategoryID = level1CategoryID;
    this.level1CategoryName = level1CategoryName;
    this.level2CategoryID = level2CategoryID;
    this.level2CategoryName = level2CategoryName;
    this.feedID = feedID;
    this.feedName = feedName;
    this.client = client;
  }

  /**
   * @return the productID
   */
  public int getProductID() {
    return productID;
  }

  /**
   * @return the productSKU
   */
  public String getProductSKU() {
    return productSKU;
  }

  /**
   * @return the productURL
   */
  public String getProductURL() {
    return productURL;
  }

  /**
   * @return the productName
   */
  public String getProductName() {
    return productName;
  }

  /**
   * @return the brandName
   */
  public String getBrandName() {
    return brandName;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the onlinePrice
   */
  public float getOnlinePrice() {
    return onlinePrice;
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @return the currencySymbol
   */
  public String getCurrencySymbol() {
    return currencySymbol;
  }

  /**
   * @return the imageURL
   */
  public String getImageURL() {
    return imageURL;
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
   * @return the level1CategoryID
   */
  public int getLevel1CategoryID() {
    return level1CategoryID;
  }

  /**
   * @return the level1CategoryName
   */
  public String getLevel1CategoryName() {
    return level1CategoryName;
  }

  /**
   * @return the level2CategoryID
   */
  public int getLevel2CategoryID() {
    return level2CategoryID;
  }

  /**
   * @return the level2CategoryName
   */
  public String getLevel2CategoryName() {
    return level2CategoryName;
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

  /**
   * Get the feed associated with this product.
   *
   * @return The feed which contains this product.
   * @throws APIException if the api call fails
   */
  public Feed getFeed() throws APIException {
    if(null == this.feed) {
      this.feed = this.client.getFeedInfo(this.feedID);
    }
    return this.feed;
  }
}
