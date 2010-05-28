/******************************************************************************

@LICENCE@
 
 *****************************************************************************/

package at.buy.entities;

/**
 * Exceptions that can be thrown by buy.at entities.
 *
 * @author buy.at
 * @version 1.0
 * @since 1.0
 */
public class EntityException extends RuntimeException {
  public EntityException() {
    super();
  }

  public EntityException(String message) {
    super(message);
  }
}
