/******************************************************************************

@LICENCE@
 
 *****************************************************************************/
 
package buyat.api

/**
 * Exceptions thrown by the buy.at API.
 * @author buy.at
 * @version 1.0
 * @since 1.0
 * @param _message Error message.
 * @param _errorCode Error code.
 */
class APIException(_message:String, _errorCode:Int) extends Exception(_message) {

  /**
   * Alternative constructor, when an error code is not available/needed.
   * @param _message Error message.
   */
  def this(_message:String) = this(_message, 0)

  /**
   * Error code. See <a href="https://users.buy.at/apidoc/">https://users.buy.at/apidoc/</a> for details of error codes.
   */
  var errorCode:Int = _errorCode
}