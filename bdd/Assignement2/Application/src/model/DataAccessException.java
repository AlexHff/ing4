package model;

/**
 * An exception reported by the {@link DataAccess}.
 *
 * @author Jean-Michel Busca
 *
 */
public class DataAccessException extends Exception {

  //
  // CONSTANTS
  //
  private static final long serialVersionUID = 1L;

  //
  // CONSTRUCTORS
  //
  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message
   *          the detail message, which is saved for later retrieval by the
   *          Throwable.getMessage() method
   */
  public DataAccessException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified cause.
   *
   * @param cause
   *          the cause, which is saved for later retrieval by the
   *          Throwable.getCause() method
   */
  public DataAccessException(Throwable cause) {
    super(cause);
  }

}
