/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.entities;

import java.util.List;

import at.buy.api.APIException;
import at.buy.api.BuyatAPIClient;

/**
 * Represents a buy.at category.
 * 
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class Category extends at.buy.entities.Entity {

  /**
   * Sort order enum
   * 
   * @author buy.at
   */
  public enum Level { 
    /** Level 1 */ LEVEL1, 
    /** Level 2 */ LEVEL2 
  };
  
  /**
   * ID of this category
   */
  private int categoryID;
  
  /**
   * Level of this category
   */
  private Level level;
  
  /**
   * Name of this category
   */
  private String categoryName;
  
  /**
   * ID of parent category 
   * -1 if none
   */
  private int parentCategoryID;
  
  /**
   * Name of parent category
   */
  private String parentCategoryName;
  
  /**
   * List of subcategories, if any
   */
  private List<Category> subcategories;

  /**
   * The client used to create this category
   */
  private BuyatAPIClient client;

  /**
   * @param categoryID
   * @param level
   * @param categoryName
   * @param parentCategoryID
   * @param parentCategoryName
   * @param subcategories
   * @param client
   */
  public Category(int categoryID, Level level, String categoryName,
      int parentCategoryID, String parentCategoryName,
      List<Category> subcategories, BuyatAPIClient client) {
    this.categoryID = categoryID;
    this.level = level;
    this.categoryName = categoryName;
    this.parentCategoryID = parentCategoryID;
    this.parentCategoryName = parentCategoryName;
    this.subcategories = subcategories;
    this.client = client;
  }

  /**
   * @param categoryID
   * @param level
   * @param categoryName
   * @param parentCategoryID
   * @param parentCategoryName
   * @param client
   */
  public Category(int categoryID, Level level, String categoryName,
      int parentCategoryID, String parentCategoryName, BuyatAPIClient client) {
    this.categoryID = categoryID;
    this.level = level;
    this.categoryName = categoryName;
    this.parentCategoryID = parentCategoryID;
    this.parentCategoryName = parentCategoryName;
    this.client = client;
  }

  /**
   * @return the categoryID
   */
  public int getCategoryID() {
    return categoryID;
  }

  /**
   * @return the level
   */
  public Level getLevel() {
    return level;
  }

  /**
   * @return the categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * @return the parentCategoryID
   */
  public int getParentCategoryID() {
    return parentCategoryID;
  }

  /**
   * @return the parentCategoryName
   */
  public String getParentCategoryName() {
    return parentCategoryName;
  }

  /**
   * @return the client
   */
  public BuyatAPIClient getClient() {
    return client;
  }
  
  public List<Category> getSubcategories() throws APIException {
    if (null == this.subcategories && this.level.equals(Level.LEVEL1)) {
      this.subcategories = this.client.getLevel2Categories(this.categoryID);
    }
    return this.subcategories;
  }
  
}
