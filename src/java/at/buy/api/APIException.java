/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.api;

/**
 * Exceptions that can be thrown by the buy.at API.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class APIException extends Exception {
  public APIException() {
    super();
  }

  public APIException(String message) {
    super(message);
  }
}
