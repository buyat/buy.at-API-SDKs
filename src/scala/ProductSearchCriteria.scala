/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

/**
 * Represents criteria that can be used to perform a product search.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
class ProductSearchCriteria {

  /**
   * Query string.
   */
  var query:String = ""

  /**
   * Page number of results to retrieve.
   */
  var page:Int = 1

  /**
   * Number of results per page.
   */
  var perPage:Int = 10

  /**
   * IDs of programmes to include.
   */
  var programmeIds:List[Int] = Nil

  /**
   * IDs of programmes to exclude.
   */
  var excludedProgrammeIds:List[Int] = Nil

  /**
   * IDs of programme categories to exclude.
   */
  var excludedProgrammeCategoryIds:List[Int] = Nil

  /**
   * IDs of feeds to include.
   */
  var feedIds:List[Int] = Nil

  /**
   * ID of category to restrict the search to.
   */
  var categoryId:Int = 0

  /**
   * ID of subcategory to restrict the search to.
   */
  var subcategoryId:Int = 0

  /**
   * Whether to include age restricted products in the results.
   */
  var includeAdult:Boolean = false

  /**
   * Link identifier, to be appended to tracking URLs.
   */
  var lid:String = null

  /**
   * Field to sort the results by.
   */
  var sort:ProductField = Relevance

  /**
   * Direction to order the sort.
   */
  var sortOrder:SortOrder = Desc
}