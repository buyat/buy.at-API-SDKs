/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api.entities

import java.util.{Date}

/**
 * Represents a buy.at feed.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 * @param _feedId Feed ID.
 * @param _feedName Feed name.
 * @param _programmeId ID of the programme the feed belongs to.
 * @param _numberOfProducts The number of products in the feed.
 * @param _lastUpdated The date the feed was last updated.
 */
class Feed(_feedId:Int, _feedName:String, _programmeId:Int, _numberOfProducts:Int, _lastUpdated:Date) extends APIEntity {

  /**
   * Unique ID of the feed.
   */
  var feedId:Int = _feedId

  /**
   * Name of the feed.
   */
  var feedName:String = _feedName

  /**
   * ID of the programme this feed belongs to.
   */
  var programmeId:Int = _programmeId

  /**
   * The number of products in the feed.
   */
  var numberOfProducts:Int = _numberOfProducts

  /**
   * The last date this feed was updated
   */
  var lastUpdated:Date = _lastUpdated
}