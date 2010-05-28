/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.entities;

import java.util.List;

public class ProductResultSet {

  /**
   * Total number of results
   */
  private int totalResults;
  
  /**
   * Number of results in current result set
   */
  private int currentResults;
  
  /**
   * Starting point of current result set
   */
  private int start;
  
  /**
   * Number of results per page
   */
  private int limit;
  
  /**
   * Query string used to get results
   */
  private String query;
  
  /**
   * List of product results
   */
  private List<Product> products;

  /**
   * @param totalResults
   * @param currentResults
   * @param start
   * @param limit
   * @param query
   * @param products
   */
  public ProductResultSet(int totalResults, int currentResults, int start,
      int limit, String query, List<Product> products) {
    this.totalResults = totalResults;
    this.currentResults = currentResults;
    this.start = start;
    this.limit = limit;
    this.query = query;
    this.products = products;
  }

  /**
   * @return the totalResults
   */
  public int getTotalResults() {
    return totalResults;
  }

  /**
   * @return the currentResults
   */
  public int getCurrentResults() {
    return currentResults;
  }

  /**
   * @return the start
   */
  public int getStart() {
    return start;
  }

  /**
   * @return the limit
   */
  public int getLimit() {
    return limit;
  }

  /**
   * @return the query
   */
  public String getQuery() {
    return query;
  }

  /**
   * @return the products
   */
  public List<Product> getProducts() {
    return products;
  }
 
}
