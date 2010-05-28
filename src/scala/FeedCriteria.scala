/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

/**
 * Represents criteria that can be used to generate a feed URL.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
class FeedCriteria {

  /**
   * ID of the feed.
   */
  var feedId:Int = 0

  /**
   * Format of the feed.
   * See FeedFormat for possible values.
   */
  var feedFormat:FeedFormat = FeedXML

  /**
   * Record # to start at.
   */
  var start:Int = -1

  /**
   * Number of products to retrieve per 'page'.
   */
  var perPage:Int = -1

  /**
   * Level 1 category ID.
   */
  var level1CategoryId:Int = -1

  /**
   * Level 2 category ID.
   */
  var level2CategoryId:Int = -1

  /**
   * Programme category ID.
   */
  var programmeCategoryId:Int = -1

  /**
   * Programme category levels.
   */
  var programmeCategoryLevels:List[String] = Nil

  /**
   * Link identifier to be appended to tracking URLs within the feed.
   */
  var lid:String = null

  /**
   * Whether to use HTTPS for the feed URL.
   */
  var useHTTPS:Boolean = false

  /**
   * Whether to reverse map XML node names.
   */
  var reverseMapXML:Boolean = true

  /**
   * Whether to restict the feed to 'best seller' items only.
   */
  var bestseller:Boolean = false
}