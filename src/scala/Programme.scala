/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api.entities

/**
 * Represents a buy.at programme.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 * @param _programmeId Programme ID.
 * @param _programmeName Programme name.
 * @param _programmeURL Programme URL.
 * @param _hasFeed Whether or not the programme has at least one feed.
 */
class Programme(_programmeId:Int, _programmeName:String, _programmeURL:String, _hasFeed:Boolean) extends APIEntity {

  /**
   * Unique ID of the programme.
   */
  var programmeId:Int = _programmeId

  /**
   * Name of the programme.
   */
  var programmeName:String = _programmeName

  /**
   * Tracking URL for the programme.
   */
  var programmeURL:String = _programmeURL

  /**
   * Whether or not this programme has feeds.
   */
  var hasFeed:Boolean = _hasFeed
}