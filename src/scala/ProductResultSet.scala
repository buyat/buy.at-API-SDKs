/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api.entities

/**
 * Represents a buy.at product result set.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 * @param _totalResults Total number of results.
 * @param _currentResults Number of results in current result set.
 * @param _start Starting point of the current result set.
 * @param _limit Number of results per page.
 * @param _query The query string used to get the results.
 * @param _products List of product results.
 */
class ProductResultSet(_totalResults:Int, _currentResults:Int, _start:Int, _limit:Int,
                       _query:String, _products:List[Product]) extends APIEntity {

  /**
   * Total number of results.
   */
  var totalResults:Int = _totalResults

  /**
   * Number of results in current result set.
   */
  var currentResults:Int = _currentResults

  /**
   * Starting point of the current result set.
   */
  var start:Int = _start

  /**
   * Number of results per page.
   */
  var limit:Int = _limit

  /**
   * The query string used to get the results.
   */
  var query:String = _query

  /**
   * List of product results.
   */
  var products:List[Product] = _products
}